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

import java.util.Collection;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * The class ObservableListModel is an observer of the ObservableCollection and
 * a ListModel at the same time. As a result of this, it is the bridge between
 * the changeable collection and UI elements like the Swing JList. It informs
 * the UI element about any change reported by the used ObservableCollection and
 * therefore is designed to simplify development.
 * 
 * @param <E>
 *            type of the ObservableCollection
 */
public class ObservableListModel<E> implements ListModel, Observer {
	protected Collection<ListDataListener> listeners;
	protected ObservableCollection<E> observableCollection;

	/**
	 * Constructor for using an existing ObservableCollection as a source
	 * 
	 * @param collection
	 *            : instance of ObservableCollection
	 */
	public ObservableListModel(ObservableCollection<E> collection) {
		listeners = new LinkedList<ListDataListener>();
		collection.addObserver(this);
		observableCollection = collection;
	}

	@Override
	public void addListDataListener(ListDataListener listDataListener) {
		listeners.add(listDataListener);
	}

	/**
	 * Fetches the element at a given index in a collection
	 * 
	 * @param collection
	 *            collection to be searched
	 * @param index
	 *            index of the item to fetch
	 * @return the item or null if not found
	 */
	protected static <E> E fetchElementAtIndex(Collection<E> collection,
			int index) {
		int currentIndex = 0;
		for (E item : collection) {
			if (currentIndex == index)
				return item;
			currentIndex++;
		}
		return null;
	}

	@Override
	public E getElementAt(int index) {
		return fetchElementAtIndex(observableCollection, index);
	}

	@Override
	public int getSize() {
		return observableCollection.size();
	}

	@Override
	public void removeListDataListener(ListDataListener listDataListener) {
		listeners.add(listDataListener);
	}

	/**
	 * Informs all registered ListDataListeners about the change
	 */
	protected void informDataListeners() {
		ListDataEvent event = new ListDataEvent(this,
				ListDataEvent.CONTENTS_CHANGED, 0, 0);
		for (ListDataListener listDataListener : listeners) {
			listDataListener.contentsChanged(event);
		}
	}

	@Override
	public void update(Observable observable, Object object) {
		informDataListeners();
	}
}
