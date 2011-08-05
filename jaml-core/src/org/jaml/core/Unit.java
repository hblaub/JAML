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
package org.jaml.core;

import java.io.InputStream;

import org.jaml.api.Defaults;
import org.jaml.api.IUnit;
import org.jaml.exceptions.JamlDefinitionNotFoundException;
import org.jaml.objects.Element;
import org.jaml.objects.ParentObject;
import org.jaml.util.ParserHandle;

public class Unit<T> extends ParentObject implements IUnit<T> {
	protected Element tree;
	protected T root;

	@SuppressWarnings("unchecked")
	protected Unit() {
		super(null);
		String uri = getClass().getSimpleName() + "." + Defaults.fileExtension
				+ "." + Defaults.alternativeExtension;
		InputStream stream = getClass().getResourceAsStream(uri);
		if (stream != null) {
			tree = JamlReader.load(new ParserHandle<T>(this), stream);
			root = (T) tree.getContent();
		} else {
			throw new JamlDefinitionNotFoundException(getClass(), uri);
		}
	}

	@Override
	public Element getTree() {
		return tree;
	}

	@Override
	public T getRoot() {
		return root;
	}

	protected <O> O get(String key) {
		return tree.getStoredObject(key);
	}
}
