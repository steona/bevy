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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Created by IntelliJ IDEA. User: root Date: Aug 30, 2006 Time: 3:05:40 PM To
 * change this template use File | Settings | File Templates.
 */
public class DateFormatter implements InterfaceFormatter {

    public String getFormattedValue(String listSoapAddress, String listName, String RootFolder, String fieldValue, String id) {

	return getFormattedValue(fieldValue);
    }

    public String getFormattedValue(String fieldValue) {
	Date mydate = null;
	SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdfOutput = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	try {
	    mydate = sdfInput.parse(fieldValue);
	} catch (ParseException e) {
	    e.printStackTrace(); // To change body of catch statement use
                                        // File | Settings | File Templates.
	}
	// System.out.println("Date" + date + "Time" + time);
	if (mydate != null)
	    // System.out.println("DateFormat result +++++++>" +
                // sdfOutput.format(mydate) );

	    return sdfOutput.format(mydate);

	return fieldValue;
    }

    public String getFormattedValue(String listSoapAddress, String listName, String RootFolder, String fieldValue, String id, HashMap formatterVariables) {
	return getFormattedValue(fieldValue);
    }

    public String getFormattedValue(String listSoapAddress, String listName, String fieldValue, String id, RenderRequest preq, RenderResponse pres, String bodyView) {
	return getFormattedValue(fieldValue);
    }
}