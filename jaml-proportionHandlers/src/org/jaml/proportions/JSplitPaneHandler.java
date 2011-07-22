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

import java.awt.Component;

import javax.swing.JSplitPane;

import org.jaml.api.AbstractProportionHandler;

public class JSplitPaneHandler extends
		AbstractProportionHandler<JSplitPane, Component> {
	protected JSplitPane referencePane;

	public JSplitPaneHandler() {
		super(JSplitPane.class, Component.class);
		referencePane = new JSplitPane();
	}

	@Override
	protected void processWithTypes(JSplitPane parent, Component child) {
		if (parent.getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
			if (parent.getLeftComponent().getClass() == referencePane
					.getLeftComponent().getClass()) {
				parent.setLeftComponent(child);
			} else {
				parent.setRightComponent(child);
			}
		} else if (parent.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
			if (parent.getTopComponent().getClass() == referencePane
					.getTopComponent().getClass()) {
				parent.setTopComponent(child);
			} else {
				parent.setBottomComponent(child);
			}
		}
	}
}
