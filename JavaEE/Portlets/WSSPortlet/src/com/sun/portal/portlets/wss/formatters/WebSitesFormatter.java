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
package com.sun.portal.portlets.wss.formatters;

import java.util.ArrayList;
import java.util.List;

import com.sun.portal.sharepoint.schemas.sites.Web;

/**
 * Created by IntelliJ IDEA. User: admin Date: Apr 23, 2007 Time: 5:07:42 PM To
 * change this template use File | Settings | File Templates.
 */
public class WebSitesFormatter {

    public static List getFormattedWebNames(String username, String password, Web website) {
	List thisrow = new ArrayList();
	String Title = website.getTitle();
	String siteurl = website.getUrl();
	// int i = website.indexOf("/_vti_bin");

	// String siteurl = website.substring(0,i);
	siteurl = siteurl.replaceAll("http://", "");
	String formattedUrl = new StringBuilder().append("http://").append(username).append(":").append(password).append("@").append(siteurl).toString();
	String modstr = "<a href=\"#\" onclick=\"window.open(\'" + formattedUrl + "\'" + ",\'Sites\',\'width=400,height=350,resizable=1\');" + "\">" + siteurl + "</a>";
	thisrow.add(modstr);
	thisrow.add(siteurl);
	return thisrow;
    }

    public static String getFormattedWebNamesString(String username, String password, String website) {
	int i = website.indexOf("/_vti_bin");
	String siteurl = website.substring(0, i);
	siteurl = siteurl.replaceAll("http://", "");
	String formattedUrl = new StringBuilder().append("http://").append(username).append(":").append(password).append("@").append(siteurl).toString();
	// String modstr = "<a href=\"#\" onclick=\"window.open(\\\'" +
        // formattedUrl + "\\\'" +
        // ",\\\'Sites\\\',\\\'width=400,height=350,resizable=1\\\');" + "\">" +
        // siteurl + "</a>";
	String modstr = "<a href=" + formattedUrl + " target=\"_blank\">" + siteurl + "</a>";
	return modstr;
    }

    public static String getFormattedWebNamesString(String username, String password, Web website) {

	String title = website.getTitle();
	String siteurl = website.getUrl();
	siteurl = siteurl.replaceAll("http://", "");
	String formattedUrl = new StringBuilder().append("http://").append(username).append(":").append(password).append("@").append(siteurl).toString();
	// String modstr = "<a href=\"#\" onclick=\"window.open(\\\'" +
        // formattedUrl + "\\\'" +
        // ",\\\'Sites\\\',\\\'width=400,height=350,resizable=1\\\');" + "\">" +
        // title + "</a>";
	String modstr = "<a href=" + formattedUrl + " target=\"_blank\">" + title + "</a>";

	return modstr;
    }
}
