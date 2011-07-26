package org.jaml.exceptions;

import java.lang.reflect.Method;

public class DelegateException extends RuntimeException {
	private static final long serialVersionUID = -5132908357722861884L;

	public DelegateException(Class<?> clazz, Method method, Object value,
			Exception exception) {
		super(
				String.format(
						"Method '%s' of class '%s' could not be invoked with value '%s'!",
						method, clazz.getName(), value), exception);
	}
}
