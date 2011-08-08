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
package org.jaml.converters;

import java.lang.reflect.Field;
import java.util.Locale;

import org.jaml.api.AbstractTypeConverter;
import org.jaml.util.ReflectionUtils;

public class IntegerConverter extends AbstractTypeConverter {

	public IntegerConverter() {
		super(Integer.class, int.class);
	}

	@Override
	public String convertObject(Locale locale, Object value) {
		return value.toString();
	}

	@Override
	public Object convertString(Locale locale, String value) {
		// Check if value contains only numbers
		if (value.matches("[0-9]+")) {
			return Integer.parseInt(value);
		} else {
			// Try to get constant
			try {
				int indexOfSeparator = value.lastIndexOf('.');
				String className = value.substring(0, indexOfSeparator);
				String fieldName = value.substring(indexOfSeparator + 1);
				Class<?> foundClass = Class.forName(className);
				Field field = ReflectionUtils.findFieldByClass(foundClass,
						fieldName);
				return field.get(null);
			} catch (Exception ex) {
				// Return minus one, if nothing could be parsed
				return -1;
			}
		}
	}
}
