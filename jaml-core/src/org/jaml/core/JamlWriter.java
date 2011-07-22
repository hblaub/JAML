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
package org.jaml.core;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.jaml.api.ITypeConverter;
import org.jaml.cache.Env;
import org.jaml.util.ReflectionUtils;
import org.jaml.util.ThreadUtils;

public class JamlWriter {
	private static final XMLOutputFactory factory = XMLOutputFactory
			.newInstance();

	private JamlWriter() {
	}

	public static String save(Object object) {
		Writer writer = new StringWriter();
		save(object, writer);
		return writer.toString();
	}

	public static boolean save(Object object, OutputStream stream) {
		return save(object, new OutputStreamWriter(stream));
	}

	public static boolean save(Object object, Writer writer) {
		try {
			XMLStreamWriter xml = factory.createXMLStreamWriter(writer);
			xml.writeStartElement(object.getClass().getSimpleName());
			xml.writeDefaultNamespace(ReflectionUtils
					.getXMLNamespaceByObject(object));
			Map<String, Object> map = ReflectionUtils
					.getDifferencingMethodsAndValuesOnly(object);
			ITypeConverter converter = null;
			String text = null;
			Object obj = null;
			for (String key : map.keySet()) {
				obj = map.get(key);
				converter = Env.get().getConverters().get(obj.getClass());
				if (converter != null) {
					text = converter.convertObject(obj);
					System.out.println(converter + " " + obj + " " + text);
					xml.writeAttribute(key, text);
				} else {
					System.err.println("No converter found for: "
							+ obj.getClass() + " " + obj);
				}
			}
			xml.writeEndElement();
			xml.flush();
			xml.close();
			return true;
		} catch (XMLStreamException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static Future<Boolean> saveAsync(final Object object,
			final Writer writer) {
		return ThreadUtils.exec(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return save(object, writer);
			}
		});
	}

	public static Future<Boolean> saveAsync(final Object object,
			final OutputStream stream) {
		return ThreadUtils.exec(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return save(object, stream);
			}
		});
	}

	public static Future<String> saveAsync(final Object object) {
		return ThreadUtils.exec(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return save(object);
			}
		});
	}
}
