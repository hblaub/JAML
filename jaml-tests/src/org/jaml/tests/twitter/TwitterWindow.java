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
package org.jaml.tests.twitter;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.jaml.core.Unit;
import org.jaml.util.ActionListenerDelegate;

public class TwitterWindow extends Unit<JFrame> {
	private Collection<String> list;

	public TwitterWindow() {
		list = get("list");
		JButton addBtn = get("addButton");
		addBtn.addActionListener(new ActionListenerDelegate<TwitterWindow>(
				this, "add_button"));
		JButton delBtn = get("delButton");
		delBtn.addActionListener(new ActionListenerDelegate<TwitterWindow>(
				this, "del_button"));
	}

	public void add_button(ActionEvent event) {
		list.add(new Date() + "");
	}

	public void del_button(ActionEvent event) {
		list.remove(list.iterator().next());
	}
}
