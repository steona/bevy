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

import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.microsoft.schemas.sharepoint.soap.views.GetViewCollectionResponseGetViewCollectionResult;
import com.microsoft.schemas.sharepoint.soap.views.GetViewResponseGetViewResult;
import com.microsoft.schemas.sharepoint.soap.views.ViewsLocator;
import com.microsoft.schemas.sharepoint.soap.views.ViewsSoapStub;
import com.sun.portal.portlets.wss.formatters.Formatter;
import com.sun.portal.xml.utils.XMLUtils;

/**
 * Created by IntelliJ IDEA. User: root Date: Sep 4, 2006 Time: 8:43:14 PM To
 * change this template use File | Settings | File Templates.
 */
public class WSSViewsHelper extends WSSHelper {

    protected static final String password = "abc123";

    protected static final String sitename = "http://129.158.227.228/_vti_bin/Views.asmx";

    protected static final String username = "Administrator";

    private static String VIEWS_SOAP_PORT_NAME = "ViewsSoap";

    public static void main(String[] args) throws Exception {
	try {

	    // String viewCollection = getViewsCollection(sitename,
                // username, password, "Announcements");
	    String view = getView(sitename, username, password, "Announcements", "{015905B5-59DD-4ECE-AFB4-8C0625C0BF83}");
	    System.out.println(view);

	    // List list = getViewsAsList(viewCollection);

	    // Formatter.getFormattedViewNames(list);
	    // System.out.println(list.toString());
	} catch (Exception e) {
	    System.out.println("Message" + e.toString());
	}

    }

    public static List getAllViewsLinks(String sitename, String username, String password, String listName, HashMap requiredVariables) throws Exception {

	String viewsCollection = getViewsCollection(sitename, username, password, listName);

	List viewsList = getViewsAsList(viewsCollection);

	List formattedViewsList = Formatter.getFormattedViewNames(requiredVariables, viewsList);

	return formattedViewsList;

    }

    public static String getBodyViewOfList(String sitename, String username, String password, String listName) throws Exception {
	int index = sitename.lastIndexOf("/");
	sitename = sitename.substring(0, index);
	sitename = sitename + "/Views.asmx";
	String viewsCollection = getViewsCollection(sitename, username, password, listName);
	List viewsList = getViewsAsList(viewsCollection);
	Iterator it = viewsList.iterator();
	while (it.hasNext()) {
	    List row = (List) it.next();
	    String name = (String) row.get(0);
	    String displayName = (String) row.get(1);
	    if (displayName.equals("")) {
		return name;
	    }
	}
	return "";
    }

    private static String getViewsCollection(String sitename, String username, String password, String listName) throws Exception {

	ViewsSoapStub stub = returnStub(sitename, username, password);
	GetViewCollectionResponseGetViewCollectionResult res = stub.getViewCollection(listName);
	String xmlString = getXmlFragment(res.get_any());
	// System.out.println(xmlString);
	return xmlString;

    }

    protected static ViewsSoapStub returnStub(String viewsSoapAddress, String username, String password) throws ServiceException {

	ViewsLocator service = new ViewsLocator();
	service.setEndpointAddress(VIEWS_SOAP_PORT_NAME, viewsSoapAddress);
	ViewsSoapStub stub = (ViewsSoapStub) service.getViewsSoap();
	stub.setUsername(username);
	stub.setPassword(password);
	return stub;
    }

    public static List getViewsAsList(String xml) throws IOException, ParserConfigurationException, SAXException {
	Document doc = XMLUtils.parseAndReturnRootNode(new InputSource(new StringReader(xml)));
	NodeList nodeList = doc.getElementsByTagNameNS("*", "View"); // List
                                                                        // of
                                                                        // all
                                                                        // rows
                                                                        // returned
	List views = new ArrayList();
	for (int i = 0; i < nodeList.getLength(); i++) {
	    List row = new ArrayList();
	    org.w3c.dom.Node n = nodeList.item(i); // i-th Row that was
                                                        // returned.
	    NamedNodeMap nodemap = n.getAttributes(); // Get all attributes
                                                        // from this node
	    String name = nodemap.getNamedItem("Name").getNodeValue();
	    String displayName = nodemap.getNamedItem("DisplayName").getNodeValue();
	    org.w3c.dom.Node defaultView = nodemap.getNamedItem("DefaultView");
	    row.add(name);
	    row.add(displayName);
	    if (defaultView != null && defaultView.getNodeValue().equals("TRUE")) {
		row.add("DefaultView");
	    }
	    views.add(row);

	}
	return views;

    }

    private static void debug(String methodName, String msg) {
	debug("WSSViewsHelper", methodName, msg);
    }

    private static String getView(String sitename, String username, String password, String listname, String viewname) throws Exception, RemoteException {
	ViewsSoapStub stub = returnStub(sitename, username, password);
	GetViewResponseGetViewResult result = stub.getView(listname, viewname);
	return getXmlFragment(result.get_any());
	// return result.toString();
    }

}
