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
package org.jaml.api;

import java.util.ServiceLoader;

public abstract class AbstractExtensionLoader<T, K> implements
		IExtensionLoader<T, K> {

	protected ServiceLoader<T> loader;

	public AbstractExtensionLoader() {
		loader = ServiceLoader.load(getTypeClass());
//		System.out.println( "----------loader: " + loader.toString() );
	}

	@Override
	public T get(K key) {
		doCaching(key);
		for (T type : loader) {
			if (eval(type, key)) {
				return type;
			}
		}
		return null;
	}

	protected void doCaching(K key) {
	}

	protected abstract Class<T> getTypeClass();

	protected abstract boolean eval(T type, K key);
}
