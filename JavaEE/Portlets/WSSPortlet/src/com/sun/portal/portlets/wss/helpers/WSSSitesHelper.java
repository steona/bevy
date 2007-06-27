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

import org.apache.axis.holders.UnsignedIntHolder;
import org.apache.axis.types.UnsignedInt;

import com.microsoft.schemas.sharepoint.soap.sites.SitesLocator;
import com.microsoft.schemas.sharepoint.soap.sites.SitesSoapStub;
import com.microsoft.schemas.sharepoint.soap.sites.Template;
import com.microsoft.schemas.sharepoint.soap.sites.holders.ArrayOfTemplateHolder;
import com.sun.portal.portlets.wss.constants.Constants;

/**
 * Created by IntelliJ IDEA. User: admin Date: Apr 20, 2007 Time: 10:19:34 PM To
 * change this template use File | Settings | File Templates.
 */
public class WSSSitesHelper extends WSSHelper {

    private static String SITES_SOAP_PORT_NAME = "SitesSoap";

    private static String listSoapAddress = "http://129.158.227.228/_vti_bin/Sites.asmx";

    private static String username = "Administrator";

    private static String password = "abc123";

    public WSSSitesHelper() {

    }

    public static void main(String args[]) throws RemoteException, ServiceException {

	Template templates[] = getSiteTemplates(listSoapAddress, username, password);
	for (int i = 0; i < templates.length; i++) {
	    System.out.println("Title--------->" + templates[i].getTitle());
	    System.out.println("Name--------->" + templates[i].getName());
	    System.out.println("Description-->" + templates[i].getDescription());
	    System.out.println("ID--------->" + templates[i].getID());
	    System.out.println("ImageUrl--->" + templates[i].getImageUrl());
	    System.out.println("===============================================");

	}

    }

    public static Template[] getSiteTemplates(String listSoapAddress, String userName, String password) throws ServiceException, RemoteException {
	SitesSoapStub stub = returnStub(listSoapAddress, userName, password);
	Template templates[] = new Template[0];
	ArrayOfTemplateHolder templateList = new ArrayOfTemplateHolder(templates);
	UnsignedIntHolder returnvalue = new UnsignedIntHolder();
	UnsignedInt lcid = new UnsignedInt("1033");
	stub.getSiteTemplates(lcid, returnvalue, templateList);
	return templateList.value;
    }

    private static SitesSoapStub returnStub(String listSoapAddress, String userName, String password) throws ServiceException {

	SitesLocator service = new SitesLocator();
	service.setEndpointAddress(SITES_SOAP_PORT_NAME, listSoapAddress);
	SitesSoapStub stub = (SitesSoapStub) service.getSitesSoap();
	stub.setUsername(userName);
	stub.setPassword(password);

	return stub;

    }

    private static SitesSoapStub returnStub(String listSoapAddress, String userName, String password, Properties props) throws ServiceException {

	SitesLocator service = new SitesLocator();
	service.setEndpointAddress(SITES_SOAP_PORT_NAME, listSoapAddress);
	SitesSoapStub stub = (SitesSoapStub) service.getSitesSoap();
	stub.setUsername(userName);
	stub.setPassword(password);
	stub._setProperty("http.proxyHost", props.getProperty(Constants.PROXY_HOST));
	stub._setProperty("http.proxyPort", props.getProperty(Constants.PROXY_PORT));
	stub._setProperty("http.proxyUser", props.getProperty(Constants.PROXY_USER));
	stub._setProperty("http.proxyPassword", props.getProperty(Constants.PROXY_PASSWORD));

	return stub;

    }

}
