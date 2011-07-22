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
package org.jaml.loaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jaml.api.AbstractExtensionLoader;
import org.jaml.api.IProportionHandler;
import org.jaml.structs.Pair;
import org.jaml.util.ReflectionUtils;

public class ProportionHandlerLoader extends
		AbstractExtensionLoader<IProportionHandler, Pair<Class<?>, Class<?>>> {

	private Map<String, List<Class<?>>> cache;

	public ProportionHandlerLoader() {
		cache = new HashMap<String, List<Class<?>>>();
	}

	@Override
	protected Class<IProportionHandler> getTypeClass() {
		return IProportionHandler.class;
	}

	private void cacheClassIfNotDone(Class<?> clazz) {
		if (!cache.containsKey(clazz.getName())) {
			cache.put(clazz.getName(),
					ReflectionUtils.getAllInterfacesAndSubClasses(clazz));
		}
	}

	@Override
	protected void doCaching(Pair<Class<?>, Class<?>> key) {
		cacheClassIfNotDone(key.getFirst());
		cacheClassIfNotDone(key.getSecond());
	}

	@Override
	protected boolean eval(IProportionHandler type, Pair<Class<?>, Class<?>> key) {
		if (key.getFirst() == Object.class || key.getSecond() == Object.class) {
			return false;
		}
		List<Class<?>> clParent = cache.get(key.getFirst().getName());
		List<Class<?>> clChild = cache.get(key.getSecond().getName());
		return clParent.contains(type.getParentType())
				&& clChild.contains(type.getChildType());
	}
}
