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

import java.util.Arrays;
import java.util.Locale;

public abstract class AbstractTypeConverter implements ITypeConverter {
	protected Class<?>[] types;

	protected AbstractTypeConverter(Class<?>... classes) {
		types = classes;
	}

	@Override
	public Class<?>[] getConvertibleTypes() {
		return types;
	}

	@Override
	public String convertObject(Object value) {
		return convertObject(Defaults.standardLocale, value);
	}

	@Override
	public Object convertString(String value) {
		return convertString(Defaults.standardLocale, value);
	}

	public abstract String convertObject(Locale locale, Object value);

	public abstract Object convertString(Locale locale, String value);

	@SuppressWarnings("unchecked")
	protected <T> T cast(Object object) {
		return (T) object;
	}

	@Override
	public String toString() {
		return String.format("%s %s", getClass().getName(),
				Arrays.asList(types));
	}
}
