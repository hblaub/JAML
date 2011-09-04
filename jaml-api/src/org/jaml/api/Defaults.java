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
package org.jaml.api;

import java.util.Locale;

/**
 * System-wide defaults for JAML parser instances, contains for example file
 * endings
 */
public class Defaults {

	/**
	 * XML name space prefix for accessing all Java classes
	 */
	public static final String namespacePrefix = "http://java.com/namespaces/";

	/**
	 * XML name space for accessing special features of the JAML parser
	 */
	public static final String parserNamespace = "http://jaml.com/parser/2011/lang/additions";

	/**
	 * The parser's default locale if nothing else is set
	 */
	public static final Locale standardLocale = Locale.ENGLISH;

	/**
	 * Default file extension
	 */
	public static final String fileExtension = "jaml";

	/**
	 * Alternative file extension
	 */
	public static final String alternativeExtension = "xml";

	/**
	 * Empty string object to avoid creating always a new one
	 */
	public static final String emptyString = "";

	/**
	 * Name space separator for specifying the source of a Java class
	 */
	public static final String namespaceSourceSeparator = "?src=";

	/**
	 * MarkupLoader's begin character
	 */
	public static final char markupBegin = '{';

	/**
	 * MarkupLoader's end character
	 */
	public static final char markupEnd = '}';
}
