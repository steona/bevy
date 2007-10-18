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
* Do Let Me Know by Email if you used the software and found it useful.
*
*/
package com.sun.portal.rss.feed;

import java.io.BufferedReader;
import javax.portlet.GenericPortlet;
import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderResponse;
import javax.portlet.PortletException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.sun.syndication.feed.WireFeed;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * FeedAggregator Portlet Class
 */
public class FeedAggregator extends GenericPortlet {

    private static final String ALL_FEEDS_XML = "/WEB-INF/resources/feeds.xml";

    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
	PortletPreferences prefs = request.getPreferences();

	if (request.getParameter("add_feed") != null) {
	    String newFeedName = request.getParameter("feedName");
	    String newFeedUrl = request.getParameter("feedUrl");
	    String typeOfFeed;

	    try {
		typeOfFeed = getFeedType(newFeedUrl);
	    } catch (FeedException e) {
		throw new PortletException("The feed URL provided is probably not correct", e);
	    }
	    // its an action to add a feed to the preferences.
	    String key = System.currentTimeMillis() + "";
	    prefs.setValues(key, new String[] { newFeedName, newFeedUrl, typeOfFeed });
	    prefs.store();
	} else if (request.getParameter("delete_feed") != null) {
	    System.out.println("Removing the entry for the feed with ID : " + request.getParameter("delete_feed"));
	    prefs.reset(request.getParameter("delete_feed"));
	    prefs.store();
	}
    }

    public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
	response.setContentType("text/html");
	displayPreferencesAndStoreAsRequestAttribute(request);
	PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher("/WEB-INF/jsp/FeedAggregator_view.jsp");
	dispatcher.include(request, response);
    }

    public void doEdit(RenderRequest request, RenderResponse response) throws PortletException, IOException {
	response.setContentType("text/html");
	PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher("/WEB-INF/jsp/FeedAggregator_edit.jsp");
	displayPreferencesAndStoreAsRequestAttribute(request);
	dispatcher.include(request, response);
    }

    public void doHelp(RenderRequest request, RenderResponse response) throws PortletException, IOException {

	response.setContentType("text/html");
	PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher("/WEB-INF/jsp/FeedAggregator_help.jsp");
	dispatcher.include(request, response);
    }

    private void displayPreferencesAndStoreAsRequestAttribute(RenderRequest request) {
	HashMap<String, String[]> map = new HashMap<String, String[]>();

	PortletPreferences prefs = request.getPreferences();
	Enumeration<String> enums = prefs.getNames();
	while (enums.hasMoreElements()) {
	    String key = enums.nextElement();
	    String[] vals = prefs.getValues(key, new String[] {});
	    for (String string : vals) {
		System.out.println("++++++++++" + key + " = " + string);
	    }
	    map.put(key, vals);
	}
	request.setAttribute("feed_list_map", map);
    }

    private HashMap preferencesToMap(ResourceRequest request) {
	HashMap<String, String[]> map = new HashMap<String, String[]>();

	PortletPreferences prefs = request.getPreferences();
	Enumeration<String> enums = prefs.getNames();
	while (enums.hasMoreElements()) {
	    String key = enums.nextElement();
	    String[] vals = prefs.getValues(key, new String[] {});
	    map.put(key, vals);
	}
	return map;
    }

    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
	String resourceID = request.getResourceID();

	if (resourceID.equals("selected_feed")) {
	    response.setContentType("text/xml");
	    String feedId = request.getParameter("feed_id");
	    if (feedId == null) {
		throw new PortletException("Required parameter, invoice, is missing.");
	    } else {
		String content = getFeed(request, feedId);
		writeToOutputStream(response, content);
	    }
	} else if (resourceID.equals("js")) {
	    response.setContentType("text/html");
	    String content = getContents(request.getParameter("path"));
	    writeToOutputStream(response, content);
	} else if (resourceID.equals("get_all_feeds")) {
	    response.setContentType("text/xml");
	    HashMap feedMap = preferencesToMap(request);
	    FeedXmlCreator creator = new FeedXmlCreator(feedMap);
	    creator.writeDocument(response.getWriter());
	}
    }

    private String getFeed(ResourceRequest request, String feedId) throws MalformedURLException {
	PortletPreferences prefs = request.getPreferences();
	String[] feedInfo = prefs.getValues(feedId, new String[] {});
	URL feedUrl = new URL(feedInfo[1]);
	StringBuffer buff = new StringBuffer();

	try {
	    HttpURLConnection.setFollowRedirects(true);
	    HttpURLConnection conn = (HttpURLConnection) feedUrl.openConnection();
	    conn.connect();

	    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = "";

		while ((line = in.readLine()) != null) {
		    buff.append(line);
		}
		return buff.toString();
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return "";
    }

    public String getContents(String path) {
	if (path != null) {
	    InputStream is = getPortletContext().getResourceAsStream(path);
	    if (is == null) {
		return "Requested Resource Not found at " + path + "!!!";
	    }
	    StringBuffer contents = new StringBuffer();

	    BufferedReader input = null;
	    try {

		input = new BufferedReader(new InputStreamReader(is));
		String line = null; // not declared within while loop
		/*
		 * readLine is a bit quirky : it returns the content of a line
		 * MINUS the newline. it returns null only for the END of the
		 * stream. it returns an empty String if two newlines appear in
		 * a row.
		 */
		while ((line = input.readLine()) != null) {
		    contents.append(line);
		    contents.append(System.getProperty("line.separator"));
		}
	    } catch (IOException ex) {
		ex.printStackTrace();
	    } finally {
		try {
		    if (input != null) {
			// flush and close both "input" and its underlying
			// FileReader
			input.close();
		    }
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
	    }
	    return contents.toString();
	} else {
	    return "Path is NULL";
	}

    }

    private void writeToOutputStream(ResourceResponse response, String content) throws IOException {
	PrintWriter writer = response.getWriter();
	writer.print(content);
    }

    private String getFeedType(String fUrl) throws IOException, IllegalArgumentException, FeedException {
	URL feedUrl = new URL(fUrl);
	SyndFeedInput input = new SyndFeedInput();
	SyndFeed inFeed = input.build(new XmlReader(feedUrl));
	return inFeed.getFeedType();
    }
}