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
package org.jaml.exceptions;

import java.util.Arrays;

public class JamlAttributeNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public JamlAttributeNotFoundException(Class<?> type, String name,
			String[] allProperties) {
		super(String.format(
				"Class '%s' does not have an attribute called '%s'! %s", type
						.getName(), name,
				(allProperties.length > 0 ? "Maybe one of the following?"
						+ getPossibleAttributes(allProperties, name) : "")));
	}

	protected static String getPossibleAttributes(String[] allProperties,
			String attribute) {
		StringBuilder bld = new StringBuilder();
		Arrays.sort(allProperties);
		for (String propName : allProperties) {
			if (propName.contains(attribute)) {
				bld.append(", ");
				bld.append(propName);
			}
		}
		return bld.toString().substring(1);
	}
}
