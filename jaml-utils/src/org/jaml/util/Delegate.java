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

public class Delegate<T, P> implements IDelegate<T, P> {
	private T object;
	private String methodName;
	private Class<P> paramClass;

	private Method method;

	public static <T, P> IDelegate<T, P> create(T object, String methodName,
			Class<P> paramClass) {
		return new Delegate<T, P>(object, methodName, paramClass);
	}

	public Delegate(T object, String methodName, Class<P> paramClass) {
		this.object = object;
		this.methodName = methodName;
		this.paramClass = paramClass;
	}

	@Override
	public boolean isValid() {
		if (method == null) {
			try {
				method = object.getClass().getMethod(methodName, paramClass);
			} catch (SecurityException e) {
				return false;
			} catch (NoSuchMethodException e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void invoke(P value) {
		if (isValid()) {
			try {
				method.invoke(object, value);
				return;
			} catch (Exception exception) {
				throw new DelegateException(object.getClass(), method, value,
						exception);
			}
		}
		throw new DelegateInvalidException(object.getClass(), methodName);
	}
}
