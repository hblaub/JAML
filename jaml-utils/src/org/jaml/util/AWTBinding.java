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

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.jaml.api.IBinding;

public class AWTBinding<F extends Component, S extends Component, P> extends
		Binding<F, S, P> {
	private F firstComponent;
	private S secondComponent;
	private String property;

	public AWTBinding(F component1, String method1, S component2,
			String method2, Class<P> paramClass, String propertyName) {
		super(Delegate.create(component1, method1, paramClass), Delegate
				.create(component2, method2, paramClass));
		firstComponent = component1;
		secondComponent = component2;
		property = propertyName;
	}

	/**
	 * Creates a new instance of AWTBinding using the specified AWT components
	 * 
	 * @param component1
	 *            First component
	 * @param method1
	 *            First method
	 * @param component2
	 *            Second component
	 * @param method2
	 *            Second method
	 * @param paramClass
	 *            Parameter's class
	 * @param propertyName
	 *            Name of property to listen to
	 * @return new AWTBinding object
	 */
	public static <F extends Component, S extends Component, P> IBinding<F, S, P> create(
			F component1, String method1, S component2, String method2,
			Class<P> paramClass, String propertyName) {
		return new AWTBinding<F, S, P>(component1, method1, component2,
				method2, paramClass, propertyName);
	}

	@Override
	public boolean apply() {
		if (!areBothDelegatesValid()) {
			return false;
		}
		if (firstDelegate.needsParameter()) {
			secondComponent.addPropertyChangeListener(property,
					new PropertyChangeListener() {
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							firstDelegate.invoke(secondDelegate.invoke());
						}
					});
			return true;
		} else if (secondDelegate.needsParameter()) {
			firstComponent.addPropertyChangeListener(property,
					new PropertyChangeListener() {
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							secondDelegate.invoke(firstDelegate.invoke());
						}
					});
			return true;
		}
		return false;
	}
}
