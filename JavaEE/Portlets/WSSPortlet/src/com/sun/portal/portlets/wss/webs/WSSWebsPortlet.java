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
package com.sun.portal.portlets.wss.webs;

import com.sun.portal.portlets.wss.GenericWssPortlet;
import com.sun.portal.portlets.wss.helpers.WSSWebsHelper;
import com.sun.portal.portlets.wss.constants.Constants;

import javax.portlet.*;
import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Apr 23, 2007
 * Time: 12:05:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class WSSWebsPortlet extends GenericWssPortlet {

    public void init(PortletConfig pConfig) throws PortletException {
        super.init(pConfig);

    }

    public void doView(RenderRequest portletRequest, RenderResponse portletResponse) throws IOException, PortletException {
        portletResponse.setContentType("text/html");
        PortletContext context = getPortletContext();
        PortletPreferences prefs = portletRequest.getPreferences();
        String xslUrl = portletResponse.encodeURL(Constants.WSS_WEB_TO_PORTAL_LIST);
        String xslFile = getPortletContext().getRealPath(xslUrl);
        try {
            if (isNullStringValue(getWssSiteUrl(prefs))) {
                writeSiteNotRespondingError(portletResponse);
            } else {
                String sitename = getWssSiteUrl(prefs) + Constants.WEBS_ASMX;
                // Template templates[] = WSSSitesHelper.getSiteTemplates(sitename, getWssSiteUsername(prefs),getWssSitePassword(prefs));
                //  List WSSSites = WSSWebsHelper.getAllWebSites(sitename,getWssSiteUsername(prefs),getWssSitePassword(prefs),xslFile) ;
                // portletRequest.setAttribute(Constants.WSS_WEB_SITES_LIST,WSSSites);
                //PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.WSS_WEB_SITES_JSP);
                // String tree = "{root : {title : 'Default Tree Root Node',children : [{title : '<a href=\"www.google.com\">Node 1.1</a>'},{title : 'Node 1.2',children : [{title : 'Node 3.1'}]}]}}";
                String tree = WSSWebsHelper.getWebSitesStringForTree(sitename, getWssSiteUsername(prefs), getWssSitePassword(prefs), xslFile);
                portletRequest.setAttribute("nodes", tree);
                PortletRequestDispatcher rd = context.getRequestDispatcher("/web/websitestree.jsp");
                rd.include(portletRequest, portletResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void doEdit(RenderRequest portletRequest, RenderResponse portletResponse) throws IOException, PortletException {
        portletResponse.setContentType("text/html");
        PortletContext context = getPortletContext();
        PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.WSS_EDIT_SITE_JSP);
        rd.include(portletRequest, portletResponse);
    }

    public void doHelp(RenderRequest portletRequest, RenderResponse portletResponse) throws IOException, PortletException {
        portletResponse.setContentType("text/html");

        PortletContext context = getPortletContext();
        PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.LIST_INDEX_JSP);
        rd.include(portletRequest, portletResponse);
    }

    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
        saveSiteInfo(request, response);
        response.setPortletMode(PortletMode.VIEW);
    }


}
