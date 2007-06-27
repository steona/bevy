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
 * WSSHelper.java
 *
 * Created on July 6, 2006, 7:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.portal.portlets.wss.helpers;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import org.apache.axis.message.MessageElement;
import org.w3c.dom.Node;

import com.sun.portal.xml.utils.XMLUtils;


public class WSSHelper {
    private static Logger logger = SharePointLogger.getLogger();

    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>";

    public static Object unmarshallFromXml(String packageName, String xmlString) throws JAXBException {
	JAXBContext jc = JAXBContext.newInstance(packageName, WSSHelper.class.getClassLoader());
	Unmarshaller u = jc.createUnmarshaller();
	return u.unmarshal(new StreamSource(new StringReader(xmlString)));
    }

    public static Unmarshaller getUnmarshaller(String packageName) throws JAXBException {
	JAXBContext jc = JAXBContext.newInstance(packageName);
	return jc.createUnmarshaller();
    }

    public static Marshaller getMarshaller(String packageName) throws JAXBException {
	JAXBContext jc = JAXBContext.newInstance(packageName);
	return jc.createMarshaller();
    }

    protected static String getXmlFromNode(Node se) throws TransformerException {
	StringWriter sw = new StringWriter();
	XMLUtils.getAsXml(se, sw);
	return sw.toString();
    }

    protected static void debug(String className, String methodName, String msg) {
	logger.log(Level.INFO, className + ":" + methodName + ":" + msg);
    }

    protected static String getXmlFragment(MessageElement[] elements) throws Exception {
	StringBuffer sb = new StringBuffer(XML_HEADER);
	for (int i = 0; i < elements.length; i++) {
	    sb.append(elements[i].getAsString());
	}
	return sb.toString();
    }

}
