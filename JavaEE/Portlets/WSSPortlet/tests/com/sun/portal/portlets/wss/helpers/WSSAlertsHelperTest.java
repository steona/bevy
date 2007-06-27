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
 * WSSAlertsHelperTest.java
 * JUnit based test
 *
 * Created on August 10, 2006, 2:20 PM
 */

package com.sun.portal.portlets.wss.helpers;

import junit.framework.*;
import com.microsoft.schemas.sharepoint.soap.alerts.*;

import java.net.*;

//import com.sun.xml.rpc.client.StubBase;

import java.rmi.RemoteException;
import javax.xml.rpc.handler.HandlerChain;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.namespace.QName;

//import com.sun.xml.rpc.encoding.*;
//import com.sun.xml.rpc.client.ServiceExceptionImpl;
//import com.sun.xml.rpc.util.exception.*;
//import com.sun.xml.rpc.soap.SOAPVersion;
//import com.sun.xml.rpc.client.HandlerChainImpl;
//
import javax.xml.rpc.*;
import java.util.Properties;
import java.io.IOException;
import java.io.FileInputStream;

/**
 * @author root
 */
public class WSSAlertsHelperTest extends TestCase {

    String listSoapAddress = null;
    String sitename = null;
    String userName = null;
    String password = null;

    public WSSAlertsHelperTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(System.getProperty("sharepoint.test.properties")));
            sitename = properties.getProperty("wss.site.url");
            listSoapAddress = sitename + "/_vti_bin/Alerts.asmx";
            userName = properties.getProperty("wss.site.username");
            password = properties.getProperty("wss.site.password");
        } catch (IOException e) {

            System.out.println("Not able to Read the Specified File");

        }
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(WSSAlertsHelperTest.class);

        return suite;
    }


    /**
     * Test of getAllAlerts method, of class com.sun.portal.portlets.wss.helpers.WSSAlertsHelper.
     */
    public void testGetAllAlerts() throws Exception {
        System.out.println("getAllAlerts");

        int expResult = 0;
        AlertInfo result = WSSAlertsHelper.getAllAlerts(listSoapAddress, userName, password);
//        System.out.println(result.getAlerts().getAlert().toString());
//        assertEquals("testGetAllAlerts", expResult, ((Alert[]) result.getAlerts().getAlert()).length);


    }

}
