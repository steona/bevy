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

import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class FeedXmlCreator {

    private HashMap feedMap;
    private Document feedListDocument = null;

    public FeedXmlCreator(HashMap feedMap) {
	this.feedMap = feedMap;
	createDocument();
    }

    private void createDocument() {
	// Create the root element
	Element feedList = new Element("feed-list");
	// create the document
	feedListDocument = new Document(feedList);

	for (Iterator iterator = feedMap.keySet().iterator(); iterator.hasNext();) {
	    String key = (String) iterator.next();
	    String[] feedValues = (String[]) feedMap.get(key);
	    Element feedElement = createFeedElement(key, feedValues);
	    feedList.addContent(feedElement);
	}
    }

    private Element createFeedElement(String key, String[] feedValues) {
	Element feedEntry = new Element("feed-entry");
	// name url type
	feedEntry.setAttribute("id", key);
	feedEntry.setAttribute("type", feedValues[2]);

	Element feedName = new Element("name");
	feedName.setText(feedValues[0]);

	Element feedUrl = new Element("url");
	feedUrl.setText(feedValues[1]);

	feedEntry.addContent(feedName);
	feedEntry.addContent(feedUrl);

	return feedEntry;
    }

    public void writeDocument(Writer out) {
	try {
	    XMLOutputter outputter = new XMLOutputter();
	    outputter.output(feedListDocument, out);
	} catch (java.io.IOException e) {
	    e.printStackTrace();
	}
    }

}
