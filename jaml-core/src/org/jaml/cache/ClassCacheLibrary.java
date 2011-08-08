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
package org.jaml.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jaml.container.ClassCache;

public class ClassCacheLibrary {
	private static final Logger log = Logger.getLogger(ClassCacheLibrary.class);

	private Map<String, ClassCache> caches;

	private ClassCacheLibrary() {
		caches = new HashMap<String, ClassCache>();
	}

	private static ClassCacheLibrary instance;

	public static ClassCacheLibrary getInstance() {
		if (instance == null) {
			log.debug("Instantiation of " + ClassCacheLibrary.class + "...");
			instance = new ClassCacheLibrary();
		}
		return instance;
	}

	public boolean isCached(String fQN) {
		return caches.containsKey(fQN);
	}

	public boolean addToCache(String fQN) {
		try {
			caches.put(fQN, new ClassCache(fQN));
			log.debug("Added '" + fQN + "' to cache!");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public void addToCacheIfRequired(String fQN) {
		if (!isCached(fQN)) {
			addToCache(fQN);
		}
	}

	public ClassCache getCache(String fQN) {
		return caches.get(fQN);
	}
}
