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
package org.jaml.tests;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.jaml.api.IBinding;
import org.jaml.api.IDelegate;
import org.jaml.util.Binding;
import org.jaml.util.Delegate;

public class BindingTest {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton btn = new JButton("TestMe");
		frame.add(btn);
		frame.setSize(640, 480);
		frame.setVisible(true);
		final IDelegate<JFrame, String> firstDelegate = Delegate.create(frame,
				"setTitle", String.class);
		final IBinding<JFrame, JButton, String> frToBtn = Binding.create(frame,
				"getTitle", btn, "setText", String.class);

		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				firstDelegate.invoke("TestMe! " + new Date());
				System.out.println(frToBtn.getFirstDelegate());
				System.out.println(frToBtn.getSecondDelegate());
				System.out.println(frToBtn.apply() + " !");
			}
		});
	}
}
