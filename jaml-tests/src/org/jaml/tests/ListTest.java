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
import javax.swing.JList;
import javax.swing.JPanel;

import org.jaml.addons.ObservableCollection;
import org.jaml.addons.ObservableListModel;

public class ListTest {

	public static void main(String[] args) {
		final ObservableCollection<TestDTO> collection = new ObservableCollection<TestDTO>();
		collection.add(new TestDTO("Dummy!"));
		JList list = new JList(new ObservableListModel<TestDTO>(collection));

		JButton btn = new JButton("Add sth. to list?!");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				collection.add(new TestDTO(new Date() + ""));
			}
		});

		JFrame frame = new JFrame("ListTest");
		JPanel panel = new JPanel();
		panel.add(list);
		panel.add(btn);

		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setVisible(true);
	}
}
