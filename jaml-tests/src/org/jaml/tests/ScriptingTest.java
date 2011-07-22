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
package org.jaml.tests;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * JSR 223 test
 * 
 */
public class ScriptingTest {

	public static void main(String[] args) throws ScriptException {
		String extension = "jaml";
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine engine = scriptEngineManager
				.getEngineByExtension(extension);
		if (engine != null) {
			System.out.println(engine + " " + engine.getFactory());
			String input = "<JButton xmlns=\"http://java.com/namespaces/javax/swing\" Text=\"Click me\" />";
			System.out.println("Input: " + input);
			Object obj = engine.eval(input);
			System.out.println("Out: " + obj.getClass() + " (" + obj + ")");
		} else {
			System.out.println("No engine found for extension '" + extension
					+ "'!");
		}
	}
}
