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
package org.jaml.patches;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

/**
 * The class FileClassLoader allows one to load a JAR or a class file without
 * worries about the current Java class path. In fact, it bypasses the default
 * class loading process to gain more control over the fact which class will be
 * loaded.
 */
public class FileClassLoader extends URLClassLoader {
	protected ClassLoader fallbackLoader;

	public FileClassLoader(File... files) {
		super(convertFilesToURLs(files), new DummyClassLoader());
		fallbackLoader = ClassLoader.getSystemClassLoader();
	}

	public FileClassLoader(String... paths) {
		this(convertPathsToFiles(paths));
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			return super.findClass(name);
		} catch (ClassNotFoundException cnfe) {
			return fallbackLoader.loadClass(name);
		}
	}

	protected static File[] convertPathsToFiles(String[] paths) {
		File[] files = new File[paths.length];
		for (int i = 0; i < paths.length; i++) {
			files[i] = new File(paths[i]);
		}
		return files;
	}

	protected static URL[] convertFilesToURLs(File[] files) {
		List<URL> urls = new LinkedList<URL>();
		for (File file : files) {
			if (file.exists()) {
				try {
					urls.add(new URL(String.format("jar:%s!/", file.toURI())));
				} catch (MalformedURLException e) {
					// Silent catch
				}
			}
		}
		return urls.toArray(new URL[urls.size()]);
	}
}
