/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*
*
* Author Of This Original Software : Sandeep Soni [ Sandeep.Soni at gmail dot com ]
* With Active Contributions from Murali Krishna Reddy

* Do Let Me Know by Email if you used the software and found it useful.
*
*/
package com.sun.portal.portlets.wss.formatters;

import java.util.HashMap;

import com.sun.portal.portlets.wss.constants.Constants;

/**
 * Created by IntelliJ IDEA. User: root Date: Aug 31, 2006 Time: 12:53:36 PM To
 * change this template use File | Settings | File Templates.
 */
public class FilenameFormatter implements InterfaceFormatter {

    public String getFormattedValue(String listSoapAddress, String listName, String RootFolder, String fieldValue, String id) {

	// /*String modstr = "<a href=\"" + listSoapAddress + "/" + RootFolder +
        // "/" + fieldValue + "\">" + fieldValue + "</a>";*/
	// PortletPreferences prefs = preq.getPreferences();
	// String username = prefs.getValue(Constants.PREF_WSS_USERNAME,
        // Constants.NULL_STRING);
	// String password = prefs.getValue(Constants.PREF_WSS_PASSWORD,
        // Constants.NULL_STRING);
	// listSoapAddress = listSoapAddress.replaceAll("http://", "");
	// String url = new
        // StringBuilder().append("http://").append(username).append(":").append(password).append("@").append(listSoapAddress).append("/Lists/").append(listName).append("/DispForm.aspx?ID=").append(id).toString();
	// String url = listSoapAddress + "/" + RootFolder + "/" + fieldValue;
	// String modstr = "<a href=\"#\" onclick=\"window.open(\'" + url + "\'"
        // + ",\'Files\',\'width=400,height=350,resizable=1\');" + "\">" +
        // fieldValue + "</a>";
	// System.out.println(modstr);
	// return modstr;
	return fieldValue;
    }

    public String getFormattedValue(String listSoapAddress, String listName, String RootFolder, String fieldValue, String id, HashMap formatterVariables) {

	listSoapAddress = listSoapAddress.replaceAll("http://", "");
	String username = (String) formatterVariables.get(Constants.WSS_USERNAME);
	String password = (String) formatterVariables.get(Constants.WSS_PASSWORD);
	String url = new StringBuilder().append("http://").append(username).append(":").append(password).append("@").append(listSoapAddress).append("/").append(RootFolder).append(
		"/").append(fieldValue).toString();
	// String url = listSoapAddress + "/" + RootFolder + "/" + fieldValue;
	String modstr = "<a href=\"#\" onclick=\"window.open(\'" + url + "\'" + ",\'Files\',\'width=400,height=350,resizable=1\');" + "\">" + fieldValue + "</a>";
	System.out.println(modstr);
	return modstr;
    }

    // public String getFormattedValue(String listSoapAddress, String
        // listName, String RootFolder, String fieldValue, String id,
        // RenderRequest preq, RenderResponse pres, String bodyView) {
    // return getFormattedValue(listSoapAddress, listName, RootFolder,
        // fieldValue, id, preq, pres);
    // }
}
