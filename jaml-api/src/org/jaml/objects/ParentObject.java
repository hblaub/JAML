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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jaml.api.IElementOperator;

public class ParentObject {
	protected ParentObject parent;
	protected List<Element> children;

	protected ParentObject(ParentObject parentObject) {
		parent = parentObject;
	}

	public boolean hasParent() {
		return parent != null;
	}

	public boolean hasChildren() {
		return children != null && !children.isEmpty();
	}

	public ParentObject getParent() {
		return parent;
	}

	protected List<Element> getChildrenInternally() {
		if (children == null) {
			children = new LinkedList<Element>();
		}
		return children;
	}

	public Element add(Object object) {
		Element element = new Element(this, object);
		add(element);
		return element;
	}

	public void add(Element element) {
		getChildrenInternally().add(element);
	}

	public Iterator<Element> getIterator() {
		return getChildrenInternally().iterator();
	}

	public void each(IElementOperator elementOperator) {
		for (Element element : getChildrenInternally()) {
			elementOperator.process(element);
		}
	}
}
