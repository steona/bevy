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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;

import com.microsoft.schemas.sharepoint.soap.webs.GetAllSubWebCollectionResponseGetAllSubWebCollectionResult;
import com.microsoft.schemas.sharepoint.soap.webs.GetWebCollectionResponseGetWebCollectionResult;
import com.microsoft.schemas.sharepoint.soap.webs.WebsLocator;
import com.microsoft.schemas.sharepoint.soap.webs.WebsSoapStub;
import com.sun.portal.portlets.wss.constants.Constants;
import com.sun.portal.portlets.wss.formatters.Formatter;
import com.sun.portal.sharepoint.schemas.sites.WSSSiteList;
import com.sun.portal.sharepoint.schemas.sites.Web;
import com.sun.portal.xml.utils.XMLUtils;

/**
 * Created by IntelliJ IDEA. User: admin Date: Apr 23, 2007 Time: 12:05:22 PM To
 * change this template use File | Settings | File Templates.
 */
public class WSSWebsHelper extends WSSHelper {
    private static String WEBS_SOAP_PORT_NAME = "WebsSoap";

    private static final String sitename = "http://129.158.227.228/_vti_bin/Webs.asmx";

    private static final String password = "abc123";

    private static final String username = "Administrator";

    private static final String XslLocation = "E:\\Sharepoint\\share1.5.1\\web-src\\xsl\\webs\\websitesList.xsl";

    public WSSWebsHelper() {

    }

    public static void main(String args[]) throws Exception {
	// List result =
        // getAllSubWebCollection(sitename,username,password,XslLocation);
	String tree = getWebSitesStringForTree(sitename, username, password, XslLocation);
	System.out.println("tree--->" + tree);
    }

    public static List getAllSubWebCollection(String listSoapAddress, String username, String password, String XslLocation) throws Exception, RemoteException {
	WebsSoapStub stub = returnStub(listSoapAddress, username, password);
	GetAllSubWebCollectionResponseGetAllSubWebCollectionResult returnvalue = stub.getAllSubWebCollection();
	String returnXml = getXmlFragment(returnvalue.get_any());
	String trXml = XMLUtils.transformViaXslt(returnXml, XslLocation);
	WSSSiteList wssSitesList = (WSSSiteList) unmarshallFromXml("com.sun.portal.sharepoint.schemas.sites", trXml);
	return wssSitesList.getWeb();

    }

    public static List getAllWebSites(String listSoapAddress, String username, String password, String XslLocation) throws Exception {
	List formattedWebSites = new ArrayList();
	List allWebSites = getAllSubWebCollection(listSoapAddress, username, password, XslLocation);
	for (int i = 0; i < allWebSites.size(); i++) {
	    Web website = (Web) allWebSites.get(i);
	    // formattedWebSites.add(Formatter.getFormattedWebSiteNames(username,password,website));
	}
	return formattedWebSites;
    }

    public static String getWebSitesStringForTree(String listSoapAddress, String username, String password, String XslLocation) throws Exception {
	StringBuffer stringForTree = new StringBuffer();
	stringForTree.append("{root:");
	stringForTree.append("{title:'");
	stringForTree.append(Formatter.getFormattedWebSiteNames(username, password, listSoapAddress));
	stringForTree.append("',");
	String resulttree = buildTheTree(listSoapAddress, username, password, XslLocation, stringForTree);
	resulttree = resulttree + "}}";
	return resulttree;
    }

    private static String buildTheTree(String listSoapAddress, String username, String password, String XslLocation, StringBuffer stringForTree) throws Exception {
	// stringForTree.append("{title:'");
	//
	// stringForTree.append(Formatter.getFormattedWebSiteNames(username,password,listSoapAddress));

	stringForTree.append("children:[");
	List webSites = getAllSubWebsites(listSoapAddress, username, password, XslLocation);
	for (int i = 0; i < webSites.size(); i++) {
	    Web website = (Web) webSites.get(i);

	    String url = website.getUrl();
	    if (url.indexOf("_vti_bin") == -1) {
		url = url + "/_vti_bin/Webs.asmx";
	    }
	    stringForTree.append("{title:'");
	    stringForTree.append(Formatter.getFormattedWebSiteNames(username, password, website));
	    stringForTree.append("'");

	    List childrenSites = getAllSubWebsites(url, username, password, XslLocation);
	    if (childrenSites != null) {
		for (int childVar = 0; childVar < childrenSites.size(); childVar++) {
		    stringForTree.append(",");
		    buildTheTree(url, username, password, XslLocation, stringForTree);
		}

	    }

	    stringForTree.append("}");
	    if (i + 1 != webSites.size()) {
		stringForTree.append(",");
	    }

	}

	stringForTree.append("]");
	// String resultString = modifyForSemicolons(stringForTree.toString());
	return stringForTree.toString();
    }

    public static List getAllSubWebsites(String listSoapAddress, String username, String password, String XslLocation) throws Exception {
	try {
	    WebsSoapStub stub = returnStub(listSoapAddress, username, password);
	    GetWebCollectionResponseGetWebCollectionResult returnvalue = stub.getWebCollection();
	    String returnXml = getXmlFragment(returnvalue.get_any());
	    String trXml = XMLUtils.transformViaXslt(returnXml, XslLocation);
	    WSSSiteList wssSitesList = (WSSSiteList) unmarshallFromXml("com.sun.portal.sharepoint.schemas.sites", trXml);
	    return wssSitesList.getWeb();
	} catch (JAXBException je) {
	    return null;
	}
    }

    private static WebsSoapStub returnStub(String listSoapAddress, String userName, String password) throws ServiceException {

	WebsLocator service = new WebsLocator();
	service.setEndpointAddress(WEBS_SOAP_PORT_NAME, listSoapAddress);
	WebsSoapStub stub = (WebsSoapStub) service.getWebsSoap();
	stub.setUsername(userName);
	stub.setPassword(password);

	return stub;

    }

    private static WebsSoapStub returnStub(String listSoapAddress, String userName, String password, Properties props) throws ServiceException {

	WebsLocator service = new WebsLocator();
	service.setEndpointAddress(WEBS_SOAP_PORT_NAME, listSoapAddress);
	WebsSoapStub stub = (WebsSoapStub) service.getWebsSoap();
	stub.setUsername(userName);
	stub.setPassword(password);
	stub._setProperty("http.proxyHost", props.getProperty(Constants.PROXY_HOST));
	stub._setProperty("http.proxyPort", props.getProperty(Constants.PROXY_PORT));
	stub._setProperty("http.proxyUser", props.getProperty(Constants.PROXY_USER));
	stub._setProperty("http.proxyPassword", props.getProperty(Constants.PROXY_PASSWORD));

	return stub;

    }

}
