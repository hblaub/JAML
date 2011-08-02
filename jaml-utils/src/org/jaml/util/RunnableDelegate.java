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

/**
 * The class RunnableDelegate enables one to use every instance method in a
 * thread, without the need for anonymous classes.
 * 
 * @param <T>
 *            method-owning type
 */
public class RunnableDelegate<T> extends Delegate<T, Void> implements Runnable {

	/**
	 * Constructor for creating a new RunnableDelegate
	 * 
	 * @param object
	 *            method-owning object
	 * @param methodName
	 *            name of the method to invoke
	 */
	public RunnableDelegate(T object, String methodName) {
		super(object, methodName, Void.class);
	}

	@Override
	public void run() {
		invoke();
	}
}
