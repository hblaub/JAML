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
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.jaml.api.ICallbackEventHandler;
import org.jaml.core.Unit;
import org.jaml.patches.WebClient;

public class TwitterWindow extends Unit<JFrame> {
	private Collection<String> _list;
	private JTextField _searchWord;

	public TwitterWindow() {
		_list = get("list");
		_searchWord = get("teSearchWord");
	}

	public void getTweets_Click(ActionEvent event) throws URISyntaxException {
		WebClient client = new WebClient();
		client.downloadStringCompleted(new ICallbackEventHandler() {
			@Override
			public void callback(Object s, Object ea) {
				List<String> titleList = new LinkedList<String>();
				try {
					// String ns = "http://www.w3.org/2005/Atom";
					StringReader stringReader = new StringReader(ea.toString());
					XMLStreamReader reader = XMLInputFactory.newInstance()
							.createXMLStreamReader(stringReader);
					while (reader.hasNext()) {
						int event = reader.next();
						if (event == XMLEvent.START_ELEMENT
								&& "entry".equals(reader.getLocalName())) {
							String title = "";
							while (reader.hasNext()) {
								int subEvent = reader.next();
								if (XMLEvent.START_ELEMENT == subEvent
										&& "title".equals(reader.getLocalName())) {
									title = reader.getElementText();
									break;
								}
							}
							titleList.add(title);
						}
					}
					_list.clear();
					for (String title : titleList) {
						_list.add(title);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		if (_searchWord.getText() == null) {
			return;
		}
		String searchText = _searchWord.getText().trim();
		if (searchText.isEmpty()) {
			return;
		}
		client.downloadStringAsync(new URI(String.format(
				"http://search.twitter.com/search.atom?q=%s", searchText)));
	}
}
