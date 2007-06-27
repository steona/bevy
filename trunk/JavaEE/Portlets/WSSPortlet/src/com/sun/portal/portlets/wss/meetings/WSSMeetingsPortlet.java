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
package com.sun.portal.portlets.wss.meetings;

import com.sun.portal.portlets.wss.GenericWssPortlet;
import com.sun.portal.portlets.wss.utils.UrlWrapper;
import com.sun.portal.portlets.wss.constants.Constants;
import com.sun.portal.portlets.wss.helpers.WSSListsHelper;
import com.sun.portal.portlets.wss.helpers.WSSMeetingsHelper;
import com.sun.portal.portlets.wss.helpers.WSSViewsHelper;
import org.xml.sax.SAXException;

import javax.portlet.*;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: root
 * Date: Sep 27, 2006
 * Time: 10:12:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class WSSMeetingsPortlet extends GenericWssPortlet {


    public void init(PortletConfig pConfig) throws PortletException {
        super.init(pConfig);

    }

    public void doView(RenderRequest portletRequest, RenderResponse portletResponse) throws IOException {
        try {
            portletResponse.setContentType("text/html");
            PortletPreferences prefs = portletRequest.getPreferences();
            String viewbody = prefs.getValue("viewbody", "");
            if (!viewbody.equals("")) {


                viewListItemBody(portletRequest, portletResponse);


            } else {

                viewListDetail(portletRequest, portletResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
            portletResponse.setContentType("text/html");
            writeSiteNotRespondingError(portletResponse);
        }

    }


    private void viewListItemBody(RenderRequest portletRequest, RenderResponse portletResponse) throws Exception, IOException, JAXBException, TransformerException, ParserConfigurationException, SAXException, SOAPException {
        PortletPreferences prefs = portletRequest.getPreferences();
        PortletContext context = getPortletContext();
        String viewbody = prefs.getValue("viewbody", "");
        String list = prefs.getValue("list", "");
        String id = prefs.getValue("id", "");
        String Meeting = prefs.getValue("Meeting", "");
        String LISTS_SOAP_ADDRESS = Meeting + Constants.LISTS_ASMX;
        String xslUrl = portletResponse.encodeURL(Constants.WSS_LIST_TO_PORTAL_LIST);
        String xslFile = getPortletContext().getRealPath(xslUrl);
        String body = WSSListsHelper.getListBody(LISTS_SOAP_ADDRESS, getWssSiteUsername(prefs), getWssSitePassword(prefs), list, id, xslFile);
        portletRequest.setAttribute(Constants.LIST_BODY, body);

        PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.LIST_DETAIL_JSP);
        rd.include(portletRequest, portletResponse);

    }


    private void viewListDetail(RenderRequest portletRequest, RenderResponse portletResponse) throws Exception, TransformerException, ParserConfigurationException, SAXException, PortletException, JAXBException, SOAPException {
        PortletPreferences prefs = portletRequest.getPreferences();


        if (isNullStringValue(getWssSiteUrl(prefs))) {
            writeNoSitedefinedError(portletResponse);
        } else {
            List NamedListData = null;
            String Meeting = prefs.getValue("Meeting", "");
            String LISTS_SOAP_ADDRESS = Meeting + Constants.LISTS_ASMX;
            String VIEWS_SOAP_ADDRESS = Meeting + Constants.VIEWS_ASMX;
            String xslUrl = portletResponse.encodeURL(Constants.WSS_LIST_TO_PORTAL_LIST);
            String xslFile = getPortletContext().getRealPath(xslUrl);
            debug("xslFile", xslFile);
            PortletContext context = getPortletContext();
            String listToDisplay = prefs.getValue(Constants.PREF_WSS_LIST_NAME, Constants.NULL_STRING);
            String displayName = prefs.getValue(Constants.PREF_WSS_DISPLAY_NAME, "WSSListPortlet");
            String viewName = prefs.getValue("viewName", "");
            HashMap viewVariables = new HashMap();

            viewVariables.put(Constants.WSS_PRESENT_VIEW, prefs.getValue("viewName", ""));
            viewVariables.put(Constants.WSS_PORTLET_URL, new UrlWrapper(portletResponse.createActionURL()));

            List viewLinks = WSSViewsHelper.getAllViewsLinks(
                    VIEWS_SOAP_ADDRESS, getWssSiteUsername(prefs),
                    getWssSitePassword(prefs), listToDisplay, viewVariables);
            portletRequest.setAttribute(Constants.WSS_VIEWS_LIST, viewLinks);
//            System.out.println(viewLinks.toString());
            //debug("viewLinks",viewLinks.toString());
            HashMap requiredAttributes = new HashMap();
            requiredAttributes.put(Constants.WSS_URL, LISTS_SOAP_ADDRESS);
            requiredAttributes.put(Constants.WSS_USERNAME, getWssSiteUsername(prefs));
            requiredAttributes.put(Constants.WSS_PASSWORD, getWssSitePassword(prefs));
            requiredAttributes.put(Constants.WSS_XSLT_FILE, xslFile);
            requiredAttributes.put(Constants.WSS_VIEW_NAME, viewName);
            requiredAttributes.put(Constants.WSS_LIST_NAME, listToDisplay);
            requiredAttributes.put(Constants.WSS_PORTLET_URL, new UrlWrapper(portletResponse.createActionURL()));
            NamedListData = WSSListsHelper.getNamedListData(requiredAttributes);
            debug("NamedListData", NamedListData.toString());
            portletResponse.setTitle(displayName);
            portletRequest.setAttribute(Constants.WSS_SINGLE_LIST, NamedListData);
            portletRequest.setAttribute(Constants.XSLT_URL, "http://" + portletRequest.getServerName() + ":" + portletRequest.getServerPort()
                    + xslUrl);
            portletRequest.setAttribute("LIST_NAME", portletRequest.getParameter("name"));
            PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.LIST_INDEX_JSP);
            rd.include(portletRequest, portletResponse);


        }


    }


    public void doEdit(RenderRequest portletRequest, RenderResponse portletResponse) throws PortletException, IOException {

        portletResponse.setContentType("text/html");
        PortletContext context = getPortletContext();
        try {
            if (portletRequest.getParameter("edit") != null) {
                displayEditPages(portletRequest, portletResponse, context);


            }
            // This Meeting parameter is getting set in processAction method with the Selected Meeting Name
            else if (portletRequest.getParameter("Meeting") != null) {
                displaySelectedMeeting(portletRequest, portletResponse, context);
            } else {

                PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.MEETINGS_EDIT_JSP);
                rd.include(portletRequest, portletResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            portletRequest.setAttribute(Constants.SITE, "invalid");
            PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.MEETINGS_EDIT_JSP);
            rd.include(portletRequest, portletResponse);
        }

    }

    private void displaySelectedMeeting(RenderRequest portletRequest, RenderResponse portletResponse, PortletContext context) throws Exception, IOException, TransformerException, PortletException, ServiceException {

        PortletPreferences prefs = portletRequest.getPreferences();
        String Meeting = portletRequest.getParameter("Meeting");
        String LISTS_SOAP_ADDRESS = Meeting + Constants.LISTS_ASMX;
        String xslUrl = portletResponse.encodeURL(Constants.WSS_LIST_COLLECTION_TO_PORTAL_LIST_COLLECTION);
        String xslFile = getPortletContext().getRealPath(xslUrl);
        debug("showAllAvailableWssLists", "xsl File" + xslFile);
        //System.out.println(xslFile);
        List listCollection = WSSListsHelper.getAllLists(LISTS_SOAP_ADDRESS, getWssSiteUsername(prefs), getWssSitePassword(prefs), xslFile);
        //System.out.println(" list collection " + listCollection.toString());

        debug("showAllAvailablelists", "about to display");

        portletRequest.setAttribute(Constants.WSS_LIST_COLLECTION, listCollection);
        PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.LIST_EDIT_LIST_JSP);
        rd.include(portletRequest, portletResponse);


    }

    private void displayEditPages(RenderRequest portletRequest, RenderResponse portletResponse, PortletContext context) throws Exception, PortletException, JAXBException, TransformerException, ServiceException {

        String editMode = portletRequest.getParameter("edit");
        if (editMode.equalsIgnoreCase("site")) {
            PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.WSS_EDIT_SITE_JSP);
            rd.include(portletRequest, portletResponse);
        } else if (editMode.equalsIgnoreCase("meetings")) {

            showAllAvailableMeetingWorkspaces(portletRequest, portletResponse);

            debug("displayEditpages", "about to display page");
            PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.MEETINGS_EDIT_LIST_JSP);
            rd.include(portletRequest, portletResponse);

        }


    }

    private void showAllAvailableMeetingWorkspaces(RenderRequest portletRequest, RenderResponse portletResponse) throws Exception, JAXBException, RemoteException, ServiceException {
        PortletPreferences prefs = portletRequest.getPreferences();
        String LISTS_SOAP_ADDRESS = getWssSiteUrl(prefs) + Constants.MEETINGS_ASMX;
        if (isNullStringValue(getWssSiteUrl(prefs))) {
            //writeNoSitedefinedError(portletResponse);
            throw new IllegalArgumentException("SITE URL CANNOT BE NULL");
        } else {

            String xslUrl = portletResponse.encodeURL(Constants.WSS_MEETINGS_COLLECTION_TO_PORTAL_MEETINGS_COLLECTION);
            String xslFile = getPortletContext().getRealPath(xslUrl);
            debug("showAllAvailableWssMeetings", "xsl File" + xslFile);
            //System.out.println(xslFile);
            List MeetingsCollection = WSSMeetingsHelper.getMeetingWorkspaces(LISTS_SOAP_ADDRESS, getWssSiteUsername(prefs), getWssSitePassword(prefs), xslFile);
            //System.out.println(" list collection " + listCollection.toString());

            debug("showAllAvailableWssMeetingWorkspaces", "about to display");
            portletRequest.setAttribute(Constants.WSS_MEETINGS_COLLECTION, MeetingsCollection);


        }

    }


    public void doHelp(RenderRequest portletRequest, RenderResponse portletResponse) throws PortletException, IOException {

        portletResponse.setContentType("text/html");

        PortletContext context = getPortletContext();
        PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.LIST_INDEX_JSP);
        rd.include(portletRequest, portletResponse);
    }

    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
        clearPreferences(request);
        saveSiteInfo(request, response);
        if (request.getParameter("viewbody") != null) {
            PortletPreferences prefs = request.getPreferences();
            prefs.setValue("list", request.getParameter("list"));
            prefs.setValue("id", request.getParameter("id"));
            prefs.setValue("viewbody", request.getParameter("viewbody"));
            prefs.store();
            response.setPortletMode(PortletMode.VIEW);
        }
        if (request.getParameter("edit") != null) {
            response.setRenderParameter("edit", request.getParameter("edit"));
        }

        if (request.getParameter("saveMeetingsSetting") != null) {
            String meeting = request.getParameter(Constants.PREF_WSS_MEETING_NAME);
            if (meeting != null) {
                response.setRenderParameter("Meeting", meeting);
                PortletPreferences prefs = request.getPreferences();
                prefs.setValue("Meeting", request.getParameter(Constants.PREF_WSS_MEETING_NAME));
                prefs.store();
            } else {
                response.setRenderParameter("edit", "meetings");
            }

        }

        if (request.getParameter("saveListSetting") != null) {

            response.setRenderParameter("saveListSetting", request.getParameter("saveListSetting"));
            saveListInfo(request, response);

        }
        if (request.getParameter("view") != null) {

            PortletPreferences prefs = request.getPreferences();
            prefs.setValue("viewName", request.getParameter("view"));
            prefs.setValue("viewbody", "");
            prefs.store();
            response.setPortletMode(PortletMode.VIEW);
        }

    }

    private void clearPreferences(ActionRequest request) throws ReadOnlyException, IOException, ValidatorException {
        PortletPreferences prefs = request.getPreferences();
        prefs.setValue("viewName", "");
        prefs.setValue("viewbody", "");
        prefs.store();
    }

    private void saveListInfo(ActionRequest request, ActionResponse response) throws IOException, ValidatorException, ReadOnlyException, PortletModeException {

        //  String Meeting = request.getParameter(Constants.WSS_MEETINGS_LIST);
        PortletPreferences prefs = request.getPreferences();
        String fullList = request.getParameter(Constants.PREF_WSS_LIST_NAME);
        if (fullList != null) {
            StringTokenizer str = new StringTokenizer(fullList, ",");
            String listName = str.nextToken();
            String displayName = str.nextToken();

            prefs.setValue(Constants.PREF_WSS_DISPLAY_NAME, displayName);
            prefs.setValue(Constants.PREF_WSS_LIST_NAME, listName);
            // prefs.setValue(Constants.WSS_MEETINGS_LIST,Meeting);
            prefs.store();

            response.setPortletMode(PortletMode.VIEW);
        } else {
            response.setRenderParameter("Meeting", prefs.getValue("Meeting", ""));
        }


    }

    private void debug(String method, String message) {
        debug(this.getClass().toString(), method, message);
    }


}
