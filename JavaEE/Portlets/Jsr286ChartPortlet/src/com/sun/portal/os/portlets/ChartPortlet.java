/*
Copyright 2004 The Apache Software Foundation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Developed by : Sandeep Soni [ http://sonisandeep.blogspot.com, 
    			      Email : Sandeep.Soni at yahoo.com 
    			    ]
 */

package com.sun.portal.os.portlets;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

public class ChartPortlet extends GenericPortlet {
    
    private PortletConfig _config ;
    private String defaultImagePath;

    public void init(PortletConfig pConfig) throws PortletException {
	_config = pConfig ;
	super.init(pConfig);
	defaultImagePath = _config.getPortletContext().getRealPath("/no-chart.png");
    }

    public void doView(RenderRequest portletRequest, RenderResponse portletResponse) throws PortletException,
	    IOException {
	portletResponse.setContentType("text/html");

	PortletContext context = getPortletContext();
	PortletSession pSession = portletRequest.getPortletSession(true);
	PortletPreferences prefs = portletRequest.getPreferences();

	if (pSession != null) {
	    pSession.setAttribute("PORTLET_PREFERENCES", prefs, PortletSession.APPLICATION_SCOPE);

	    PortletRequestDispatcher rd = context.getRequestDispatcher("/index.jsp");
	    rd.include(portletRequest, portletResponse);
	}
    }

    public void doEdit(RenderRequest portletRequest, RenderResponse portletResponse) throws PortletException,
	    IOException {
	portletResponse.setContentType("text/html");

	PortletContext context = getPortletContext();
	PortletRequestDispatcher rd = context.getRequestDispatcher("/edit.jsp");
	rd.include(portletRequest, portletResponse);
    }

    public void doHelp(RenderRequest portletRequest, RenderResponse portletResponse) throws PortletException,
	    IOException {
	WindowState state = portletRequest.getWindowState();
	portletResponse.setContentType("text/html");

	PortletContext context = getPortletContext();
	PortletRequestDispatcher rd = context.getRequestDispatcher("/help.jsp");
	rd.include(portletRequest, portletResponse);
    }

    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException,
	    IOException {
	if (actionRequest.getParameter("saveChartConfig") != null) {
	    PortletPreferences prefs = actionRequest.getPreferences();

	    String chartType = actionRequest.getParameter(Constants.PREF_CHART_TYPE);
	    String barSql = actionRequest.getParameter(Constants.PREF_BAR_CHART_SQL);
	    String pieSql = actionRequest.getParameter(Constants.PREF_PIE_CHART_SQL);
	    String timeSeriesSql = actionRequest.getParameter(Constants.PREF_TIME_SERIES_SQL);

	    System.out.println("Selected Chart Type : " + chartType);
	    prefs.setValue(Constants.PREF_CHART_TYPE, chartType);
	    prefs.setValue(Constants.PREF_PIE_CHART_SQL, pieSql);
	    prefs.setValue(Constants.PREF_BAR_CHART_SQL, barSql);
	    prefs.setValue(Constants.PREF_TIME_SERIES_SQL, timeSeriesSql);

	    prefs.store();

	    actionResponse.setPortletMode(PortletMode.VIEW);
	}
    }

    public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
	String resourceID = request.getResourceID();

	if (resourceID.equals("chart-image")) {
	    PortletChartUtilities.writeChartImageData(request, response , defaultImagePath);
	}
    }
}
