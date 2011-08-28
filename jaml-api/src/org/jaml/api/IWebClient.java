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
package org.jaml.api;

import java.net.URI;

public interface IWebClient {

	public enum HTTPMethod {
		POST, GET
	}

	// Operations
	void downloadStringAsync(URI address);

	void downloadStringAsync(URI address, Object userToken);

	void openReadAsync(URI address);

	void openReadAsync(URI address, Object userToken);

	void openWriteAsync(URI address);

	void openWriteAsync(URI address, HTTPMethod method);

	void openWriteAsync(URI address, HTTPMethod method, Object userToken);

	void uploadStringAsync(URI address, String data);

	void uploadStringAsync(URI address, HTTPMethod method, String data);

	void uploadStringAsync(URI address, HTTPMethod method, String data,
			Object userToken);

	// Events
	void downloadProgressChanged(ICallbackEventHandler eventHandler);

	void downloadStringCompleted(ICallbackEventHandler eventHandler);

	void openReadCompleted(ICallbackEventHandler eventHandler);

	void openWriteCompleted(ICallbackEventHandler eventHandler);

	void uploadProgressChanged(ICallbackEventHandler eventHandler);

	void uploadStringCompleted(ICallbackEventHandler eventHandler);

	void writeStreamClosed(ICallbackEventHandler eventHandler);

	// Cancel and dispose
	void cancelAsync();

	void dispose();
}
