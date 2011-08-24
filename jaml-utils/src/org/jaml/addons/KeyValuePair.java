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
package org.jaml.addons;

import java.util.Map;

import org.jaml.api.IKeyValuePair;

public class KeyValuePair implements IKeyValuePair {
	private String key;
	private Object object;
	@SuppressWarnings("rawtypes")
	private Map owner;

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public Object getContent() {
		return object;
	}

	@Override
	public void setContent(Object object) {
		this.object = object;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getOwner() {
		return owner;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setOwner(Map owner) {
		this.owner = owner;
	}
}
