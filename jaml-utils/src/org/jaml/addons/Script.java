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
package org.jaml.addons;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * The Script class allows one to code directly in JAML with free choice of a
 * Java scripting language.
 */
public class Script {
	private ScriptEngineManager engineManager;
	private ScriptEngine scriptEngine;

	private String scriptExtension;
	private String scriptMimeType;
	private String scriptEngineName;
	private String scriptContent;
	private Class<?> scriptTargetType;

	public Script() {
		engineManager = new ScriptEngineManager();
	}

	public boolean isValid() {
		return scriptEngine != null && scriptTargetType != void.class
				&& scriptContent != null && !scriptContent.isEmpty();
	}

	public String getContent() {
		return scriptContent;
	}

	public String getEngineName() {
		return scriptEngineName;
	}

	public String getExtension() {
		return scriptExtension;
	}

	public String getMimeType() {
		return scriptMimeType;
	}

	public Class<?> getTargetType() {
		return scriptTargetType;
	}

	public ScriptEngine getScriptEngine() {
		return scriptEngine;
	}

	public void setContent(String content) {
		scriptContent = content;
	}

	public void setEngineName(String engineName) {
		scriptEngineName = engineName;
		scriptEngine = engineManager.getEngineByName(engineName);
	}

	public void setExtension(String extension) {
		scriptExtension = extension;
		scriptEngine = engineManager.getEngineByExtension(extension);
	}

	public void setMimeType(String mimeType) {
		scriptMimeType = mimeType;
		scriptEngine = engineManager.getEngineByMimeType(mimeType);
	}

	public void setTargetType(String targetType) {
		try {
			scriptTargetType = Class.forName(targetType);
		} catch (ClassNotFoundException e) {
			scriptTargetType = void.class;
		}
	}

	public <T> T compile(Class<T> targetClass) {
		if (scriptEngine != null) {
			try {
				scriptEngine.eval(scriptContent);
				T result = ((Invocable) scriptEngine).getInterface(targetClass);
				return result;
			} catch (ScriptException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
