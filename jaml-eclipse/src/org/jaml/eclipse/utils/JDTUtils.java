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
package org.jaml.eclipse.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;
import org.jaml.eclipse.Activator;

public class JDTUtils {

	public static List<IType> iterateJavaProjects() {
		List<IType> list = new LinkedList<IType>();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsRoot = workspace.getRoot();
		IProject[] projects = wsRoot.getProjects();
		for (IProject project : projects) {
			IJavaProject javaProject = JavaCore.create(project);
			if (javaProject != null) {
				try {
					list.addAll(findJavaTypes(javaProject));
				} catch (JavaModelException jme) {
					jme.printStackTrace();
				}
			}
		}
		return list;
	}

	public static List<IType> findJavaTypes(IJavaProject javaProject)
			throws JavaModelException {
		List<IType> list = new LinkedList<IType>();
		findJavaTypes(list, javaProject.getAllPackageFragmentRoots());
		return list;
	}

	public static void findJavaTypes(List<IType> list, IJavaElement[] elements)
			throws JavaModelException {
		for (IJavaElement element : elements) {
			findJavaTypes(list, element);
		}
	}

	public static void findJavaTypes(List<IType> list, IJavaElement element)
			throws JavaModelException {
		switch (element.getElementType()) {
		case IJavaElement.JAVA_PROJECT:
			// Java project
			IJavaProject proj = (IJavaProject) element;
			findJavaTypes(list, proj.getChildren());
			break;
		case IJavaElement.PACKAGE_FRAGMENT_ROOT:
			// JAR file
			IPackageFragmentRoot pkgRoot = (IPackageFragmentRoot) element;
			findJavaTypes(list, pkgRoot.getChildren());
			break;
		case IJavaElement.PACKAGE_FRAGMENT:
			// Java package
			IPackageFragment pkgFragment = (IPackageFragment) element;
			findJavaTypes(list, pkgFragment.getChildren());
			break;
		case IJavaElement.COMPILATION_UNIT:
			// Source file (.java)
			ICompilationUnit cmUnit = (ICompilationUnit) element;
			findJavaTypes(list, cmUnit.getTypes());
			break;
		case IJavaElement.CLASS_FILE:
			// Compiled file (.class)
			IClassFile clFile = (IClassFile) element;
			findJavaTypes(list, clFile.getType());
			break;
		case IJavaElement.TYPE:
			// Java class
			IType type = (IType) element;
			if (!type.getFullyQualifiedName().contains("$")) {
				list.add(type);
			}
			break;
		}
	}

	public static void sortTypeList(List<IType> types) {
		Collections.sort(types, new Comparator<IType>() {
			@Override
			public int compare(IType type1, IType type2) {
				return type1.getElementName().compareTo(type2.getElementName());
			}
		});
	}

	public static List<IType> filterByPackage(List<IType> types,
			String packageName) {
		List<IType> filteredTypes = new LinkedList<IType>();
		for (IType type : types) {
			if (type.getFullyQualifiedName().startsWith(packageName)) {
				filteredTypes.add(type);
			}
		}
		return filteredTypes;
	}

	public static List<String> getListOfProperties(IType type) {
		try {
			List<IMethod> allMethods = new LinkedList<IMethod>();
			fetchMethods(allMethods, type);
			return getListOfProperties(allMethods);
		} catch (JavaModelException e) {
			// Deliver empty list
			return new LinkedList<String>();
		}
	}

	public static void fetchMethods(List<IMethod> list, IType type)
			throws JavaModelException {
		if (type != null) {
			// Fetch all methods from current type
			for (IMethod method : type.getMethods()) {
				list.add(method);
			}
			// Get superclass
			IType superClassType = getITypeByName(Activator.getDefault()
					.getAvailableTypes(), type.getSuperclassName());
			fetchMethods(list, superClassType);
			// Get super interfaces
			String[] superInterfaces = type.getSuperInterfaceNames();
			IType superInterfaceType = null;
			for (String superInterface : superInterfaces) {
				superInterfaceType = getITypeByName(Activator.getDefault()
						.getAvailableTypes(), superInterface);
				fetchMethods(list, superInterfaceType);
			}
		}
	}

	public static IType getITypeByName(List<IType> list, String fQN) {
		for (IType type : list) {
			if (type.getFullyQualifiedName().equals(fQN)) {
				return type;
			}
		}
		return null;
	}

	public static List<String> getListOfProperties(Iterable<IMethod> methods) {
		List<String> listOfGetters = new LinkedList<String>();
		List<String> listOfSetters = new LinkedList<String>();
		String name = null;
		String attrName = null;
		for (IMethod method : methods) {
			name = method.getElementName();
			if (name.length() > 3) {
				attrName = name.substring(3);
				if (name.startsWith("get")) {
					listOfGetters.add(attrName);
				} else if (name.startsWith("set")) {
					listOfSetters.add(attrName);
				} else if (name.startsWith("is")) {
					listOfGetters.add(name.substring(2));
				}
			}
		}
		// Keep only the property names which are in the setters list, too
		listOfGetters.retainAll(listOfSetters);
		// Convert to set, because sets cannot have duplicates
		listOfGetters = new LinkedList<String>(new HashSet<String>(
				listOfGetters));
		// Sort list
		Collections.sort(listOfGetters);
		return listOfGetters;
	}

	public static List<String> getTypesByAttr(IType type, String attribute)
			throws JavaModelException {
		List<String> types = new LinkedList<String>();
		List<IMethod> methods = new LinkedList<IMethod>();
		fetchMethods(methods, type);
		String tmp = null;
		for (IMethod method : methods) {
			if (method.getElementName().equals("set" + attribute)) {
				if (method.getParameterTypes().length == 1) {
					tmp = method.getParameterTypes()[0];
					tmp = (tmp.startsWith("L") && tmp.endsWith(";")) ? tmp
							.substring(1, tmp.length() - 1) : tmp;
					/*
					 * method.getTypeRoot().findPrimaryType().getFullyQualifiedName
					 * () tmp = + tmp;
					 */
					if (!types.contains(tmp)) {
						types.add(tmp);
					}
				}
			}
		}
		return types;
	}

	public static List<String> getAlreadyUsedAttrs(IDocument doc,
			int documentOffset) throws BadLocationException {
		List<String> attrList = new LinkedList<String>();
		StringBuilder bld = new StringBuilder();
		String returnVal = null;
		String[] attrs = null;
		int startPos = documentOffset - 2;
		char letter = '?';
		while (startPos > 0) {
			letter = doc.getChar(startPos);
			if (letter == '<') {
				returnVal = bld.toString();
				attrs = returnVal.split(" ");
				for (int i = 1; i < attrs.length; i++) {
					attrList.add(attrs[i].split("=")[0].trim());
				}
				return attrList;
			}
			bld.insert(0, letter);
			startPos--;
		}
		return null;
	}

	public static boolean checkDocToChars(IDocument doc, int offset,
			String letters) throws BadLocationException {
		return checkDocToChars(doc, offset, letters.toCharArray());
	}

	public static boolean checkDocToChars(IDocument doc, int offset,
			char... letters) throws BadLocationException {
		for (int i = 0; i < letters.length; i++) {
			if (doc.getChar(offset - (i + 1)) != letters[i]) {
				return false;
			}
		}
		return true;
	}

	public static String getLastAttribute(IDocument doc, int documentOffset)
			throws BadLocationException {
		StringBuilder bld = new StringBuilder();
		String returnVal = null;
		int startPos = documentOffset - 2;
		char letter = '?';
		while (startPos > 0) {
			letter = doc.getChar(startPos);
			if (letter == ' ') {
				returnVal = bld.toString();
				return returnVal.contains("=") ? returnVal.split("=")[0]
						: returnVal;
			}
			bld.insert(0, letter);
			startPos--;
		}
		return null;
	}

	public static IType getITypeFromTag(String tag) {
		List<IType> list = Activator.getDefault().getAvailableTypes();
		for (IType type : list) {
			if (type.getElementName().equals(tag)) {
				return type;
			}
		}
		return null;
	}

	public static String getLastTag(IDocument doc, int documentOffset)
			throws BadLocationException {
		StringBuilder bld = new StringBuilder();
		String returnVal = null;
		int startPos = documentOffset - 2;
		char letter = '?';
		while (startPos > 0) {
			letter = doc.getChar(startPos);
			if (letter == '<') {
				returnVal = bld.toString();
				return returnVal.contains(" ") ? returnVal.split(" ")[0]
						: returnVal;
			}
			bld.insert(0, letter);
			startPos--;
		}
		return null;
	}

	public static String getProposalPartBeforeCursor(String text) {
		return "<" + text + ">";
	}

	public static String getProposalPartAfterCursor(String text) {
		return "</" + text + ">";
	}

	public static String getQualifier(IDocument doc, int documentOffset) {
		// Use string buffer to collect characters
		StringBuffer buf = new StringBuffer();
		while (true) {
			try {
				// Read character backwards
				char c = doc.getChar(--documentOffset);
				// This was not the start of a tag
				if (c == '>' || Character.isWhitespace(c)) {
					return "";
				}
				// Collect character
				buf.append(c);
				// Start of tag. Return qualifier
				if (c == '<') {
					return buf.reverse().toString();
				}
			} catch (BadLocationException e) {
				// Document start reached, no tag found
				return "";
			}
		}
	}

	public static void loadAllTypes(final List<IType> list) {
		invokeLongTask(new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				monitor.setTaskName("JAML");
				monitor.beginTask("Collecting JAML classes...", 2);
				list.addAll(JDTUtils.iterateJavaProjects());
				monitor.internalWorked(1);
				JDTUtils.sortTypeList(list);
				monitor.internalWorked(2);
				monitor.done();
			}
		});
	}

	public static Exception invokeLongTask(IRunnableWithProgress runnable) {
		try {
			getProgressSvc().busyCursorWhile(runnable);
			return null;
		} catch (Exception e) {
			return e;
		}
	}

	public static IProgressService getProgressSvc() {
		return PlatformUI.getWorkbench().getProgressService();
	}

	public static String getIJavaElementPath(IJavaElement element)
			throws JavaModelException {
		switch (element.getElementType()) {
		case IJavaElement.JAVA_PROJECT:
			return element.getCorrespondingResource().getFullPath().toString();
		case IJavaElement.PACKAGE_FRAGMENT_ROOT:
			return element.getCorrespondingResource().getFullPath().toString();
		case IJavaElement.PACKAGE_FRAGMENT:
			return element.getCorrespondingResource().getFullPath().toString();
		case IJavaElement.COMPILATION_UNIT:
			return element.getCorrespondingResource().getFullPath().toString()
					.replace(element.getElementName(), "");
		case IJavaElement.TYPE:
			return getIJavaElementPath(element.getParent());
		}
		return JavaExt.STRING_EMPTY;
	}
}
