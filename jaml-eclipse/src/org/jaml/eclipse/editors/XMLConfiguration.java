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

import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class XMLConfiguration extends SourceViewerConfiguration {
	private XMLDoubleClickStrategy doubleClickStrategy;
	private XMLTagScanner tagScanner;
	private XMLScanner scanner;
	private ColorManager colorManager;

	public XMLConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
				XMLPartitionScanner.XML_COMMENT, XMLPartitionScanner.XML_TAG };
	}

	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new XMLDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected XMLScanner getXMLScanner() {
		if (scanner == null) {
			scanner = new XMLScanner(colorManager);
			scanner.setDefaultReturnToken(new Token(new TextAttribute(
					colorManager.getColor(IXMLColorConstants.DEFAULT))));
		}
		return scanner;
	}

	protected XMLTagScanner getXMLTagScanner() {
		if (tagScanner == null) {
			tagScanner = new XMLTagScanner(colorManager);
			tagScanner.setDefaultReturnToken(new Token(new TextAttribute(
					colorManager.getColor(IXMLColorConstants.TAG))));
		}
		return tagScanner;
	}

	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(
				getXMLTagScanner());
		reconciler.setDamager(dr, XMLPartitionScanner.XML_TAG);
		reconciler.setRepairer(dr, XMLPartitionScanner.XML_TAG);

		dr = new DefaultDamagerRepairer(getXMLScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
				new TextAttribute(
						colorManager.getColor(IXMLColorConstants.XML_COMMENT)));
		reconciler.setDamager(ndr, XMLPartitionScanner.XML_COMMENT);
		reconciler.setRepairer(ndr, XMLPartitionScanner.XML_COMMENT);

		return reconciler;
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		// Create content assistant
		ContentAssistant assistant = new ContentAssistant();
		// Create content assistant processor
		IContentAssistProcessor processor = new JamlContentAssistProcessor();
		// Set this processor for each supported content type
		assistant.setContentAssistProcessor(processor,
				XMLPartitionScanner.XML_TAG);
		assistant.setContentAssistProcessor(processor,
				XMLPartitionScanner.XML_COMMENT);
		assistant.setContentAssistProcessor(processor,
				IDocument.DEFAULT_CONTENT_TYPE);
		// Add detail information window
		assistant
				.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		// Enable auto completion proposals
		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(500);
		// Change background color
		Color bgColor = colorManager.getColor(new RGB(250, 250, 250));
		assistant.setProposalSelectorBackground(bgColor);
		// Return the content assistant
		return assistant;
	}

	private static final DefaultInformationControl.IInformationPresenter presenter = new DefaultInformationControl.IInformationPresenter() {
		@Override
		public String updatePresentation(Display display, String infoText,
				TextPresentation presentation, int maxWidth, int maxHeight) {
			int start = -1;
			// Loop over all characters of information text
			for (int i = 0; i < infoText.length(); i++) {
				switch (infoText.charAt(i)) {
				case '<':
					// Remember start of tag
					start = i;
					break;
				case '>':
					if (start >= 0) {
						// We have found a tag and create a new style range
						StyleRange range = new StyleRange(start, i - start + 1,
								null, null, SWT.BOLD);
						// Add this style range to the presentation
						presentation.addStyleRange(range);
						// Reset tag start indicator
						start = -1;
					}
					break;
				}
			}
			// Return the information text
			return infoText;
		}
	};

	@Override
	public IInformationControlCreator getInformationControlCreator(
			ISourceViewer sourceViewer) {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				return new DefaultInformationControl(parent, presenter);
			}
		};
	}
}
