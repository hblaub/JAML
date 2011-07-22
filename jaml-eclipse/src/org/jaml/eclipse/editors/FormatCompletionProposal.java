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

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

public class FormatCompletionProposal implements ICompletionProposal {
	private ICompletionProposal proposal;
	private IDocHandler handler;

	public FormatCompletionProposal(ICompletionProposal proposal,
			IDocHandler handler) {
		this.proposal = proposal;
		this.handler = handler;
	}

	@Override
	public void apply(IDocument doc) {
		proposal.apply(doc);
		handler.handle(proposal, doc);
	}

	@Override
	public String getAdditionalProposalInfo() {
		return proposal.getAdditionalProposalInfo();
	}

	@Override
	public IContextInformation getContextInformation() {
		return proposal.getContextInformation();
	}

	@Override
	public String getDisplayString() {
		return proposal.getDisplayString();
	}

	@Override
	public Image getImage() {
		return proposal.getImage();
	}

	@Override
	public Point getSelection(IDocument doc) {
		return proposal.getSelection(doc);
	}
}
