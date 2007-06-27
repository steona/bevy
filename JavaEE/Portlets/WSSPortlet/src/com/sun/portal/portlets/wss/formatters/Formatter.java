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

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.portal.portlets.wss.constants.Constants;
import com.sun.portal.portlets.wss.helpers.SharePointLogger;
import com.sun.portal.portlets.wss.utils.UrlWrapper;
import com.sun.portal.sharepoint.schemas.list.single.ViewField;
import com.sun.portal.sharepoint.schemas.list.single.WssSingleList;
import com.sun.portal.sharepoint.schemas.sites.Web;
import com.sun.portal.xml.utils.XMLUtils;

/**
 * Created by IntelliJ IDEA. User: root Date: Aug 30, 2006 Time: 2:32:57 PM To
 * change this template use File | Settings | File Templates.
 */
public class Formatter {

    private static Logger logger = SharePointLogger.getLogger();

    public static String reformateValue(String listSoapAddress, String fieldName, String listName, String RootFolder, String id, String fieldType, String fieldValue,
	    HashMap formatterVariables) {
	// String Address = listSoapAddress.replaceAll(Constants.LISTS_ASMX,
        // "");
	int lastindex = listSoapAddress.lastIndexOf("/");
	String Address = listSoapAddress.substring(0, lastindex);
	InterfaceFormatter inter = getFormatter(fieldName, fieldType);
	debug("fieldValue", fieldValue);
	if (inter != null) {

	    return inter.getFormattedValue(Address, listName, RootFolder, fieldValue, id, formatterVariables);

	}
	return fieldValue;
    }

    public static List getAllFormattedListValues(HashMap formatterVariables) throws IOException, ParserConfigurationException, SAXException {
	WssSingleList wssList = (WssSingleList) formatterVariables.get(Constants.WSS_LIST);
	String xml = (String) formatterVariables.get(Constants.WSS_XML);
	String listSoapAddress = (String) formatterVariables.get(Constants.WSS_URL);
	String username = (String) formatterVariables.get(Constants.WSS_USERNAME);
	String password = (String) formatterVariables.get(Constants.WSS_PASSWORD);
	String junkString = "ows_";
	List list = new ArrayList();
	List viewFields = wssList.getDefaultView().getViewField();
	String Title = wssList.getTitle();
	String RootFolder = wssList.getRootFolder();
	Document doc = XMLUtils.parseAndReturnRootNode(new InputSource(new StringReader(xml)));
	NodeList nodeList = doc.getElementsByTagNameNS("*", "row"); // List
                                                                        // of
                                                                        // all
                                                                        // rows
                                                                        // returned
	for (int i = 0; i < nodeList.getLength(); i++) {
	    List thisRow = new ArrayList();
	    Node n = nodeList.item(i); // i-th Row that was returned.
	    NamedNodeMap nodemap = n.getAttributes(); // Get all attributes
                                                        // from this node
	    for (Iterator it = viewFields.iterator(); it.hasNext();) {
		Object viewField = it.next();
		ViewField element = (ViewField) viewField;
		String fieldName = element.getName();
		String fieldType = element.getType();
		Node attributeNode = nodemap.getNamedItem(junkString + fieldName);
		String id = nodemap.getNamedItem(junkString + "ID").getNodeValue();
		if (attributeNode == null)
		    thisRow.add("");
		else {
		    // System.out.println(attributeNode.getNodeValue());
		    String value = reformateValue(listSoapAddress, fieldName, Title, RootFolder, id, fieldType, attributeNode.getNodeValue(), formatterVariables);
		    thisRow.add(value);
		}

	    }

	    list.add(thisRow);
	}
	return list;
    }

    public static List getFormattedViewNames(HashMap requiredVariables, List viewsList) {
	try {
	    boolean defaultView = false;
	    List links = new ArrayList();
	    // String context = preq.getContextPath();
	    Iterator it = viewsList.iterator();
	    // PortletPreferences prefs = preq.getPreferences();
	    String presentView = (String) requiredVariables.get(Constants.WSS_PRESENT_VIEW);

	    while (it.hasNext()) {
		List row = (List) it.next();

		String name = (String) row.get(0);
		String displayName = (String) row.get(1);

		if (row.size() == 3)
		    defaultView = true;
		else
		    defaultView = false;
		if (!presentView.equals(""))
		    defaultView = false;

		UrlWrapper url = (UrlWrapper) requiredVariables.get(Constants.WSS_PORTLET_URL);
		url.setParameter("view", name);
		if (name.equals(presentView) || defaultView) {
		    String link = "<a href=\"" + url.toString() + "\">" + "<font color=\"red\">" + displayName + "</font color>" + "</a>";
		    links.add(link);
		} else {

		    String link = "<a href=\"" + url.toString() + "\">" + displayName + "</a>";
		    links.add(link);
		}

	    }

	    return links;
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public static String getFormattedWebSiteNames(String username, String password, String website) {
	// return
        // WebSitesFormatter.getFormattedWebNames(username,password,website);
	return WebSitesFormatter.getFormattedWebNamesString(username, password, website);
    }

    public static String getFormattedWebSiteNames(String username, String password, Web website) {
	// return
        // WebSitesFormatter.getFormattedWebNames(username,password,website);
	return WebSitesFormatter.getFormattedWebNamesString(username, password, website);
    }

    /*
         * At present this method is not used There is a method in
         * WSSListsHelper (getListBody) Which is returning the Body.This may be
         * necessary latter when we intend to add additional details in Body.
         */
    public static List getBodyList(RenderRequest preq, RenderResponse pres, List list, String bodyView) {
	BodyFormatter bform = new BodyFormatter();
	return bform.getFormattedNames(preq, pres, list, bodyView);
    }

    private static InterfaceFormatter getFormatter(String fieldName, String fieldType) {
	debug("getFormatter-fieldType", fieldType);
	debug("getFormatter-fieldName", fieldName);

	if (fieldType.equals("User"))
	    return new UserFormatter();

	else if (fieldType.equals("Calculated"))
	    return new CalculatedFormatter();
	else if (fieldType.equals("DateTime"))
	    return new DateFormatter();
	else if (fieldType.equals("Attachments"))
	    return new AttachmentsFormatter();
	else if (fieldType.equals("Lookup"))
	    return new LookupFormatter();
	else if (fieldType.equals("URL"))
	    return new LinksFormatter();
	else if (fieldType.equals("Boolean")) {
	    if (fieldName.equals("HaveIT") || fieldName.equals("WantIt"))
		return new HWBooleanFormatter();
	} else if (fieldType.equals("Number")) {
	    if (fieldName.equals("Percentage"))
		return new PercentageFormatter();
	    else
		return new NumberFormatter();

	} else if (fieldType.equals("Computed")) {

	    if (fieldName.equals("LinkFilename")) {
		return new FilenameFormatter();
	    } else if (fieldName.equals("LinkCheckedOutTitle")) {
		return null;
	    } else if (fieldName.equals("URLwMenu")) {
		return new LinksFormatter();
	    } else if (fieldName.equals("DocIcon")) {
		return new DocIconFormatter();
	    } else if (fieldName.equals("Threading") || fieldName.equals("ThreadingNoIndent")) {
		return new DiscussionsFormatter();
	    } else {
		return new ComputedFormatter();
	    }

	}
	return null;
    }

    protected static void debug(String className, String methodName, String msg) {
	logger.log(Level.INFO, className + ":" + methodName + ":" + msg);
    }

    private static void debug(String methodName, String msg) {
	debug("Formatter", methodName, msg);
    }

}
