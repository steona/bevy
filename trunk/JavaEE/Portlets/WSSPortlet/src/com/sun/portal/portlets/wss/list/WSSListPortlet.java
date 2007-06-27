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
package com.sun.portal.portlets.wss.list;

import com.sun.portal.portlets.wss.GenericWssPortlet;
import com.sun.portal.portlets.wss.constants.Constants;
import com.sun.portal.portlets.wss.helpers.WSSListsHelper;
import com.sun.portal.portlets.wss.helpers.WSSViewsHelper;
import com.sun.portal.portlets.wss.utils.UrlWrapper;
import org.xml.sax.SAXException;

import javax.portlet.*;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class WSSListPortlet extends GenericWssPortlet {

    private HashMap requiredListAttributes = new HashMap();

    public void init(PortletConfig pConfig) throws PortletException {
        super.init(pConfig);

    }

    public void doView(RenderRequest portletRequest, RenderResponse portletResponse) throws IOException {
        try {
            portletResponse.setContentType("text/html");
            PortletPreferences prefs = portletRequest.getPreferences();
            String viewbody = prefs.getValue("viewbody", "");
            if (portletRequest.getParameter("additem") != null) {
                addListItem(portletRequest, portletResponse);
                return;
            }
            if (portletRequest.getParameter("itemadded") != null) {
                boolean sucessfullyadded = addItemToWssSite(portletRequest, portletResponse);
                if (!sucessfullyadded) {
                    PortletContext context = getPortletContext();
                    portletRequest.setAttribute(Constants.MESSAGE, "Failed to Add The Item");
                    PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.ADD_ITEM_TO_LIST);
                    rd.include(portletRequest, portletResponse);
                    return;
                }
            }
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

    private boolean addItemToWssSite(RenderRequest portletRequest, RenderResponse portletResponse) {
        try {
            PortletPreferences prefs = portletRequest.getPreferences();
            String LISTS_SOAP_ADDRESS = getWssSiteUrl(prefs) + Constants.LISTS_ASMX;
            String listToDisplay = prefs.getValue(Constants.PREF_WSS_LIST_NAME, Constants.NULL_STRING);
            WSSListsHelper.updateListItems(LISTS_SOAP_ADDRESS, getWssSiteUsername(prefs), getWssSitePassword(prefs), listToDisplay, "new", portletRequest.getParameterMap());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void addListItem(RenderRequest portletRequest, RenderResponse portletResponse) throws Exception, PortletException {
        PortletPreferences prefs = portletRequest.getPreferences();
        PortletContext context = getPortletContext();
        String LISTS_SOAP_ADDRESS = getWssSiteUrl(prefs) + Constants.LISTS_ASMX;
        String listToDisplay = prefs.getValue(Constants.PREF_WSS_LIST_NAME, Constants.NULL_STRING);
        String xslFile = getPortletContext().getRealPath(Constants.WSS_ADD_ITEM_TO_LIST);
        List FieldsToShow = WSSListsHelper.getFieldsToShow(LISTS_SOAP_ADDRESS, getWssSiteUsername(prefs), getWssSitePassword(prefs), listToDisplay, xslFile);
        portletRequest.setAttribute(Constants.WSS_FIELDS, FieldsToShow);
        PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.ADD_ITEM_TO_LIST);
        rd.include(portletRequest, portletResponse);

    }

    private void viewListItemBody(RenderRequest portletRequest, RenderResponse portletResponse) throws Exception {
        PortletPreferences prefs = portletRequest.getPreferences();
        PortletContext context = getPortletContext();
        String list = prefs.getValue("list", "");
        String id = prefs.getValue("id", "");
        String LISTS_SOAP_ADDRESS = getWssSiteUrl(prefs) + Constants.LISTS_ASMX;
        String xslUrl = portletResponse.encodeURL(Constants.WSS_LIST_TO_PORTAL_LIST);
        String xslFile = getPortletContext().getRealPath(xslUrl);
        String body = "";
        if (isProxySet(prefs)) {
            body = WSSListsHelper.getListBody(LISTS_SOAP_ADDRESS, getWssSiteUsername(prefs),
                    getWssSitePassword(prefs), list, id, xslFile, getProxyProperties(prefs));
        } else {
            body = WSSListsHelper.getListBody(LISTS_SOAP_ADDRESS, getWssSiteUsername(prefs),
                    getWssSitePassword(prefs), list, id, xslFile);
        }
        portletRequest.setAttribute(Constants.LIST_BODY, body);
        PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.LIST_DETAIL_JSP);
        rd.include(portletRequest, portletResponse);

    }


    private void viewListDetail(RenderRequest portletRequest, RenderResponse portletResponse) throws Exception,
            PortletException, TransformerException, JAXBException,
            ParserConfigurationException, SAXException,
            SOAPException, ServiceException {
        PortletPreferences prefs = portletRequest.getPreferences();
        if (isNullStringValue(getWssSiteUrl(prefs))) {
            writeSiteNotRespondingError(portletResponse);
        } else {
            List NamedListData;
            String LISTS_SOAP_ADDRESS = getWssSiteUrl(prefs) + Constants.LISTS_ASMX;
            String VIEWS_SOAP_ADDRESS = getWssSiteUrl(prefs) + Constants.VIEWS_ASMX;
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
            // System.out.println(viewLinks.toString());
            //debug("viewLinks",viewLinks.toString());
//            HashMap requiredAttributes = new HashMap();
//            requiredAttributes.put(Constants.WSS_URL,LISTS_SOAP_ADDRESS);
//            requiredAttributes.put(Constants.WSS_USERNAME,getWssSiteUsername(prefs));
//            requiredAttributes.put(Constants.WSS_PASSWORD,getWssSitePassword(prefs));
//            requiredAttributes.put(Constants.WSS_XSLT_FILE,xslFile);
//            requiredAttributes.put(Constants.WSS_VIEW_NAME,viewName);
//            requiredAttributes.put(Constants.WSS_LIST_NAME,listToDisplay);
//            requiredAttributes.put(Constants.WSS_PORTLET_URL,new UrlWrapper(portletResponse.createActionURL()));

            if (isProxySet(prefs)) {
                NamedListData = WSSListsHelper.getNamedListData(getListVariablesHashMap(portletRequest, portletResponse, LISTS_SOAP_ADDRESS, xslFile, viewName, listToDisplay), getProxyProperties(prefs));
            } else {
                NamedListData = WSSListsHelper.getNamedListData(getListVariablesHashMap(portletRequest, portletResponse, LISTS_SOAP_ADDRESS, xslFile, viewName, listToDisplay));
            }
            debug("NamedListData", NamedListData.toString());
            portletResponse.setTitle(displayName);
            portletRequest.setAttribute(Constants.WSS_SINGLE_LIST, NamedListData);
            portletRequest.setAttribute(Constants.XSLT_URL, "http://" + portletRequest.getServerName() + ":" + portletRequest.getServerPort()
                    + xslUrl);
            portletRequest.setAttribute("LIST_NAME", (String) portletRequest.getParameter("name"));
            PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.LIST_INDEX_JSP);
            rd.include(portletRequest, portletResponse);


        }


    }


    private void showAllAvailableWssLists(RenderRequest portletRequest, RenderResponse portletResponse, PortletContext context) throws Exception, PortletException, JAXBException, TransformerException, ServiceException {
        PortletPreferences prefs = portletRequest.getPreferences();

        if (isNullStringValue(getWssSiteUrl(prefs))) {
            // writeNoSitedefinedError(portletResponse);
            throw new IllegalArgumentException("SITE URL CANNOT BE NULL");
        } else {

            String LISTS_SOAP_ADDRESS = getWssSiteUrl(prefs) + Constants.LISTS_ASMX;
            String xslUrl = portletResponse.encodeURL(Constants.WSS_LIST_COLLECTION_TO_PORTAL_LIST_COLLECTION);
            String xslFile = getPortletContext().getRealPath(xslUrl);
            debug("showAllAvailableWssLists", "xsl File" + xslFile);
            //System.out.println(xslFile);
            List listCollection = null;
            if (isProxySet(prefs)) {
                listCollection = WSSListsHelper.getAllLists(LISTS_SOAP_ADDRESS, getWssSiteUsername(prefs), getWssSitePassword(prefs), xslFile, getProxyProperties(prefs));
            } else {
                listCollection = WSSListsHelper.getAllLists(LISTS_SOAP_ADDRESS, getWssSiteUsername(prefs), getWssSitePassword(prefs), xslFile);
            }
            //System.out.println(" list collection " + listCollection.toString());

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

    private HashMap getListVariablesHashMap(RenderRequest portletRequest, RenderResponse portletResponse, String listSoapAddress, String xslfile, String viewname, String listtodisplay) throws IOException {

        PortletPreferences prefs = portletRequest.getPreferences();
        // if(requiredListAttributes.size()==0){
        Properties props = new Properties();
        props.load(new FileInputStream(getPortletContext().getRealPath("/WEB-INF/icons.properties")));
        Enumeration en = props.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String value = (String) props.get(key);
            requiredListAttributes.put(key, portletResponse.encodeURL(portletRequest.getContextPath() + value));
        }
        requiredListAttributes.put(Constants.WSS_URL, listSoapAddress);
        requiredListAttributes.put(Constants.WSS_USERNAME, getWssSiteUsername(prefs));
        requiredListAttributes.put(Constants.WSS_PASSWORD, getWssSitePassword(prefs));
        requiredListAttributes.put(Constants.WSS_XSLT_FILE, xslfile);
        requiredListAttributes.put(Constants.WSS_VIEW_NAME, viewname);
        requiredListAttributes.put(Constants.WSS_LIST_NAME, listtodisplay);
        requiredListAttributes.put(Constants.WSS_PORTLET_URL, new UrlWrapper(portletResponse.createActionURL()));

        //  }
        return requiredListAttributes;

    }

    private void displayEditPages(RenderRequest portletRequest, RenderResponse portletResponse, PortletContext context) throws Exception, PortletException, JAXBException, TransformerException, ServiceException {

        String editMode = portletRequest.getParameter("edit");

        if (editMode.equalsIgnoreCase("site")) {
            PortletRequestDispatcher rd = context.getRequestDispatcher(Constants.WSS_EDIT_SITE_JSP);
            rd.include(portletRequest, portletResponse);

        } else if (editMode.equalsIgnoreCase("list")) {
            // Show list of all available lists on the selected WSS site
            showAllAvailableWssLists(portletRequest, portletResponse, context);
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
        clearpreferences(request);
        saveSiteInfo(request, response);
        if (request.getParameter("body") != null && (request.getParameter("body")).equals("back")) {
            response.setPortletMode(PortletMode.VIEW);
        }

        if (request.getParameter("viewbody") != null) {
            PortletPreferences prefs = request.getPreferences();
            prefs.setValue("list", request.getParameter("list"));
            prefs.setValue("id", request.getParameter("id"));
            prefs.setValue("viewbody", request.getParameter("viewbody"));
            prefs.store();
            response.setPortletMode(PortletMode.VIEW);
        }
        if (request.getParameter("view") != null) {

            PortletPreferences prefs = request.getPreferences();
            prefs.setValue("viewName", request.getParameter("view"));
            prefs.setValue("viewbody", "");
            prefs.store();
            response.setPortletMode(PortletMode.VIEW);
        }

        if (request.getParameter("edit") != null) {

            response.setRenderParameter("edit", request.getParameter("edit"));
        }

        if (request.getParameter("saveListSetting") != null) {


            response.setRenderParameter("saveListSetting", request.getParameter("saveListSetting"));
            saveListInfo(request, response);

        }
        if (request.getParameter("additem") != null) {
            response.setRenderParameter("additem", request.getParameter("additem"));
        }
        if (request.getParameter("itemadded") != null) {
            response.setRenderParameter("itemadded", request.getParameter("itemadded"));
            Map fieldsMap = request.getParameterMap();
            response.setRenderParameters(fieldsMap);
        }
        if (request.getParameter("itemnotadded") != null) {
            response.setPortletMode(PortletMode.VIEW);
        }

    }


    private void clearpreferences(ActionRequest request) throws ReadOnlyException, IOException, ValidatorException {
        PortletPreferences prefs = request.getPreferences();
        prefs.setValue("viewName", "");
        prefs.setValue("viewbody", "");
        prefs.store();
    }

    private void saveListInfo(ActionRequest request, ActionResponse response) throws ReadOnlyException, IOException, ValidatorException, PortletModeException {

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
