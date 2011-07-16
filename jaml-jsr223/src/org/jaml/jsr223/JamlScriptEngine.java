package org.jaml.jsr223;

import java.io.Reader;
import java.io.StringReader;

import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.jaml.core.JamlReader;

public class JamlScriptEngine extends AbstractScriptEngine {
	protected ScriptEngineFactory factory;

	public JamlScriptEngine(ScriptEngineFactory scriptEngineFactory) {
		factory = scriptEngineFactory;
	}

	@Override
	public Bindings createBindings() {
		return new SimpleBindings();
	}

	@Override
	public Object eval(String script, ScriptContext context)
			throws ScriptException {
		return eval(new StringReader(script), context);
	}

	@Override
	public Object eval(Reader reader, ScriptContext context)
			throws ScriptException {
		return JamlReader.load(reader);
	}

	@Override
	public ScriptEngineFactory getFactory() {
		return factory;
	}
}
