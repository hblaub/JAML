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

/**
 * Implementation of the interface IBinding
 * 
 * @param <F>
 *            First object's class
 * @param <S>
 *            Second object's class
 * @param <P>
 *            Parameter's class
 */
public class Binding<F, S, P> implements IBinding<F, S, P> {
	private IDelegate<F, P> firstDelegate;
	private IDelegate<S, P> secondDelegate;

	public Binding(IDelegate<F, P> firstDelegate, IDelegate<S, P> secondDelegate) {
		this.firstDelegate = firstDelegate;
		this.secondDelegate = secondDelegate;
	}

	@Override
	public IDelegate<F, P> getFirstDelegate() {
		return firstDelegate;
	}

	@Override
	public IDelegate<S, P> getSecondDelegate() {
		return secondDelegate;
	}

	public static <F, S, P> Binding<F, S, P> create(F object1, String method1,
			S object2, String method2, Class<P> paramClass) {
		return new Binding<F, S, P>(Delegate.create(object1, method1,
				paramClass), Delegate.create(object2, method2, paramClass));
	}

	@Override
	public boolean apply() {
		if (!firstDelegate.isValid() || !secondDelegate.isValid()) {
			return false;
		}
		if (firstDelegate.needsParameter()) {
			firstDelegate.invoke(secondDelegate.invoke());
			return true;
		} else if (secondDelegate.needsParameter()) {
			secondDelegate.invoke(firstDelegate.invoke());
			return true;
		}
		return false;
	}
}
