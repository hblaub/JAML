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

import java.util.Collection;

/**
 * ObservableInstantiationException is thrown if observable objects like
 * ObservableCollection cannot be instantiated
 */
public class ObservableInstantiationException extends RuntimeException {
	private static final long serialVersionUID = 4411966021971325094L;

	/**
	 * ObservableInstantiationException constructor
	 * 
	 * @param collectionClass
	 *            class which could not be used
	 * @param exception
	 *            caught exception for information purposes
	 */
	public ObservableInstantiationException(
			Class<? extends Collection<?>> collectionClass, Exception exception) {
		super(String.format("Could not create new collection of type '%s'!",
				collectionClass.getName(), exception));
	}
}
