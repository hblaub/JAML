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

import java.awt.Dimension;
import java.util.Locale;

import org.jaml.api.AbstractTypeConverter;

public class DimensionConverter extends AbstractTypeConverter {

	public DimensionConverter() {
		super(Dimension.class);
	}

	@Override
	public String convertObject(Locale locale, Object value) {
		Dimension dim = cast(value);
		return dim.width + ";" + dim.height;
	}

	@Override
	public Object convertString(Locale locale, String value) {
		String[] splitted = value.split(";");
		int width = Integer.parseInt(splitted[0]);
		int height = Integer.parseInt(splitted[1]);
		return new Dimension(width, height);
	}
}
