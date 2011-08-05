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

import java.awt.event.ActionListener;
import java.util.Locale;

import org.jaml.api.AbstractTypeConverter;
import org.jaml.api.IParserHandle;
import org.jaml.api.IParserHandleConsumer;
import org.jaml.exceptions.ParserHandleExpectedException;

public class ActionListenerConverter extends AbstractTypeConverter implements
		IParserHandleConsumer {
	private IParserHandle parserHandle;

	public ActionListenerConverter() {
		super(ActionListener.class);
	}

	@Override
	public String convertObject(Locale locale, Object value) {
		return null; // Not supported!
	}

	@Override
	public Object convertString(Locale locale, String value) {
		if (parserHandle == null)
			throw new ParserHandleExpectedException(this.getClass());
		return parserHandle.createDelegate(value, ActionListener.class);
	}

	@Override
	public void setIParserHandle(IParserHandle handle) {
		parserHandle = handle;
	}
}
