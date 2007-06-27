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
/*
 * WSSAlertsHelper.java
 *
 * Created on July 3, 2006, 7:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.portal.portlets.wss.helpers;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.microsoft.schemas.sharepoint.soap.alerts.Alert;
import com.microsoft.schemas.sharepoint.soap.alerts.AlertInfo;
import com.microsoft.schemas.sharepoint.soap.alerts.AlertsLocator;
import com.microsoft.schemas.sharepoint.soap.alerts.AlertsSoapStub;


public class WSSAlertsHelper extends WSSHelper {

    private static final String password = "abc123";

    private static final String sitename = "http://localhost:4040/_vti_bin/Alerts.asmx";

    private static final String username = "murali";

    private static String ALERTS_SOAP_PORT_NAME = "AlertsSoap";

    /**
         * Creates a new instance of WSSAlertsHelper
         */
    public WSSAlertsHelper() {

    }

    public static void main(String[] args) {

	try {
	    // System.getProperties().put("proxySet","true");
	    // System.setProperty("http.proxyPort","8080");
	    // System.setProperty("http.proxyHost","webcache.central.sun.com");
	    AlertsSoapStub stub = returnStub(sitename, username, password);

	    AlertInfo alertinfo = stub.getAlerts();

	    Alert[] alerts = alertinfo.getAlerts();

	    if (alerts.length != 0) {
		for (int i = 0; i < alerts.length; i++) {
		    Alert alert = alerts[i];
		    printAlertInfo(alert);

		}
	    } else
		System.out.println("No alerts found");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private static AlertsSoapStub returnStub(String listSoapAddress, String userName, String password) throws ServiceException {

	AlertsLocator service = new AlertsLocator();
	service.setEndpointAddress(ALERTS_SOAP_PORT_NAME, listSoapAddress);
	AlertsSoapStub stub = (AlertsSoapStub) service.getAlertsSoap();
	stub.setUsername(userName);
	stub.setPassword(password);
	return stub;
    }

    private static void printAlertInfo(Alert alert) {
	System.out.println("ID:" + alert.getId());
	System.out.println("Title:" + alert.getTitle());
	System.out.println(alert.getEventType());
	System.out.println("getAlertForTitle:" + alert.getAlertForTitle());
	System.out.println(alert.isActive());

    }

    public static AlertInfo getAllAlerts(String listSoapAddress, String userName, String password) throws RemoteException, ServiceException {
	debug("getAllAlerts", "listSoapAddress" + listSoapAddress);
	debug("getAllAlerts", "listSoapAddress" + userName);
	debug("getAllAlerts", "listSoapAddress" + password);

	AlertsSoapStub stub = returnStub(listSoapAddress, userName, password);
	return stub.getAlerts();
    }

    private static void debug(String methodName, String msg) {
	debug("WSSAlertsHelper", methodName, msg);
    }

}
