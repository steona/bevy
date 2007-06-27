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
package com.sun.portal.portlets.wss.helpers;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.xml.rpc.ServiceException;

import com.microsoft.schemas.sharepoint.soap.webpart.WebPartPagesWebServiceLocator;
import com.microsoft.schemas.sharepoint.soap.webpart.WebPartPagesWebServiceSoapStub;
import com.sun.portal.portlets.wss.constants.Constants;

/**
 * Created by IntelliJ IDEA. User: admin Date: Apr 25, 2007 Time: 2:44:49 PM To
 * change this template use File | Settings | File Templates.
 */
public class WSSWebPartsHelper extends WSSHelper {
    private static String WEBPART_SOAP_PORT_NAME = "WebPartPagesWebServiceSoap";

    private static final String sitename = "http://localhost:9999/_vti_bin/WebPartPages.asmx";

    private static final String password = "abc123";

    private static final String username = "Administrator";

    public WSSWebPartsHelper() {

    }

    public static void main(String[] args) throws RemoteException, ServiceException {
	getWebPartPage(sitename, username, password, "Lists/Announcements/NewForm.aspx");
    }

    public static void getWebPartPage(String listSoapAddress, String username, String password, String title) throws ServiceException, RemoteException {
	WebPartPagesWebServiceSoapStub stub = returnStub(listSoapAddress, username, password);
	String result = stub.getWebPartPageDocument(title);

    }

    public static void getWebPart(String listSoapAddress, String username, String password, String pageurl, String storageKey, String storage) throws ServiceException,
	    RemoteException {
	WebPartPagesWebServiceSoapStub stub = returnStub(listSoapAddress, username, password);
	// stub.getWebPart(pageurl,storageKey,new Storage("Shared"));
    }

    private static WebPartPagesWebServiceSoapStub returnStub(String listSoapAddress, String userName, String password) throws ServiceException {

	WebPartPagesWebServiceLocator service = new WebPartPagesWebServiceLocator();
	service.setEndpointAddress(WEBPART_SOAP_PORT_NAME, listSoapAddress);
	WebPartPagesWebServiceSoapStub stub = (WebPartPagesWebServiceSoapStub) service.getWebPartPagesWebServiceSoap();
	stub.setUsername(userName);
	stub.setPassword(password);

	return stub;

    }

    private static WebPartPagesWebServiceSoapStub returnStub(String listSoapAddress, String userName, String password, Properties props) throws ServiceException {

	WebPartPagesWebServiceLocator service = new WebPartPagesWebServiceLocator();
	service.setEndpointAddress(WEBPART_SOAP_PORT_NAME, listSoapAddress);
	WebPartPagesWebServiceSoapStub stub = (WebPartPagesWebServiceSoapStub) service.getWebPartPagesWebServiceSoap();
	stub.setUsername(userName);
	stub.setPassword(password);
	stub._setProperty("http.proxyHost", props.getProperty(Constants.PROXY_HOST));
	stub._setProperty("http.proxyPort", props.getProperty(Constants.PROXY_PORT));
	stub._setProperty("http.proxyUser", props.getProperty(Constants.PROXY_USER));
	stub._setProperty("http.proxyPassword", props.getProperty(Constants.PROXY_PASSWORD));

	return stub;

    }

}
