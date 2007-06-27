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
package com.sun.portal.portlets.wss.documents;

import com.sun.portal.portlets.wss.GenericWssPortlet;
import com.sun.portal.portlets.wss.utils.UrlWrapper;
import com.sun.portal.portlets.wss.constants.Constants;
import com.sun.portal.portlets.wss.helpers.WSSDocumentsHelper;
import com.sun.portal.portlets.wss.helpers.WSSListsHelper;
import org.xml.sax.SAXException;

import javax.portlet.*;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.*;

public class WSSDocumentsPortlet extends GenericWssPortlet {
    private HashMap requiredDocumentAttributes = new HashMap();

    public void init(PortletConfig pConfig) throws PortletException {
	super.init(pConfig);

    }

    public void doView(RenderRequest portletRequest, RenderResponse portletResponse) throws IOException {
	try {
	    portletResponse.setContentType("text/html");

	    viewDocumentsDetail(portletRequest, portletResponse);
	} catch (Exception e) {
	    e.printStackTrace();
	    portletResponse.setContentType("text/html");
	    writeSiteNotRespondingError(portletResponse);

	}

    }

    private void viewDocumentsDetail(RenderRequest portletRequest, RenderResponse portletResponse) throws Exception, PortletException, JAXBException, TransformerException,
	    ParserConfigurationException, SAXException, SOAPException {
	PortletPreferences prefs = portletRequest.getPreferences();

	if (isNullStringValue(getWssSiteUrl(prefs))) {
	    writeNoSitedefinedError(portletResponse);
	} else {

	    String LISTS_SOAP_ADDRESS = getWssSiteUrl(prefs) + Constants.LISTS_ASMX;
	    String xslUrl = portletResponse.encodeURL(Constants.WSS_LIST_TO_PORTAL_LIST);
	    String xslFile = getPortletContext().getRealPath(xslUrl);
	    PortletContext context = getPortletContext();
	    String listToDisplay = prefs.getValue(Constants.PREF_WSS_LIST_NAME, Constants.NULL_STRING);
	    String displayName = prefs.getValue(Constants.PREF_WSS_DISPLAY_NAME, "WSSListPortlet");
	    String viewName = "";

	    List NamedListData = WSSListsHelper
		    .getNamedListData(getDocumentVariablesHashMap(portletRequest, portletResponse, LISTS_SOAP_ADDRESS, xslFile, viewName, listToDisplay));
	    portletResponse.setTitle(displayName);
	    portletRequest.setAttribute(Constants.WSS_SINGLE_LIST, NamedListData);
	    portletRequest.setAttribute(Constants.XSLT_URL, "http://" + portletRequest.getServerName() + ":" + portletRequest.getServerPort() + xslUrl);
	    portletRequest.setAttribute("LIST_NAME", portletRequest.getParameter("name"));
	    PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.LIST_INDEX_JSP);
	    rd.include(portletRequest, portletResponse);

	}

    }

    private HashMap getDocumentVariablesHashMap(RenderRequest portletRequest, RenderResponse portletResponse, String listSoapAddress, String xslfile, String viewname,
	    String listtodisplay) throws IOException {

	PortletPreferences prefs = portletRequest.getPreferences();
	if (requiredDocumentAttributes.size() == 0) {
	    Properties props = new Properties();
	    props.load(new FileInputStream(getPortletContext().getRealPath("/WEB-INF/icons.properties")));
	    Enumeration en = props.propertyNames();
	    while (en.hasMoreElements()) {
		String key = (String) en.nextElement();
		String value = (String) props.get(key);
		requiredDocumentAttributes.put(key, portletResponse.encodeURL(portletRequest.getContextPath() + value));
	    }
	    requiredDocumentAttributes.put(Constants.WSS_URL, listSoapAddress);
	    requiredDocumentAttributes.put(Constants.WSS_USERNAME, getWssSiteUsername(prefs));
	    requiredDocumentAttributes.put(Constants.WSS_PASSWORD, getWssSitePassword(prefs));
	    requiredDocumentAttributes.put(Constants.WSS_XSLT_FILE, xslfile);
	    requiredDocumentAttributes.put(Constants.WSS_VIEW_NAME, viewname);
	    requiredDocumentAttributes.put(Constants.WSS_LIST_NAME, listtodisplay);
	    requiredDocumentAttributes.put(Constants.WSS_PORTLET_URL, new UrlWrapper(portletResponse.createActionURL()));

	}
	return requiredDocumentAttributes;

    }

    private void showAllAvailableWssDocuments(RenderRequest portletRequest, RenderResponse portletResponse) throws Exception, IOException, TransformerException, ServiceException {
	PortletPreferences prefs = portletRequest.getPreferences();

	if (isNullStringValue(getWssSiteUrl(prefs))) {
	    writeSiteNotRespondingError(portletResponse);
	} else {

	    String LISTS_SOAP_ADDRESS = getWssSiteUrl(prefs) + Constants.LISTS_ASMX;

	    String xslUrl = portletResponse.encodeURL(Constants.WSS_DOCUMENTS_COLLECTION_TO_PORTAL_DOCUMENTS_COLLECTION);
	    String xslFile = getPortletContext().getRealPath(xslUrl);
	    debug("showAllAvailableWssLists", "xsl File" + xslFile);
	    // System.out.println(xslFile);
	    List listCollection = WSSDocumentsHelper.getAllDocuments(LISTS_SOAP_ADDRESS, getWssSiteUsername(prefs), getWssSitePassword(prefs), xslFile);
	    // System.out.println(" list collection " +
                // listCollection.toString());

	    debug("showAllAvailablelists", "about to display");
	    portletRequest.setAttribute(Constants.WSS_LIST_COLLECTION, listCollection);

	}
    }

    public void doEdit(RenderRequest portletRequest, RenderResponse portletResponse) throws IOException, PortletException {

	portletResponse.setContentType("text/html");
	PortletContext context = getPortletContext();
	try {
	    if (portletRequest.getParameter("edit") != null) {
		displayEditPages(portletRequest, portletResponse, context);
	    } else {
		PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.LIST_EDIT_JSP);
		rd.include(portletRequest, portletResponse);
	    }
	} catch (Exception e) {
	    portletRequest.setAttribute(Constants.SITE, "invalid");
	    PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.LIST_EDIT_JSP);
	    rd.include(portletRequest, portletResponse);
	    e.printStackTrace();
	}

    }

    private void displayEditPages(RenderRequest portletRequest, RenderResponse portletResponse, PortletContext context) throws Exception, PortletException, JAXBException,
	    TransformerException, ServiceException {

	String editMode = portletRequest.getParameter("edit");

	if (editMode.equalsIgnoreCase("site")) {
	    PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.WSS_EDIT_SITE_JSP);
	    rd.include(portletRequest, portletResponse);
	} else if (editMode.equalsIgnoreCase("list")) {
	    // Show list of all available lists on the selected WSS site
	    showAllAvailableWssDocuments(portletRequest, portletResponse);

	    // System.out.println("No list selected");
	    debug("displayEditpages", "about to display page");
	    PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.LIST_EDIT_LIST_JSP);
	    rd.include(portletRequest, portletResponse);

	}

    }

    public void doHelp(RenderRequest portletRequest, RenderResponse portletResponse) throws PortletException, IOException {

	portletResponse.setContentType("text/html");

	PortletContext context = getPortletContext();
	PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.LIST_INDEX_JSP);
	rd.include(portletRequest, portletResponse);
    }

    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {

	saveSiteInfo(request, response);

	if (request.getParameter("edit") != null)
	    response.setRenderParameter("edit", request.getParameter("edit"));

	if (request.getParameter("saveListSetting") != null) {
	    response.setRenderParameter("saveListSetting", request.getParameter("saveListSetting"));
	    saveListInfo(request, response);

	}

    }

    private void saveListInfo(ActionRequest request, ActionResponse response) throws ReadOnlyException, PortletModeException, IOException, ValidatorException {

	PortletPreferences prefs = request.getPreferences();
	String numberOfRows = request.getParameter("numberOfRows");
	prefs.setValue("numberOfRows", numberOfRows);
	String fullList = request.getParameter(Constants.PREF_WSS_LIST_NAME);
	if (fullList != null) {
	    StringTokenizer str = new StringTokenizer(fullList, ",");
	    String listName = str.nextToken();
	    String displayName = str.nextToken();

	    prefs.setValue(Constants.PREF_WSS_DISPLAY_NAME, displayName);
	    prefs.setValue(Constants.PREF_WSS_LIST_NAME, listName);

	    response.setPortletMode(PortletMode.VIEW);
	} else {
	    response.setRenderParameter("edit", "list");
	}
	prefs.store();

    }

    private void debug(String method, String message) {
	debug(this.getClass().toString(), method, message);
    }

}
