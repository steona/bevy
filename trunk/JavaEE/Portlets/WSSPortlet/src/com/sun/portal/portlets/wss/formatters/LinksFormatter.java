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
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Created by IntelliJ IDEA. User: root Date: Aug 30, 2006 Time: 8:25:50 PM To
 * change this template use File | Settings | File Templates.
 */
public class LinksFormatter implements InterfaceFormatter {

    public String getFormattedValue(String fieldValue) {
	try {
	    StringTokenizer st = new StringTokenizer(fieldValue, ",");
	    int num = st.countTokens();
	    String link = st.nextToken();
	    String site = st.nextToken();
	    /* String modstr = "<a href=\"" + link + "\">" + site + "</a>"; */
	    String modstr = "<a href=\"#\" onclick=\"window.open(\'" + link + "\'" + ",\'Links\',\'width=400,height=350,resizable=1\');" + "\">" + site + "</a>";

	    return modstr;
	} catch (NoSuchElementException e) {
	    return fieldValue;
	}

    }

    public String getFormattedValue(String listSoapAddress, String listName, String fieldValue, String id) {
	return getFormattedValue(fieldValue);
    }

    public String getFormattedValue(String listSoapAddress, String listName, String RootFolder, String fieldValue, String id, HashMap formatterVariables) {
	return getFormattedValue(fieldValue);
    }

    public String getFormattedValue(String listSoapAddress, String listName, String fieldValue, String id, RenderRequest preq, RenderResponse pres, String bodyView) {
	return getFormattedValue(fieldValue);
    }
}
