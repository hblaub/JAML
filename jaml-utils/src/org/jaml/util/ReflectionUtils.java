/*******************************************************************************
 * Copyright (C) 2011 by Harry Blauberg
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.jaml.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jaml.api.Defaults;
import org.jaml.container.ClassCache;
import org.jaml.container.ParameterContainer;
import org.jaml.container.PropertyContainer;

public class ReflectionUtils {

	public static boolean contains(Object obj, Class<?> clazz) {
		return contains(obj.getClass(), clazz);
	}

	public static boolean contains(Class<?> objClazz, Class<?> clazz) {
		for (Class<?> oneClass : getAllInterfacesAndSubClasses(objClazz)) {
			if (oneClass == clazz) {
				return true;
			}
		}
		return false;
	}

	public static List<Class<?>> getAllInterfacesAndSubClasses(Object obj) {
		return getAllInterfacesAndSubClasses(obj.getClass());
	}

	public static List<Class<?>> getAllInterfacesAndSubClasses(Class<?> clazz) {
		List<Class<?>> allClasses = new LinkedList<Class<?>>();
		Class<?> actual = correctIfArray(clazz);
		while (actual != null) {
			addUniqueClassToList(allClasses, actual.getInterfaces());
			addUniqueClassToList(allClasses, actual);
			actual = actual.getSuperclass();
		}
		return allClasses;
	}

	public static Class<?> correctIfArray(Class<?> clazz) {
		return clazz.isArray() ? clazz.getComponentType() : clazz;
	}

	protected static void addUniqueClassToList(List<Class<?>> list,
			Class<?>... classes) {
		for (Class<?> clazz : classes) {
			if (clazz != null && !list.contains(clazz)) {
				list.add(clazz);
			}
		}
	}

	public static boolean containsOneOfThem(Class<?> type, Class<?>[] classes) {
		for (Class<?> clazz : classes) {
			if (contains(type, clazz)) {
				return true;
			}
		}
		return false;
	}

	public static String getClassByNamespace(String namespace,
			String elementName) {
		return namespace.replace(Defaults.namespacePrefix, "")
				.replace('/', '.') + "." + elementName;
	}

	public static String getXMLNamespaceByObject(Object object) {
		return Defaults.namespacePrefix
				+ object.getClass().getPackage().getName().replace('.', '/');
	}

	public static String getClassByNamespace(QName name) {
		return getClassByNamespace(name.getNamespaceURI(), name.getLocalPart());
	}

	public static Field findFieldByClass(Class<?> clazz, String name) {
		Field[] allFields = clazz.getFields();
		for (Field field : allFields) {
			if (field.getName().equalsIgnoreCase(name)) {
				return field;
			}
		}
		return null;
	}

	public static Map<String, Object> getDifferencingMethodsAndValuesOnly(
			Object instance) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, PropertyContainer> methodMap = createMethodMap(instance
				.getClass());
		Object[] args = new Object[] {};
		Object instanceValue = null;
		Object compareValue = null;
		Method method = null;
		PropertyContainer propContainer = null;
		ParameterContainer paramContainer = null;
		try {
			Object compareObj = instance.getClass().newInstance();
			for (String property : methodMap.keySet()) {
				propContainer = methodMap.get(property);
				if (propContainer.isRW()) {
					paramContainer = new ParameterContainer();
					paramContainer.add(propContainer.getGetters());
					List<Class<?>[]> paramList = paramContainer
							.filterByNumber(0);
					if (!paramList.isEmpty()) {
						method = paramContainer.get(paramList.get(0));
						try {
							instanceValue = method.invoke(instance, args);
							compareValue = method.invoke(compareObj, args);
							if (instanceValue != null && compareValue != null
									&& !instanceValue.equals(compareValue)
									&& !instanceValue.toString().contains("@")) {
								map.put(method.getName().substring(3),
										instanceValue);
							}
						} catch (Exception e) {
							throw new NullPointerException(property + " -> "
									+ e.getClass().getSimpleName() + " ("
									+ e.getMessage() + ")");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, PropertyContainer> createMethodMap(Class<?> clazz) {
		Map<String, PropertyContainer> map = new HashMap<String, PropertyContainer>();
		String propertyName = null;
		PropertyContainer container = null;
		for (Method method : clazz.getMethods()) {
			if (!Modifier.isStatic(method.getModifiers())
					&& method.getName().length() > 3) {
				if (method.getName().startsWith("get")
						|| method.getName().startsWith("set")
						|| method.getName().startsWith("add")) {
					propertyName = method.getName().substring(3);
					if (!map.containsKey(propertyName)) {
						map.put(propertyName, new PropertyContainer());
					}
					container = map.get(propertyName);
					container.add(method);
				}
			}
		}
		return map;
	}

	public static Method searchSetterMethodWithInterface(ClassCache cache,
			String attribute, Class<?> interfaceClazz) {
		Iterator<Method> iterator = cache.set(attribute);
		List<Class<?>> typeList = getAllInterfacesAndSubClasses(interfaceClazz);
		Method setter = null;
		while (iterator.hasNext()) {
			setter = iterator.next();
			if (setter.getParameterTypes().length == 1
					&& setter.getParameterTypes()[0].isInterface()
					&& typeList.contains(setter.getParameterTypes()[0])) {
				return setter;
			}
		}
		return null;
	}

	public static Method searchSetterMethod(ClassCache cache, String attribute,
			Class<?> clazz) {
		Iterator<Method> iterator = cache.set(attribute);
		Method setter = null;
		while (iterator.hasNext()) {
			setter = iterator.next();
			if (setter.getParameterTypes().length == 1
					&& (setter.getParameterTypes()[0] == clazz || setter
							.getParameterTypes()[0] == getPrimitiveTypeIfFound(clazz))) {
				return setter;
			}
		}
		return searchSetterMethodWithInterface(cache, attribute, clazz);
	}

	public static Class<?> getPrimitiveTypeIfFound(Class<?> clazz) {
		try {
			Class<?> primitive = (Class<?>) clazz.getField("TYPE").get(null);
			return primitive;
		} catch (Exception ex) {
			return clazz;
		}
	}
}
