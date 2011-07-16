package org.jaml.jsr223;

import java.util.Arrays;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class JamlScriptEngineFactory implements ScriptEngineFactory {

	@Override
	public String getEngineName() {
		return "JAML Script Engine";
	}

	@Override
	public String getEngineVersion() {
		return "0.0.1 alpha";
	}

	@Override
	public List<String> getExtensions() {
		return Arrays.asList(new String[] { "jaml" });
	}

	@Override
	public String getLanguageName() {
		return "JAML Script";
	}

	@Override
	public String getLanguageVersion() {
		return "1.0";
	}

	@Override
	public String getMethodCallSyntax(String arg0, String arg1, String... arg2) {
		return null;
	}

	@Override
	public List<String> getMimeTypes() {
		return Arrays.asList(new String[] { "application/xaml+xml" });
	}

	@Override
	public List<String> getNames() {
		return Arrays.asList(new String[] { "JAML" });
	}

	@Override
	public String getOutputStatement(String arg0) {
		return null;
	}

	@Override
	public Object getParameter(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProgram(String... arg0) {
		return null;
	}

	@Override
	public ScriptEngine getScriptEngine() {
		return new JamlScriptEngine(this);
	}
}
