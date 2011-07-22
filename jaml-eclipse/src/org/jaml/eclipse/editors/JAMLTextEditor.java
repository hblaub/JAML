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
package org.jaml.eclipse.editors;

import java.io.PrintWriter;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.ui.editors.text.TextEditor;

public class JAMLTextEditor extends TextEditor {

	private ColorManager colorManager;

	public static final Logger log = Logger.getLogger(JAMLTextEditor.class);

	public JAMLTextEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());

		/* Log4J begin */
		ConsoleAppender appender = new ConsoleAppender();
		appender.setWriter(new PrintWriter(System.out));
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern("%-4r [%t] %-5p %c %x - %m%n");
		appender.setLayout(layout);
		BasicConfigurator.configure(appender);
		/* Log4J end */

		log.debug("Created: " + getClass().getSimpleName());
	}

	public void dispose() {
		log.debug("Invoking dispose...");
		colorManager.dispose();
		super.dispose();
	}
}
