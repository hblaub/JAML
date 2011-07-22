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

import java.awt.Color;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.jaml.core.JamlReader;
import org.jaml.core.JamlWriter;

public class TestCLI {

	public static void main(String[] args) {
		try {
			System.out
					.println("<Button Background=\"#FFF0F8FF\" Width=\"100\" Height=\"50\" "
							+ "xmlns=\"http://schemas.microsoft.com/winfx/2006/xaml/presentation\">Click Me</Button>");

			// Create the Button
			JButton originalButton = new JButton();
			originalButton.setSize(50, 100);
			originalButton.setBackground(Color.BLUE);
			originalButton.setText("Click me");

			// Save the Button to a String
			String savedButton = JamlWriter.save(originalButton);
			System.out.println(savedButton);

			// Load the button
			Future<JButton> readerLoadButton = JamlReader
					.loadAndUnwrapAsync(savedButton);

			// Output
			JFrame frame = new JFrame("Test");
			frame.setSize(400, 200);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// Async
			Thread.sleep(3000);
			frame.add(readerLoadButton.get());
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
