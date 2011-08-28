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
package org.jaml.patches;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.jaml.api.ICallbackEventHandler;
import org.jaml.api.IWebClient;

/**
 * The class WebClient is the standard implementation of IWebClient and
 * simplifies down- and uploads from/to web sources.
 */
public class WebClient implements IWebClient {
	protected boolean allowReadStreamBuffering;
	protected boolean allowWriteStreamBuffering;
	protected String baseAddress;
	protected Object credentials;
	protected Object encoding;
	protected Map<String, String> requestHeaders;
	protected Map<String, String> responseHeaders;
	protected boolean defaultCredentialsEnabled;
	protected boolean busy;

	protected ICallbackEventHandler eventDownloadProgressChanged;
	protected ICallbackEventHandler eventDownloadStringCompleted;
	protected ICallbackEventHandler eventOpenReadCompleted;
	protected ICallbackEventHandler eventOpenWriteCompleted;
	protected ICallbackEventHandler eventUploadProgressChanged;
	protected ICallbackEventHandler eventUploadStringCompleted;
	protected ICallbackEventHandler eventWriteStreamClosed;

	@Override
	public void dispose() {
		allowReadStreamBuffering = false;
		allowWriteStreamBuffering = false;
		baseAddress = null;
		credentials = null;
		encoding = null;
		requestHeaders = null;
		responseHeaders = null;
		defaultCredentialsEnabled = false;
		busy = false;
		eventDownloadProgressChanged = null;
		eventDownloadStringCompleted = null;
		eventOpenReadCompleted = null;
		eventOpenWriteCompleted = null;
		eventUploadProgressChanged = null;
		eventUploadStringCompleted = null;
		eventWriteStreamClosed = null;
	}

	@Override
	public void cancelAsync() {
		// TODO Auto-generated method stub
	}

	@Override
	public void downloadStringAsync(URI address) {
		downloadStringAsync(address, null);
	}

	@Override
	public void downloadStringAsync(URI address, Object userToken) {
		try {
			URL url = address.toURL();
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			StringBuilder strBuilder = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				strBuilder.append(inputLine);
			}
			in.close();
			eventDownloadStringCompleted.callback(this, strBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void openReadAsync(URI address) {
		openReadAsync(address, null);
	}

	@Override
	public void openReadAsync(URI address, Object userToken) {
		// TODO Auto-generated method stub
	}

	@Override
	public void openWriteAsync(URI address) {
		openWriteAsync(address, HTTPMethod.POST);
	}

	@Override
	public void openWriteAsync(URI address, HTTPMethod method) {
		openWriteAsync(address, method, null);
	}

	@Override
	public void openWriteAsync(URI address, HTTPMethod method, Object userToken) {
		// TODO Auto-generated method stub
	}

	@Override
	public void uploadStringAsync(URI address, String data) {
		uploadStringAsync(address, HTTPMethod.POST, data);
	}

	@Override
	public void uploadStringAsync(URI address, HTTPMethod method, String data) {
		uploadStringAsync(address, method, data, null);
	}

	@Override
	public void uploadStringAsync(URI address, HTTPMethod method, String data,
			Object userToken) {
		// TODO Auto-generated method stub
	}

	@Override
	public void downloadProgressChanged(ICallbackEventHandler eventHandler) {
		eventDownloadProgressChanged = eventHandler;
	}

	@Override
	public void downloadStringCompleted(ICallbackEventHandler eventHandler) {
		eventDownloadStringCompleted = eventHandler;
	}

	@Override
	public void openReadCompleted(ICallbackEventHandler eventHandler) {
		eventOpenReadCompleted = eventHandler;
	}

	@Override
	public void openWriteCompleted(ICallbackEventHandler eventHandler) {
		eventOpenWriteCompleted = eventHandler;
	}

	@Override
	public void uploadProgressChanged(ICallbackEventHandler eventHandler) {
		eventUploadProgressChanged = eventHandler;
	}

	@Override
	public void uploadStringCompleted(ICallbackEventHandler eventHandler) {
		eventUploadStringCompleted = eventHandler;
	}

	@Override
	public void writeStreamClosed(ICallbackEventHandler eventHandler) {
		eventWriteStreamClosed = eventHandler;
	}
}
