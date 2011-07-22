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
package org.jaml.cache;

import org.jaml.api.IExtensionLoader;
import org.jaml.api.IMarkupExtension;
import org.jaml.api.IProportionHandler;
import org.jaml.api.ITypeConverter;
import org.jaml.loaders.MarkupLoader;
import org.jaml.loaders.ProportionHandlerLoader;
import org.jaml.loaders.TypeConverterLoader;
import org.jaml.structs.Pair;

public class Env {

	private static Env instance;

	public static Env get() {
		if (instance == null) {
			instance = new Env();
		}
		return instance;
	}

	private IExtensionLoader<ITypeConverter, Class<?>> converters;
	private IExtensionLoader<IMarkupExtension, String> markups;
	private IExtensionLoader<IProportionHandler, Pair<Class<?>, Class<?>>> proportions;

	private Env() {
		this.converters = new TypeConverterLoader();
		this.markups = new MarkupLoader();
		this.proportions = new ProportionHandlerLoader();
	}

	public IExtensionLoader<ITypeConverter, Class<?>> getConverters() {
		return converters;
	}

	public IExtensionLoader<IMarkupExtension, String> getMarkups() {
		return markups;
	}

	public IExtensionLoader<IProportionHandler, Pair<Class<?>, Class<?>>> getProportions() {
		return proportions;
	}
}
