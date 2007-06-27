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
package com.sun.portal.xml.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

//import com.sun.portal.sharepoint.schemas.batch.Batch;

public class XMLUtils {
    private static DocumentBuilderFactory _factory = DocumentBuilderFactory.newInstance();

    public static Document createBareDocument(String rootElementString, String[] elements, String[] textForElements)
            throws ParserConfigurationException {
        DocumentBuilder builder = _factory.newDocumentBuilder();

        Document document = builder.newDocument();
        try {

            Element root = (Element) document.createElement(rootElementString);
            document.appendChild(root);

            for (int i = 0; i < elements.length; i++) {
                Element e = document.createElement(elements[i]);
                if (textForElements[i] != null)
                    e.appendChild(document.createTextNode(textForElements[i]));
                root.appendChild(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public static Document createBareDocument(String rootElementString, String nameSpaceUri, String[] elements,
                                              String[] textForElements) throws ParserConfigurationException {
        DocumentBuilder builder = _factory.newDocumentBuilder();

        Document document = builder.newDocument();
        try {

            Element root = (Element) document.createElementNS(nameSpaceUri, rootElementString);
            document.appendChild(root);

            for (int i = 0; i < elements.length; i++) {
                Element e = document.createElement(elements[i]);
                if (textForElements[i] != null)
                    e.appendChild(document.createTextNode(textForElements[i]));
                root.appendChild(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public static void prettyPrintXml(Document doc) throws TransformerException

    {
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer aTransformer = tranFactory.newTransformer();

        Source src = new DOMSource(doc);
        Result dest = new StreamResult(System.out);
        aTransformer.transform(src, dest);
    }

    public static void getAsXml(Document doc, Writer writer) throws TransformerException

    {
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer aTransformer = tranFactory.newTransformer();

        Source src = new DOMSource(doc);
        Result dest = new StreamResult(writer);
        aTransformer.transform(src, dest);
    }


    public static void getAsXml(Node node, Writer writer) throws TransformerException

    {
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer aTransformer = tranFactory.newTransformer();

        Source src = new DOMSource(node);
        Result dest = new StreamResult(writer);
        aTransformer.transform(src, dest);
    }

    public static void main(String[] args) throws ParserConfigurationException, TransformerException {
        Document d = createBareDocument("SANDEEP", new String[]{"SONI", "MONI"}, new String[]{"TSONI", "TMONI"});
        // prettyPrintXml(d);

        d = createBareDocument("SOAPSDK9:queryOptions", "http://schemas.microsoft.com/sharepoint/soap/",
                new String[]{}, new String[]{});
        prettyPrintXml(d);
    }

    public static String transformViaXslt(String xml, String xslFile) throws TransformerException {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        StringReader xmlreader = new StringReader(xml);

        Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xslFile)));

        StringWriter output = new StringWriter();

        transformer.transform(new StreamSource(xmlreader), new StreamResult(output));

        return output.getBuffer().toString();
    }


    public static Document parseAndReturnRootNode(InputSource source) throws ParserConfigurationException, SAXException, IOException {
        _factory.setNamespaceAware(true);
        DocumentBuilder db = _factory.newDocumentBuilder();
        return db.parse(source);
    }
}
