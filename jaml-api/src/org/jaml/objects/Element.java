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
package org.jaml.objects;

import java.util.HashMap;
import java.util.Map;

import org.jaml.api.ParsingInstructions;

public class Element extends ParentObject {
	protected Object content;
	protected Class<?> type;
	protected Map<ParsingInstructions, String> instructions;
	protected Map<String, Object> storedObjects;

	public Element(ParentObject parent, Object object) {
		super(parent);
		content = object;
		type = object != null ? object.getClass() : void.class;
	}

	public Object getContent() {
		return content;
	}

	public <T> T getContent(Class<T> targetClass) {
		return targetClass.cast(content);
	}

	public Class<?> getType() {
		return type;
	}

	public boolean isValid() {
		return type != void.class;
	}

	protected Map<ParsingInstructions, String> getInstructionsInternal() {
		if (instructions == null) {
			instructions = new HashMap<ParsingInstructions, String>();
		}
		return instructions;
	}

	public boolean hasInstruction(ParsingInstructions instruction) {
		return getInstructionsInternal().containsKey(instruction);
	}

	public String getInstruction(ParsingInstructions instruction) {
		return getInstructionsInternal().get(instruction);
	}

	public void setInstruction(ParsingInstructions instruction, String value) {
		getInstructionsInternal().put(instruction, value);
	}

	protected Map<String, Object> getStoredObjectsInternal() {
		if (storedObjects == null) {
			storedObjects = new HashMap<String, Object>();
		}
		return storedObjects;
	}

	public boolean hasStoredObject(String key) {
		return getStoredObjectsInternal().containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getStoredObject(String key) {
		return (T) getStoredObjectsInternal().get(key);
	}

	public void storeObject(String key, Object object) {
		getStoredObjectsInternal().put(key, object);
	}
}
