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
package org.jaml.addons;

import java.awt.Component;

import org.jaml.api.ILayoutItem;

public class LayoutItem implements ILayoutItem {
	private Component component;
	private Object constraints;

	@Override
	public Component getComponent() {
		return component;
	}

	@Override
	public void setComponent(Component component) {
		this.component = component;
	}

	@Override
	public Object getConstraints() {
		return constraints;
	}

	@Override
	public void setConstraints(Object constraints) {
		this.constraints = constraints;
	}
}
