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

import com.microsoft.schemas.sharepoint.soap.forms.FormsLocator;
import com.microsoft.schemas.sharepoint.soap.forms.FormsSoapStub;
import com.microsoft.schemas.sharepoint.soap.forms.GetFormCollectionResponseGetFormCollectionResult;
import com.microsoft.schemas.sharepoint.soap.forms.GetFormResponseGetFormResult;
import com.sun.portal.portlets.wss.constants.Constants;

/**
 * Created by IntelliJ IDEA. User: admin Date: Apr 24, 2007 Time: 9:32:38 PM To
 * change this template use File | Settings | File Templates.
 */
public class WSSFormsHelper extends WSSHelper {

    private static String FORMS_SOAP_PORT_NAME = "FormsSoap";

    private static String listSoapAddress = "http://129.158.227.228/_vti_bin/Forms.asmx";

    private static String username = "Administrator";

    private static String password = "abc123";

    private static String listname = "Announcements";

    public WSSFormsHelper() {

    }

    public static void main(String[] args) throws Exception {
	String result = getFormCollection(listSoapAddress, username, password, listname);
	String form = getForm(listSoapAddress, username, password, listname, "Lists/Announcements/NewForm.aspx");

	System.out.println("result----->" + result);
	System.out.println("form----->" + form);

    }

    public static String getForm(String listSoapAddress, String username, String password, String listname, String formurl) throws Exception, ServiceException {
	FormsSoapStub stub = returnStub(listSoapAddress, username, password);
	GetFormResponseGetFormResult result = stub.getForm(listname, formurl);
	return getXmlFragment(result.get_any());
    }

    public static String getFormCollection(String listSoapAddress, String username, String password, String listname) throws Exception, RemoteException {
	FormsSoapStub stub = returnStub(listSoapAddress, username, password);
	GetFormCollectionResponseGetFormCollectionResult result = stub.getFormCollection(listname);
	String xml = getXmlFragment(result.get_any());
	return xml;
    }

    private static FormsSoapStub returnStub(String listSoapAddress, String userName, String password) throws ServiceException {

	FormsLocator service = new FormsLocator();
	service.setEndpointAddress(FORMS_SOAP_PORT_NAME, listSoapAddress);
	FormsSoapStub stub = (FormsSoapStub) service.getFormsSoap();
	stub.setUsername(userName);
	stub.setPassword(password);
	return stub;

    }

    private static FormsSoapStub returnStub(String listSoapAddress, String userName, String password, Properties props) throws ServiceException {

	FormsLocator service = new FormsLocator();
	service.setEndpointAddress(FORMS_SOAP_PORT_NAME, listSoapAddress);
	FormsSoapStub stub = (FormsSoapStub) service.getFormsSoap();
	stub.setUsername(userName);
	stub.setPassword(password);
	stub._setProperty("http.proxyHost", props.getProperty(Constants.PROXY_HOST));
	stub._setProperty("http.proxyPort", props.getProperty(Constants.PROXY_PORT));
	stub._setProperty("http.proxyUser", props.getProperty(Constants.PROXY_USER));
	stub._setProperty("http.proxyPassword", props.getProperty(Constants.PROXY_PASSWORD));

	return stub;

    }

}
