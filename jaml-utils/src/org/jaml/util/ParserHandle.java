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

import org.jaml.api.IBinding;
import org.jaml.api.IDelegate;
import org.jaml.api.IParserHandle;
import org.jaml.api.IUnit;

public class ParserHandle<T> implements IParserHandle<T> {
	private IUnit<T> invoker;

	public ParserHandle(IUnit<T> invoker) {
		this.invoker = invoker;
	}

	@Override
	public IBinding createBinding(String methodName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <TC> IDelegate createDelegate(String methodName,
			Class<TC> targetClass) {
		return new ActionListenerDelegate<IUnit<T>>(invoker, methodName);
	}
}
