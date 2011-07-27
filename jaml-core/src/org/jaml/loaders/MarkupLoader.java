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

import org.jaml.api.AbstractExtensionLoader;
import org.jaml.api.IMarkupExtension;

public class MarkupLoader extends
		AbstractExtensionLoader<IMarkupExtension, String> {

	@Override
	protected Class<IMarkupExtension> getTypeClass() {
		return IMarkupExtension.class;
	}

	@Override
	protected boolean eval(IMarkupExtension type, String key) {
		return type.getSymbol().equalsIgnoreCase(key);
		
//		String markup = key.substring(1, key.length() - 1);
//		String[] splitted = markup.split(" ");
//		String symbol = splitted[0];
//		return type.getSymbol().equalsIgnoreCase(symbol);
	}
}
