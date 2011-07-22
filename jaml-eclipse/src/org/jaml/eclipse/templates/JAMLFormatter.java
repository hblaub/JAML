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
package org.jaml.eclipse.templates;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.jaml.eclipse.editors.IDocHandler;

public class JAMLFormatter {

	public static final String LINE_SEPARATOR = String.format("%n");

	public static String formatJAML(String text) {
		StringBuilder bld = new StringBuilder();
		int indent = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) != 10 && text.charAt(i) != 13) {
				if (i > 0 && text.charAt(i) == '<') {
					bld.append(LINE_SEPARATOR
							+ (text.charAt(i + 1) != '/' ? getSpaces(indent)
									: getSpaces(indent - 1)));
				}
				bld.append(text.charAt(i));
				if (i > 0) {
					if (text.charAt(i - 1) == '<') {
						if (text.charAt(i) != '/') {
							// Start tag
							indent++;
						} else {
							// End tag
							indent--;
						}
					}
				}
			}
		}
		return bld.toString();
	}

	public static String getSpaces(int indent) {
		return getChars(indent, ' ');
	}

	public static String getChars(int indent, char letter) {
		StringBuilder bld = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			bld.append(letter);
		}
		return bld.toString();
	}

	public static String getDebugStr(String text) {
		StringBuilder bld = new StringBuilder();
		for (char c : text.toCharArray()) {
			bld.append(c);
			bld.append("(" + ((int) c) + ")");
		}
		return bld.toString();
	}

	private static final IDocHandler DOC_HANDLER = new IDocHandler() {
		@Override
		public void handle(ICompletionProposal proposal, IDocument doc) {
			String orig = doc.get();
			String formatted = formatJAML(orig);
			doc.set(formatted);
		}
	};

	public static IDocHandler getIDocHandler() {
		return DOC_HANDLER;
	}
}
