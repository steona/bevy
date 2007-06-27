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

import com.microsoft.schemas.sharepoint.soap.meetings.GetMeetingWorkspacesResponseGetMeetingWorkspacesResult;
import com.microsoft.schemas.sharepoint.soap.meetings.MeetingsLocator;
import com.microsoft.schemas.sharepoint.soap.meetings.MeetingsSoapStub;
import com.sun.portal.sharepoint.schemas.meeting.collections.WSSMeetingsCollection;
import com.sun.portal.xml.utils.XMLUtils;

/**
 * Created by IntelliJ IDEA. User: root Date: Sep 20, 2006 Time: 10:42:02 AM To
 * change this template use File | Settings | File Templates.
 */
public class WSSMeetingsHelper extends WSSHelper {

    private static final String password = "";

    private static final String sitename = "http://www.wssdemo.com:8080/_vti_bin/meetings.asmx";

    private static final String xslFile = "/space/ws/share1.5.1/web-src/xsl/meetings/wss-meeting-collections.xsl";

    private static final String username = "";

    private static String MEETINGS_SOAP_PORT_NAME = "MeetingsSoap";

    public WSSMeetingsHelper() {

    }

    public static void main(String[] args) {
	try {
	    System.setProperty("http.proxyPort", "8080");
	    System.setProperty("http.proxyHost", "webcache.central.sun.com");
	    List l = getMeetingWorkspaces(sitename, username, password, xslFile);

	    // getMeetingsInformation(sitename,username,password);

	} catch (Exception e) {
	    e.printStackTrace(); // To change body of catch statement use
                                        // File | Settings | File Templates.
	}
    }

    public static List getMeetingWorkspaces(String sitename, String username, String password, String xslFile) throws Exception, RemoteException, JAXBException, ServiceException {

	MeetingsSoapStub stub = returnStub(sitename, username, password);
	GetMeetingWorkspacesResponseGetMeetingWorkspacesResult res = stub.getMeetingWorkspaces(false);
	String result = getXmlFragment(res.get_any());
	// System.out.println(result);
	// System.out.println("xmlString " + result);
	String trXmlString = XMLUtils.transformViaXslt(result, xslFile);
	// System.out.println("trXMLString " + trXmlString);
	WSSMeetingsCollection collection = (WSSMeetingsCollection) unmarshallFromXml("com.sun.portal.sharepoint.schemas.meeting.collections", trXmlString);
	return collection.getWSSMeetings();

    }

    private static void debug(String methodName, String msg) {
	debug("WSSMeetingsHelper", methodName, msg);
    }

    private static MeetingsSoapStub returnStub(String sitename, String username, String password) throws ServiceException {

	MeetingsLocator service = new MeetingsLocator();
	service.setEndpointAddress(MEETINGS_SOAP_PORT_NAME, sitename);
	MeetingsSoapStub stub = (MeetingsSoapStub) service.getMeetingsSoap();
	stub.setUsername(username);
	stub.setPassword(password);
	return stub;

    }

}
