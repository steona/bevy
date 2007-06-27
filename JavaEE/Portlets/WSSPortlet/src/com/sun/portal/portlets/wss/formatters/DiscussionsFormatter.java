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
 * Created by IntelliJ IDEA. User: root Date: Dec 26, 2006 Time: 12:30:46 PM To
 * change this template use File | Settings | File Templates.
 */
public class DiscussionsFormatter implements InterfaceFormatter {
    public String getFormattedValue(String listSoapAddress, String listName, String RootFolder, String fieldValue, String id, HashMap formatterVariables) {
	String username = (String) formatterVariables.get(Constants.WSS_USERNAME);
	String password = (String) formatterVariables.get(Constants.WSS_PASSWORD);
	listSoapAddress = listSoapAddress.replaceAll("http://", "");
	String url = new StringBuilder().append("http://").append(username).append(":").append(password).append("@").append(listSoapAddress).append("/Lists/").append(listName)
		.append("/DispForm.aspx?ID=").append(id).toString();
	System.out.println("printing the url" + url);
	String returnValue = "<a href=\"#\" onclick=\"window.open(\'" + url + "\'" + ",\'Discussion\',\'width=400,height=350,resizable=1\');" + "\">" + fieldValue + "</a>";
	return returnValue;

    }
}
