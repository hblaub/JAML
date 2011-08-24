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
package org.jaml.proportions;

import java.util.Collection;

import org.jaml.api.AbstractProportionHandler;

@SuppressWarnings("rawtypes")
public class CollectionHandler extends
		AbstractProportionHandler<Collection, Object> {

	public CollectionHandler() {
		super(Collection.class, Object.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void processWithTypes(Collection parent, Object child) {
		parent.add(child);
	}
}
