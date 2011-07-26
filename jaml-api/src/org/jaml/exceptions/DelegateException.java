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
package org.jaml.exceptions;

import java.lang.reflect.Method;

public class DelegateException extends RuntimeException {
	private static final long serialVersionUID = -5132908357722861884L;

	public DelegateException(Class<?> clazz, Method method, Object value,
			Exception exception) {
		super(
				String.format(
						"Method '%s' of class '%s' could not be invoked with value '%s'!",
						method, clazz.getName(), value), exception);
	}
}
