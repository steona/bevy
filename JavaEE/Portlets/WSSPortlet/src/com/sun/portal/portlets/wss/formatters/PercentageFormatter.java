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

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Created by IntelliJ IDEA. User: root Date: Aug 31, 2006 Time: 3:52:39 PM To
 * change this template use File | Settings | File Templates.
 */
public class PercentageFormatter implements InterfaceFormatter {
    public String getFormattedValue(String listSoapAddress, String listName, String fieldValue, String id) {
	float i = Float.valueOf(fieldValue).floatValue();
	float j = Math.round(i * 100);
	// System.out.println("printing the value" + i*100);
	String modstr = Float.toString(j);
	String modstr1 = modstr + "%";
	return modstr1;
    }

    public String getFormattedValue(String listSoapAddress, String listName, String RootFolder, String fieldValue, String id, HashMap formatterVariables) {
	return getFormattedValue(listSoapAddress, listName, fieldValue, id);
    }

    public String getFormattedValue(String listSoapAddress, String listName, String fieldValue, String id, RenderRequest preq, RenderResponse pres, String bodyView) {
	return getFormattedValue(listSoapAddress, listName, fieldValue, id);
    }
}
