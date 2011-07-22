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

public abstract class AbstractProportionHandler<P, C> implements
		IProportionHandler {
	protected Class<P> parentType;
	protected Class<C> childType;

	protected AbstractProportionHandler(Class<P> parentClass,
			Class<C> childClass) {
		parentType = parentClass;
		childType = childClass;
	}

	@Override
	public Class<?> getParentType() {
		return parentType;
	}

	@Override
	public Class<?> getChildType() {
		return childType;
	}

	@Override
	public void process(Object parent, Object child) {
		processWithTypes(parentType.cast(parent), childType.cast(child));
	}

	@Override
	public String toString() {
		return getClass().getName() + " [ " + getParentType().getName() + " ; "
				+ getChildType().getName() + " ]";
	}

	protected abstract void processWithTypes(P parent, C child);
}
