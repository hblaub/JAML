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
import java.util.LinkedList;
import java.util.List;

public class PropertyContainer {
	protected List<Method> getters;
	protected List<Method> setters;

	public PropertyContainer() {
		getters = new LinkedList<Method>();
		setters = new LinkedList<Method>();
	}

	public void add(Method method) {
		if (method.getName().startsWith("get")) {
			addGetter(method);
		} else if (method.getName().startsWith("set")) {
			addSetter(method);
		} else if (method.getName().startsWith("add")) {
			addSetter(method);
		}
	}

	protected void addGetter(Method method) {
		getters.add(method);
	}

	protected void addSetter(Method method) {
		setters.add(method);
	}

	public boolean isReadable() {
		return !getters.isEmpty();
	}

	public boolean isWritable() {
		return !setters.isEmpty();
	}

	public Iterator<Method> getGetters() {
		return getters.iterator();
	}

	public Iterator<Method> getSetters() {
		return setters.iterator();
	}

	public boolean isRW() {
		return isReadable() && isWritable();
	}
}
