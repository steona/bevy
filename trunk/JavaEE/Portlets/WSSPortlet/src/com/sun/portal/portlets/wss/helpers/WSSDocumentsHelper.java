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
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;

import com.microsoft.schemas.sharepoint.soap.list.GetListCollectionResponseGetListCollectionResult;
import com.microsoft.schemas.sharepoint.soap.list.ListsLocator;
import com.microsoft.schemas.sharepoint.soap.list.ListsSoapStub;
import com.sun.portal.sharepoint.schemas.list.collections.WssListCollection;
import com.sun.portal.xml.utils.XMLUtils;


public class WSSDocumentsHelper extends WSSHelper {

    private static final String password = "test";

    private static final String sitename = "http://129.158.227.81/_vti_bin/Lists.asmx";

    private static final String username = "test";

    private static final String XslLocation = "/WSSPortlets/muralisharepoint/web-src/xsl/list/newsingleWssList.xsl";

    private static String LISTS_SOAP_PORT_NAME = "ListsSoap";

    public WSSDocumentsHelper() {

    }

    public static void main(String[] args) throws Exception {

	try {

	    List list = getAllDocuments(sitename, username, password, XslLocation);
	    System.out.println("Lists" + list);

	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println("Message" + e.getMessage());

	}

    }

    public static List getAllDocuments(String listSoapAddress, String username, String password, String xslFile) throws Exception, JAXBException, RemoteException, ServiceException {

	String xmlString = getDocumentsCollectionFromWss(listSoapAddress, username, password);
	// System.out.println("xmlString " + xmlString);
	String trXmlString = XMLUtils.transformViaXslt(xmlString, xslFile);
	// System.out.println("trXMLString " +trXmlString);
	WssListCollection collection = (WssListCollection) unmarshallFromXml("com.sun.portal.sharepoint.schemas.list.collections", trXmlString);
	return collection.getWssList();

    }

    private static String getDocumentsCollectionFromWss(String listSoapAddress, String username, String password) throws Exception, RemoteException, ServiceException {

	ListsSoapStub stub = returnStub(listSoapAddress, username, password);
	GetListCollectionResponseGetListCollectionResult res = stub.getListCollection();
	return getXmlFragment(res.get_any());

    }

    private static ListsSoapStub returnStub(String listSoapAddress, String userName, String password) throws ServiceException {
	ListsLocator service = new ListsLocator();
	service.setEndpointAddress(LISTS_SOAP_PORT_NAME, listSoapAddress);
	ListsSoapStub stub = (ListsSoapStub) service.getListsSoap();
	stub.setUsername(userName);
	stub.setPassword(password);

	return stub;
    }

    private static void debug(String methodName, String msg) {
	debug("WSSDocumentsHelper", methodName, msg);
    }
}
