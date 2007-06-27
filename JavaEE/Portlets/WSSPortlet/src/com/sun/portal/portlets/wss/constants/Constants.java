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
package com.sun.portal.portlets.wss.constants;

public interface Constants {

    public static final boolean INTRANET = false;

    public static String NULL_STRING = "";

    public static String NULL_LIST = "nulllist";

    public static String SITE = "site";

    public static String MESSAGE = "message";

    public static final String NO_WSS_SITE_DEFINED = "NO_WSS_SITE_DEFINED";

    // meetings related view Files

    public static final String MEETINGS_EDIT_JSP = "/meetings/edit_meetings.jsp";

    public static final String MEETINGS_EDIT_LIST_JSP = "/meetings/edit_meetings_detail.jsp";

    // List related View Files

    public static final String LIST_INDEX_JSP = "/list/index_list.jsp";

    public static final String LIST_DETAIL_JSP = "/list/list_detail.jsp";

    public static final String LIST_EDIT_JSP = "/list/edit_list.jsp";

    public static final String WSS_EDIT_SITE_JSP = "/edit_wss_site.jsp";

    public static final String LIST_EDIT_LIST_JSP = "/list/edit_list_detail.jsp";

    public static final String ADD_ITEM_TO_LIST = "/list/add_item_list.jsp";

    // Alerts Related Views

    public static final String ALERT_INDEX_JSP = "/alert/index.jsp";

    // Sites related jsps

    public static final String WSS_SITES_JSP = "/sites/sites.jsp";

    // Request keys that the views look up, which the portlet would have set
        // earlier.
    public static final String XSLT_URL = "WSS_PORTLET_XSL_URL";

    public static final String XML_FRAGMENT_STRING = "WSS_PORTLET_XML";

    public static final String WSS_LIST_COLLECTION = "WSS_LIST_COLLECTION";

    public static final String WSS_MEETINGS_COLLECTION = "WSS_MEETINGS_COLLECTION";

    public static final String WSS_SINGLE_LIST = "WSS_SINGLE_LIST";

    public static final String WSS_FIELDS = "WSS_FIELDS";

    public static final String WSS_VIEWS_LIST = "WSS_VIEWS_LIST";

    public static final String WSS_TEMPLATES_LIST = "WSS_TEMPLATES_LIST";

    public static final String WSS_WEB_SITES_LIST = "WSS_WEB_SITES_LIST";

    public static final String LIST_BODY = "LIST_BODY";

    public static final String WSS_MEETINGS_LIST = "WSS_MEETINGS_LIST";

    // XSL Files to render views.

    public static final String WSS_LIST_COLLECTION_TO_PORTAL_LIST_COLLECTION = "/xsl/list/wss-list-collections.xsl";

    public static final String WSS_DOCUMENTS_COLLECTION_TO_PORTAL_DOCUMENTS_COLLECTION = "/xsl/documents/wss-documents-collection.xsl";

    public static final String WSS_LIST_TO_PORTAL_LIST = "/xsl/list/newsingleWssList.xsl";

    public static final String WSS_MEETINGS_COLLECTION_TO_PORTAL_MEETINGS_COLLECTION = "/xsl/meetings/wss-meeting-collections.xsl";

    public static final String WSS_WEB_TO_PORTAL_LIST = "/xsl/webs/websitesList.xsl";

    public static final String WSS_ADD_ITEM_TO_LIST = "/xsl/list/getFieldsToAdd.xsl";

    // WSS Individual ASMX files.
    public static final String LISTS_ASMX = "/_vti_bin/Lists.asmx";

    public static final String ALERTS_ASMX = "/_vti_bin/Alerts.asmx";

    public static final String VIEWS_ASMX = "/_vti_bin/Views.asmx";

    public static final String SITES_ASMX = "/_vti_bin/Sites.asmx";

    public static final String WEBS_ASMX = "/_vti_bin/Webs.asmx";

    // Portlet Preferences Common Key Names

    public static String PREF_WSS_URL = "wss.site.url";

    public static String PREF_WSS_DISPLAY_NAME = "wss.site.display.name";

    public static String PREF_WSS_USERNAME = "wss.site.username";

    public static String PREF_WSS_PASSWORD = "wss.site.password";

    // public static String PREF_WSS_LIST_NAME = "PREF_WSS_LIST_NAME";
    public static String PREF_WSS_LIST_NAME = "wss.site.list.name";

    public static String PREF_WSS_MEETING_NAME = "wss.site.meeting.name";

    // Strings representing the base Types.

    public static String BASE_TYPE_DISCUSSIONS = "3";

    public static String BASE_TYPE_LIBRARIES = "1";

    public static String BASE_TYPE_LISTS = "0";

    public static String MEETINGS_ASMX = "/_vti_bin/meetings.asmx";

    // String for NoBody
    public static String NO_BODY = "No Body present";

    // preference names for the proxy variables
    public static String PROXY_SET = "";

    public static String PROXY_USER = "proxyuser";

    public static String PROXY_PASSWORD = "proxypassword";

    public static String PROXY_HOST = "proxyhost";

    public static String PROXY_PORT = "proxyport";

    public static String WSS_WEB_SITES_JSP = "/web/websites.jsp";

    // Required by the Helpers from the client(portlets)

    public static String WSS_SOAP_ADDRESS = "listSoapAddress";

    public static String WSS_USERNAME = "username";

    public static String WSS_PASSWORD = "password";

    public static String WSS_URL = "wssurl";

    public static String WSS_BODYVIEW = "bodyview";

    public static String WSS_LIST = "wsslist";

    public static String WSS_LIST_NAME = "listname";

    public static String WSS_XML = "wssxml";

    public static String WSS_XSLT_FILE = "xsltfile";

    public static String WSS_VIEW_NAME = "viewname";

    public static String WSS_PORTLET_URL = "portleturl";

    public static String WSS_LIST_ID = "listid";

    public static String WSS_PRESENT_VIEW = "presentView";

}
