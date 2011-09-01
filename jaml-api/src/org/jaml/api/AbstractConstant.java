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

import java.lang.reflect.Field;

public abstract class AbstractConstant<T> implements IConstant<T> {
	protected Class<T> type;
	protected T value;
	protected String source;
	protected String name;
	protected boolean valid;

	protected AbstractConstant(Class<T> type) {
		this.type = type;
	}

	@Override
	public Class<T> getType() {
		return type;
	}

	protected abstract T getDefaultValue();

	@Override
	public T getValue() {
		if (value == null) {
			valid = false;
			try {
				Class<?> clazz = Class.forName(source);
				for (Field publicField : clazz.getFields()) {
					if (publicField.getName().equalsIgnoreCase(name)) {
						value = type.cast(publicField.get(null));
						valid = true;
						break;
					}
				}
			} catch (Exception e) {
				// Silent exception
			}
		}
		return value == null ? getDefaultValue() : value;
	}

	@Override
	public String getSource() {
		return source;
	}

	@Override
	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isValid() {
		return valid;
	}
}
