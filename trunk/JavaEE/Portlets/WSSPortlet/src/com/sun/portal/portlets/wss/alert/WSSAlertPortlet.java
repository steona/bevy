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
package com.sun.portal.portlets.wss.alert;

import com.microsoft.schemas.sharepoint.soap.alerts.Alert;
import com.sun.portal.portlets.wss.GenericWssPortlet;
import com.sun.portal.portlets.wss.constants.Constants;
import com.sun.portal.portlets.wss.helpers.WSSAlertsHelper;

import javax.portlet.*;
import java.io.IOException;

public class WSSAlertPortlet extends GenericWssPortlet {

    /**
         * A life cycle method. This method gets inovked by the container.
         * 
         * @param pConfig
         *                a object that holds the portlet configuration
         *                information
         * @throws PortletException
         *                 an exception that you throw if things go wrong while
         *                 starting the portlet
         */
    public void init(PortletConfig pConfig) throws PortletException {
	super.init(pConfig);
    }

    /**
         * The GenericPortlet calls this method if the portlet mode is view
         * 
         * @param portletRequest
         *                the request
         * @param portletResponse
         *                the response
         * @throws PortletException
         * @throws IOException
         */
    public void doView(RenderRequest portletRequest, RenderResponse portletResponse) throws PortletException, IOException {

	portletResponse.setContentType("text/html");

	viewAlertsDetail(portletRequest, portletResponse);

    }

    private void viewAlertsDetail(RenderRequest portletRequest, RenderResponse portletResponse) throws PortletException, IOException {

	PortletPreferences prefs = portletRequest.getPreferences();

	if (isNullStringValue(getWssSiteUrl(prefs))) {
	    writeNoSitedefinedError(portletResponse);
	} else {
	    String ALERTS_SOAP_ADDRESS = getWssSiteUrl(prefs) + Constants.ALERTS_ASMX;
	    PortletContext context = getPortletContext();

	    try {
		Alert[] alerts = WSSAlertsHelper.getAllAlerts(ALERTS_SOAP_ADDRESS, getWssSiteUsername(prefs), getWssSitePassword(prefs)).getAlerts();
		portletRequest.setAttribute("WSS_ALERTS_ARRAY", alerts);

		PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.ALERT_INDEX_JSP);
		rd.include(portletRequest, portletResponse);
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	}

    }

    public void doEdit(RenderRequest portletRequest, RenderResponse portletResponse) throws PortletException, IOException {
	WindowState state = portletRequest.getWindowState();
	portletResponse.setContentType("text/html");

	PortletContext context = getPortletContext();
	PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.WSS_EDIT_SITE_JSP);
	rd.include(portletRequest, portletResponse);
    }

    public void doHelp(RenderRequest portletRequest, RenderResponse portletResponse) throws PortletException, IOException {
	WindowState state = portletRequest.getWindowState();
	portletResponse.setContentType("text/html");

	PortletContext context = getPortletContext();
	PortletRequestDispatcher rd = context.getRequestDispatcher("/index.jsp");
	rd.include(portletRequest, portletResponse);
    }

    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
	saveSiteInfo(request, response);
	response.setPortletMode(PortletMode.VIEW);
    }
}
