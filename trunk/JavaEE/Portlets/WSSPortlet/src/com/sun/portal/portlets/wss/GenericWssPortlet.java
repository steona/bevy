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
package com.sun.portal.portlets.wss;

import com.sun.portal.portlets.wss.constants.Constants;
import com.sun.portal.portlets.wss.helpers.SharePointLogger;

import javax.portlet.*;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericWssPortlet extends GenericPortlet {

    Logger logger = null;

    public void init() throws PortletException {
	super.init();
	setProxyIfNeeded();
	logger = SharePointLogger.getLogger();

    }

    public void init(PortletConfig arg0) throws PortletException {
	super.init(arg0);
	setProxyIfNeeded();
	logger = SharePointLogger.getLogger();
    }

    private void setProxyIfNeeded() {
	if (Constants.INTRANET) {
	    System.setProperty("http.proxyHost", "webcache.sfbay.sun.com");
	    System.setProperty("http.proxyPort", "8080");
	}
    }

    protected void writeNoSitedefinedError(RenderResponse portletResponse) throws IOException {
	String out = "NO WSS SITE DEFINED, Please do that in edit mode";
	portletResponse.getPortletOutputStream().write(out.getBytes());
    }

    protected void writeSiteNotRespondingError(RenderResponse portletResponse) throws IOException {

	String sout = "SITE COULD NOT PROCESS GIVEN REQUEST,Please change it in edit mode";
	portletResponse.getPortletOutputStream().write(sout.getBytes());

    }

    protected String getWssSiteUrl(PortletPreferences prefs) {
	return prefs.getValue(Constants.PREF_WSS_URL, Constants.NULL_STRING);
    }

    protected String getWssSiteUsername(PortletPreferences prefs) {
	return prefs.getValue(Constants.PREF_WSS_USERNAME, Constants.NULL_STRING);
    }

    protected String getWssSitePassword(PortletPreferences prefs) {
	return prefs.getValue(Constants.PREF_WSS_PASSWORD, Constants.NULL_STRING);
    }

    protected boolean isNullStringValue(String s) {
	return s.equalsIgnoreCase(Constants.NULL_STRING) ? true : false;
    }

    protected boolean isProxySet(PortletPreferences prefs) {
	String proxyset = prefs.getValue(Constants.PROXY_SET, "");
	if (!proxyset.equalsIgnoreCase("") && proxyset.equalsIgnoreCase("TRUE"))
	    return true;
	else
	    return false;
    }

    protected Properties getProxyProperties(PortletPreferences prefs) {
	Properties props = new Properties();
	props.setProperty(Constants.PROXY_HOST, prefs.getValue(Constants.PROXY_HOST, ""));
	props.setProperty(Constants.PROXY_PORT, prefs.getValue(Constants.PROXY_PORT, ""));
	props.setProperty(Constants.PROXY_USER, prefs.getValue(Constants.PROXY_USER, ""));
	props.setProperty(Constants.PROXY_PASSWORD, prefs.getValue(Constants.PROXY_PASSWORD, ""));
	return props;
    }

    protected void saveSiteInfo(ActionRequest request, ActionResponse response) throws PortletModeException {

	if (request.getParameter("typeOfSubmit") != null) {

	    if (request.getParameter("typeOfSubmit").trim().equalsIgnoreCase("saveSite")) {
		response.setRenderParameter("saveSite", request.getParameter("saveSite"));
		PortletPreferences prefs = request.getPreferences();
		String proxyset = request.getParameter("proxysetting");

		try {

		    if (proxyset != null) {
			prefs.setValue(Constants.PROXY_SET, "TRUE");
			prefs.setValue(Constants.PROXY_HOST, request.getParameter(Constants.PROXY_HOST).trim());
			prefs.setValue(Constants.PROXY_PORT, request.getParameter(Constants.PROXY_PORT).trim());
			prefs.setValue(Constants.PROXY_USER, request.getParameter(Constants.PROXY_USER).trim());
			prefs.setValue(Constants.PROXY_PASSWORD, request.getParameter(Constants.PROXY_PASSWORD).trim());
		    } else {
			prefs.setValue(Constants.PROXY_SET, "FALSE");

		    }
		    prefs.setValue(Constants.PREF_WSS_URL, request.getParameter(Constants.PREF_WSS_URL).trim());
		    prefs.setValue(Constants.PREF_WSS_USERNAME, request.getParameter(Constants.PREF_WSS_USERNAME).trim());
		    prefs.setValue(Constants.PREF_WSS_PASSWORD, request.getParameter(Constants.PREF_WSS_PASSWORD).trim());
		    prefs.store();

		} catch (Exception e) {

		    e.printStackTrace();
		}
	    } else if (request.getParameter("typeOfSubmit").trim().equalsIgnoreCase("Cancel")) {
		response.setPortletMode(PortletMode.VIEW);
	    }
	}

    }

    protected void debug(String className, String methodName, String msg) {
	logger.log(Level.INFO, className + ":" + methodName + ":" + msg);
    }
}
