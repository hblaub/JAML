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
package org.jaml.structs;

public class ClassInfo {
	private String classPackage;
	private String className;
	private String classSource;

	public ClassInfo(String classPackage, String className, String classSource) {
		this.classPackage = classPackage;
		this.className = className;
		this.classSource = classSource;
	}

	public String getClassName() {
		return className;
	}

	public String getClassPackage() {
		return classPackage;
	}

	public String getClassSource() {
		return classSource;
	}

	public boolean hasSource() {
		return classSource != null && !classSource.isEmpty();
	}

	public String getFQN() {
		return String.format("%s.%s", classPackage, className);
	}

	public String getFQP() {
		return classSource == null ? getFQN() : String.format("%s:%s",
				classSource, getFQN());
	}
}
