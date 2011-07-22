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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.jaml.eclipse.Activator;
import org.jaml.eclipse.templates.Defaults;
import org.jaml.eclipse.utils.JDTUtils;

public class JamlContentAssistProcessor implements IContentAssistProcessor {

	public static final String TYPE_PNG = "TYPE_PNG";
	public static final String CLASS_PNG = "CLASS_PNG";
	public static final String PROP_PNG = "PROP_PNG";

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int documentOffset) {
		List<ICompletionProposal> propList = new LinkedList<ICompletionProposal>();
		// Retrieve current document
		IDocument doc = viewer.getDocument();
		try {
			if (JDTUtils.checkDocToChars(doc, documentOffset, '"', '=')) {
				String tag = JDTUtils.getLastTag(doc, documentOffset);
				IType type = JDTUtils.getITypeFromTag(tag);
				String attr = JDTUtils.getLastAttribute(doc, documentOffset);
				List<String> argTypes = JDTUtils.getTypesByAttr(type, attr);
				computeTypeProposals(argTypes, documentOffset, propList);
			} else if (JDTUtils.checkDocToChars(doc, documentOffset, ' ')) {
				String tag = JDTUtils.getLastTag(doc, documentOffset);
				IType type = JDTUtils.getITypeFromTag(tag);
				List<String> usedAttrs = JDTUtils.getAlreadyUsedAttrs(doc,
						documentOffset);
				computeAttributeProposals(type, usedAttrs, documentOffset,
						propList);
			} else {
				// Tag has begun
				String qualifier = JDTUtils.getQualifier(doc, documentOffset);
				// Compute completion proposals
				computeStructureProposals(qualifier, documentOffset, propList);
			}
		} catch (BadLocationException e) {
			JAMLTextEditor.log.debug(e.getClass(), e);
		} catch (JavaModelException e) {
			JAMLTextEditor.log.debug(e.getClass(), e);
		}
		// Create completion proposal array, fill with list elements and return
		// the proposals
		return propList.toArray(new ICompletionProposal[propList.size()]);
	}

	private void computeTypeProposals(List<String> types, int documentOffset,
			List<ICompletionProposal> propList) {
		for (String type : types) {
			IContextInformation information = new ContextInformation(null, type);
			String value = Defaults.valueMap.get(type);
			value = value == null ? "unknown" : value;
			String specialText = String
					.format("Java class of this property: %n %s %n %n Possible value: %n %s %n",
							type, value);
			ICompletionProposal proposal = new CompletionProposal(value,
					documentOffset, 0, value.length(), Activator.getDefault()
							.getImageRegistry().get(TYPE_PNG), type,
					information, specialText);
			propList.add(proposal);
		}
	}

	private void computeAttributeProposals(IType type, List<String> usedAttrs,
			int documentOffset, List<ICompletionProposal> propList) {
		List<String> propertiesList = JDTUtils.getListOfProperties(type);
		propertiesList.removeAll(usedAttrs);
		for (String attribute : propertiesList) {
			IContextInformation information = new ContextInformation(null,
					attribute);
			String replaceTxt = attribute + "=\"\"";
			String specialText = String.format(
					"Java class: %n %s %n %n Property: %n %s %n",
					type.getFullyQualifiedName(), attribute);
			ICompletionProposal proposal = new CompletionProposal(replaceTxt,
					documentOffset, 0, attribute.length() + 2, Activator
							.getDefault().getImageRegistry().get(PROP_PNG),
					attribute, information, specialText);
			propList.add(proposal);
		}
	}

	private void computeStructureProposals(String qualifier,
			int documentOffset, List<ICompletionProposal> propList)
			throws JavaModelException {
		List<IType> list = /* JDTUtils.filterByPackage( */Activator
				.getDefault().getAvailableTypes()/* , "javax.swing") */;
		IType type = null;
		int qlen = qualifier.length();
		// Loop through all proposals
		for (int i = 0; i < list.size(); i++) {
			type = list.get(i);
			String startTag = JDTUtils.getProposalPartBeforeCursor(type
					.getElementName());
			// Check if proposal matches qualifier
			if (startTag.startsWith(qualifier)) {
				// Yes -- compute whole proposal text
				String text = startTag
						+ JDTUtils.getProposalPartAfterCursor(type
								.getElementName());
				// Derive cursor position
				int cursor = startTag.length();
				// Construct proposal
				IContextInformation contextInfo = new ContextInformation(null,
						type.getFullyQualifiedName());
				IJavaElement highestRoot = getHighestRoot(type);
				String info = String
						.format("%s %n %n Fully qualified name: %n %s %n %n Defined by: %n %s %n",
								text, type.getFullyQualifiedName(),
								highestRoot.getElementName());
				ICompletionProposal proposal = /* new FormatCompletionProposal( */
				new CompletionProposal(text, documentOffset - qlen, qlen,
						cursor, Activator.getDefault().getImageRegistry()
								.get(CLASS_PNG), type.getElementName(),
						contextInfo, info)/*
										 * , JAMLFormatter.getIDocHandler())
										 */;
				// and add to result list
				propList.add(proposal);
			}
		}
	}

	private IJavaElement getHighestRoot(IJavaElement element) {
		return element.getElementType() != IJavaElement.PACKAGE_FRAGMENT_ROOT
				&& element.getElementType() != IJavaElement.JAVA_PROJECT
				&& element.getParent() != null ? getHighestRoot(element
				.getParent()) : element;
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int documentOffset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '<', ' ' };
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return new char[] { '<' };
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return new IContextInformationValidator() {
			@Override
			public boolean isContextInformationValid(int value) {
				return true;
			}

			@Override
			public void install(IContextInformation info, ITextViewer viewer,
					int value) {
			}
		};
	}

	@Override
	public String getErrorMessage() {
		return null;
	}
}
