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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;

import org.jaml.exceptions.ObservableInstantiationException;

/**
 * The class ObservableCollection contains a collection, monitors changes and is
 * a collection itself at the same time. One possible use case are UI elements,
 * which have to be informed about any changes in the collection.
 * 
 * @param <E>
 *            type of the internal collection
 */
public class ObservableCollection<E> extends Observable implements
		Collection<E> {
	protected Collection<E> internalCollection;

	/**
	 * Constructor for using an already existing collection
	 * 
	 * @param source
	 *            a collection
	 */
	public ObservableCollection(Collection<E> source) {
		internalCollection = source;
	}

	/**
	 * Constructor for using a new designed collection class
	 * 
	 * @param collectionClass
	 *            collection class to be instantiated
	 * @throws ObservableInstantiationException
	 *             if no new instance of the collection can be created
	 */
	public ObservableCollection(Class<? extends Collection<E>> collectionClass) {
		try {
			internalCollection = collectionClass.newInstance();
		} catch (Exception exception) {
			throw new ObservableInstantiationException(collectionClass,
					exception);
		}
	}

	/**
	 * Default constructor, using a new LinkedList instance
	 */
	public ObservableCollection() {
		this(new LinkedList<E>());
	}

	/**
	 * Sends changes to the observers
	 */
	protected void sendChange() {
		setChanged();
		notifyObservers();
	}

	@Override
	public boolean add(E item) {
		sendChange();
		return internalCollection.add(item);
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		sendChange();
		return internalCollection.addAll(collection);
	}

	@Override
	public void clear() {
		sendChange();
		internalCollection.clear();
	}

	@Override
	public boolean contains(Object object) {
		return internalCollection.contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return internalCollection.containsAll(collection);
	}

	@Override
	public boolean isEmpty() {
		return internalCollection.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return internalCollection.iterator();
	}

	@Override
	public boolean remove(Object object) {
		sendChange();
		return internalCollection.remove(object);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		sendChange();
		return internalCollection.removeAll(internalCollection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		sendChange();
		return internalCollection.retainAll(collection);
	}

	@Override
	public int size() {
		return internalCollection.size();
	}

	@Override
	public Object[] toArray() {
		return internalCollection.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return internalCollection.toArray(array);
	}
}
