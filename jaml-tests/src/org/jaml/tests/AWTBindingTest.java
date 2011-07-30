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

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jaml.api.IBinding;
import org.jaml.util.AWTBinding;

public class AWTBindingTest {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		final JButton btn = new JButton("TestMe");
		final JFormattedTextField tf = new JFormattedTextField();
		tf.setValue(new Double(10.0));
		tf.setColumns(10);
		panel.add(btn);
		panel.add(tf);

		frame.add(panel);
		frame.setSize(640, 480);
		frame.setVisible(true);

		IBinding<JFormattedTextField, JButton, String> awtCompBnd = AWTBinding
				.create(tf, "getText", btn, "setText", String.class, "value");
		System.out.println(awtCompBnd.apply());

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tf.setValue(Math.random() * 100.0);
			}
		});
	}
}
