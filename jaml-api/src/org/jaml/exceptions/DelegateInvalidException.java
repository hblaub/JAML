package org.jaml.exceptions;

public class DelegateInvalidException extends RuntimeException {
	private static final long serialVersionUID = -2111825263307255415L;

	public DelegateInvalidException(Class<?> clazz, String methodName) {
		super(String.format("Method '%s' is not valid in class '%s'!",
				methodName, clazz.getName()));
	}
}
