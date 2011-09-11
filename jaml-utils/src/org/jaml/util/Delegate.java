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

import java.lang.reflect.Method;

import org.jaml.api.IDelegate;
import org.jaml.exceptions.DelegateException;
import org.jaml.exceptions.DelegateInvalidException;

/**
 * Implementation of the IDelegate interface
 * 
 * @param <T>
 *            Class of the method owner
 * @param <P>
 *            Parameter of the targeted method
 */
public class Delegate<T, P> implements IDelegate<T, P> {
	private T object;
	private Class<?> objectClass;
	private String methodName;
	private Class<P> paramClass;

	private Method method;

	public static <T, P> IDelegate<T, P> create(T object, String methodName,
			Class<P> paramClass) {
		return new Delegate<T, P>(object, methodName, paramClass);
	}

	public static <T, P> IDelegate<T, P> create(Class<T> objectClass,
			String methodName, Class<P> paramClass) {
		return new Delegate<T, P>(objectClass, methodName, paramClass);
	}

	private Delegate(T object, Class<T> objectClass, String methodName,
			Class<P> paramClass) {
		this.object = object;
		this.objectClass = object == null ? objectClass : object.getClass();
		this.methodName = methodName;
		this.paramClass = paramClass;
	}

	public Delegate(T object, String methodName, Class<P> paramClass) {
		this(object, null, methodName, paramClass);
	}

	public Delegate(Class<T> objectClass, String methodName, Class<P> paramClass) {
		this(null, objectClass, methodName, paramClass);
	}

	@Override
	public boolean isValid() {
		method = method == null ? searchMethod(paramClass) : method;
		method = method == null ? searchMethod() : method;
		return method != null;
	}

	private Method searchMethod(Class<P> paramClass) {
		try {
			return objectClass.getMethod(methodName, paramClass);
		} catch (Exception e) {
			return null;
		}
	}

	private Method searchMethod() {
		try {
			Method method = objectClass.getMethod(methodName);
			return method.getReturnType().equals(paramClass) ? method : null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void invoke(P value) {
		if (isValid()) {
			try {
				method.invoke(object, value);
				return;
			} catch (Exception exception) {
				throw new DelegateException(objectClass, method, value,
						exception);
			}
		}
		throw new DelegateInvalidException(objectClass, methodName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public P invoke() {
		if (isValid()) {
			try {
				return (P) method.invoke(object);
			} catch (Exception exception) {
				throw new DelegateException(objectClass, method, object,
						exception);
			}
		}
		throw new DelegateInvalidException(objectClass, methodName);
	}

	@Override
	public boolean needsParameter() {
		return method != null ? method.getParameterTypes().length > 0 : false;
	}

	@Override
	public String toString() {
		return String.format(
				"Delegate of class '%s' --> %s",
				objectClass.getName(),
				method == null ? String.format(
						"should be method %s of type %s!", methodName,
						paramClass.getName()) : method);
	}
}
