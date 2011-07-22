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

import java.awt.Rectangle;
import java.util.Locale;

import org.jaml.api.AbstractTypeConverter;

public class RectangleConverter extends AbstractTypeConverter {

	public RectangleConverter() {
		super(Rectangle.class);
	}

	@Override
	public String convertObject(Locale locale, Object value) {
		Rectangle rectangle = cast(value);
		return rectangle.width + ";" + rectangle.height + ";" + rectangle.x
				+ ";" + rectangle.y;
	}

	@Override
	public Object convertString(Locale locale, String value) {
		String[] splitted = value.split(";");
		int width = Integer.parseInt(splitted[0]);
		int height = Integer.parseInt(splitted[1]);
		int x = Integer.parseInt(splitted[2]);
		int y = Integer.parseInt(splitted[3]);
		return new Rectangle(x, y, width, height);
	}
}
