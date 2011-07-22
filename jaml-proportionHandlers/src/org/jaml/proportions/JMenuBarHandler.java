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

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.jaml.api.AbstractProportionHandler;

public class JMenuBarHandler extends
		AbstractProportionHandler<JFrame, JMenuBar> {

	public JMenuBarHandler() {
		super(JFrame.class, JMenuBar.class);
	}

	@Override
	protected void processWithTypes(JFrame parent, JMenuBar child) {
		parent.setJMenuBar(child);
	}
}
