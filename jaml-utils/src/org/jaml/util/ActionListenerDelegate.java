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
package org.jaml.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class ActionListenerDelegate is designed to invoke nearly every possible
 * method as an action, regardless of its name. So one can insert more than one
 * action-handling method in a single class file.
 * 
 * @param <T>
 *            method-owning Java type
 */
public class ActionListenerDelegate<T> extends Delegate<T, ActionEvent>
		implements ActionListener {

	public ActionListenerDelegate(T object, String methodName) {
		super(object, methodName, ActionEvent.class);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		invoke(event);
	}
}
