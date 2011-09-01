package org.jaml.addons;

import java.awt.Component;

import org.jaml.api.ILayoutItem;

public class LayoutItem implements ILayoutItem {
	private Component component;
	private Object constraints;

	@Override
	public Component getComponent() {
		return component;
	}

	@Override
	public void setComponent(Component component) {
		this.component = component;
	}

	@Override
	public Object getConstraints() {
		return constraints;
	}

	@Override
	public void setConstraints(Object constraints) {
		this.constraints = constraints;
	}
}
