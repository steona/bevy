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
/*
 * WSSListsHelperTest.java
 * JUnit based test
 *
 * Created on August 1, 2006, 4:07 PM
 */

package com.sun.portal.portlets.wss.helpers;

import junit.framework.*;
import com.sun.accessibility.internal.resources.accessibility;
import com.sun.portal.sharepoint.schemas.list.collections.WssList;
import com.sun.portal.sharepoint.schemas.list.collections.WssListCollection;

import java.io.StringReader;
import java.net.*;

//import com.sun.xml.rpc.client.StubBase;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.handler.HandlerChain;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.namespace.QName;

import javax.xml.rpc.*;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPFactory;

import com.sun.portal.xml.utils.*;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.StringWriter;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.sun.portal.sharepoint.schemas.list.single.WssSingleList;
import com.sun.portal.sharepoint.schemas.list.single.ViewField;
import org.xml.sax.SAXParseException;

import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author root
 */
public class WSSListsHelperTest extends TestCase {

    String listSoapAddress = null;
    String listName = "Shared Documents";
    String sitename = null;
    String userName = null;
    String password = null;
    String xsldirectory = null;
    String singlelist = null;
    String collectionslist = null;

    public WSSListsHelperTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(System.getProperty("sharepoint.test.properties")));
            sitename = properties.getProperty("wss.site.url");
            listSoapAddress = sitename + "/_vti_bin/Lists.asmx";
            userName = properties.getProperty("wss.site.username");
            password = properties.getProperty("wss.site.password");
            xsldirectory = System.getProperty("xsl.directory");
            singlelist = xsldirectory + "/newsingleWssList.xsl";
            collectionslist = xsldirectory + "/wss-list-collections.xsl";

        } catch (IOException e) {
            System.out.println("Not able to read the specified file");
        }

    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(WSSListsHelperTest.class);

        return suite;
    }


    /**
     * Test of getNamedListData method, of class com.sun.portal.portlets.wss.helpers.WSSListsHelper.
     */
    public void testGetNamedListData() throws Exception {
        String expResult = "[Attachments, LinkTitle, FirstName, Company, WorkPhone, HomePhone, Email]";
        // List result = WSSListsHelper.getNamedListData(listSoapAddress, listName, userName, password, singlelist);
        // assertEquals("getNamedListData", expResult, result.get(0).toString());

    }

    /**
     * Test of getAllLists method, of class com.sun.portal.portlets.wss.helpers.WSSListsHelper.
     */
    public void testGetAllLists() throws Exception {
        String expResult = "Use the Announcements list to post messages on the home page of your site.";
        List result = WSSListsHelper.getAllLists(listSoapAddress, userName, password, collectionslist);
        assertEquals("testGetAllLists", expResult, ((WssList) result.get(0)).getDescription());
    }


}
