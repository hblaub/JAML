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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParameterContainer {
	private Map<Class<?>[], Method> methodMap;

	public ParameterContainer() {
		methodMap = new HashMap<Class<?>[], Method>();
	}

	public void add(Method method) {
		methodMap.put(method.getParameterTypes(), method);
	}

	public boolean has(Class<?>... classes) {
		for (Class<?>[] params : methodMap.keySet()) {
			if (compare(params, classes)) {
				return true;
			}
		}
		return false;
	}

	private boolean compare(Class<?>[] params, Class<?>[] classes) {
		if (params.length == classes.length) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] != classes[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public Method get(Class<?>... classes) {
		for (Class<?>[] params : methodMap.keySet()) {
			if (compare(params, classes)) {
				return methodMap.get(params);
			}
		}
		return null;
	}

	public List<Class<?>[]> filterByNumber(int number) {
		List<Class<?>[]> paramList = new LinkedList<Class<?>[]>();
		for (Class<?>[] params : methodMap.keySet()) {
			if (params.length == number) {
				paramList.add(params);
			}
		}
		return paramList;
	}

	public void add(Iterator<Method> iterator) {
		while (iterator.hasNext()) {
			add(iterator.next());
		}
	}
}
