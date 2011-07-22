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
package org.jaml.container;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.jaml.exceptions.JamlAttributeNotFoundException;
import org.jaml.util.ReflectionUtils;

public class ClassCache {
	protected Class<?> type;
	protected Map<String, PropertyContainer> methods;

	public ClassCache(String className) throws ClassNotFoundException {
		type = Class.forName(className, true, getClass().getClassLoader());
		methods = ReflectionUtils.createMethodMap(type);
	}

	public Class<?> getType() {
		return type;
	}

	public boolean hasProperty(String name) {
		return methods.containsKey(name);
	}

	protected PropertyContainer getPropertyContainer(String name) {
		if (!hasProperty(name)) {
			throw new JamlAttributeNotFoundException(type, name,
					listProperties());
		}
		return methods.get(name);
	}

	protected String[] listProperties() {
		return methods.keySet().toArray(new String[methods.keySet().size()]);
	}

	public boolean isReadable(String name) {
		return getPropertyContainer(name).isReadable();
	}

	public boolean isWritable(String name) {
		return getPropertyContainer(name).isWritable();
	}

	public Iterator<Method> get(String name) {
		return getPropertyContainer(name).getGetters();
	}

	public Iterator<Method> set(String name) {
		return getPropertyContainer(name).getSetters();
	}

	public boolean isRW(String name) {
		return isReadable(name) && isWritable(name);
	}

	protected ParameterContainer getArguments(Iterator<Method> iterator) {
		ParameterContainer paramContainer = new ParameterContainer();
		while (iterator.hasNext()) {
			paramContainer.add(iterator.next());
		}
		return paramContainer;
	}

	public ParameterContainer getReadableArguments(String name) {
		return getArguments(getPropertyContainer(name).getGetters());
	}

	public ParameterContainer getWritableArguments(String name) {
		return getArguments(getPropertyContainer(name).getSetters());
	}
}
