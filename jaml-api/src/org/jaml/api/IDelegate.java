package org.jaml.api;

/**
 * Interface for invoking a method on a given object
 * 
 * @param <T>
 *            Object's class
 * @param <P>
 *            Parameter's class for invocation
 */
public interface IDelegate<T, P> {
	boolean isValid();

	void invoke(P value);
}
