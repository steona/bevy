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
package com.sun.portal.portlets.wss.helpers;

import com.microsoft.schemas.sharepoint.soap.list.*;
import com.sun.portal.portlets.wss.constants.Constants;
import com.sun.portal.portlets.wss.formatters.Formatter;
import com.sun.portal.portlets.wss.html.generators.HtmlGenerator;
import com.sun.portal.sharepoint.schemas.batch.Batch;
import com.sun.portal.sharepoint.schemas.batch.Field;
import com.sun.portal.sharepoint.schemas.batch.Method;
import com.sun.portal.sharepoint.schemas.list.collections.WssListCollection;
import com.sun.portal.sharepoint.schemas.list.fields.ListField;
import com.sun.portal.sharepoint.schemas.list.fields.ListFields;
import com.sun.portal.sharepoint.schemas.list.single.ViewField;
import com.sun.portal.sharepoint.schemas.list.single.WssSingleList;
import com.sun.portal.xml.utils.XMLUtils;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.PrefixedQName;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.*;




public class WSSListsHelper extends WSSHelper {

    private static final String SINGLE_LIST_NAME = "{A11B2CDE-F7FB-4B64-A648-00F2DB088157}";

    private static final String SINGLE_Document_NAME = "test for documents";

    private static final String password = "abc123";

    private static final String viewName = "";
    /*
         * When trying to run and see the individual file you should mention the
         * site name entirely for Lists i.e siteurl/_vti_bin/Lists.asmx
         */
    private static final String sitename = "http://localhost:7787/_vti_bin/Lists.asmx";

    private static final String username = "Administrator";

    private static final String XslLocation = "E:\\Sharepoint\\poc\\india\\share1.5.1\\web-src\\xsl\\list\\getFieldsToAdd.xsl";

    private static final String collectionXslLocation = "Z:\\space\\ws\\share1.5.1\\web-src\\xsl\\list\\wss-list-collections.xsl";

    private static String LISTS_SOAP_PORT_NAME = "ListsSoap";

    public WSSListsHelper() {

    }


    public static void main(String[] args) throws Exception {

        try {
// ResourceBundle rbundle = null;
// rbundle = ResourceBundle.getBundle("unittest");
// String siteurl= rbundle.getString(Constants.PREF_WSS_URL);
// String username = rbundle.getString(Constants.PREF_WSS_USERNAME);
// String password = rbundle.getString(Constants.PREF_WSS_PASSWORD);
// System.out.println(siteurl+username+password);
            // System.setProperty("http.proxyPort", "8080");
            // System.setProperty("http.proxyHost",
                // "webcache.central.sun.com");
            // List collection =
                // getAllLists(sitename,username,password,collectionXslLocation);
            // System.out.println("collection result" + collection);
// String listandview =
// getListAndView(sitename,username,password,SINGLE_LIST_NAME,"");
// String list = getList(sitename,SINGLE_LIST_NAME,username,password);
// System.out.println("list and View --->"+listandview);
// System.out.println("List --->"+ list);
            // System.out.println(listandview);
            List list = getNamedListData(sitename, SINGLE_LIST_NAME, username, password, XslLocation, viewName);
            // String listview =
                // getListAndView(sitename,username,password,SINGLE_LIST_NAME,viewName)
                // ;
            // List ls1 = refineNamedListData(list,SINGLE_LIST_NAME);
            // addList(sitename,username,password,"Murali","adding list from
                // code",104);
            // deleteList(sitename,username,password,"Murali");
            // getAttachmentCollection(sitename,username,password,"Announcements","10");
            // System.out.println("examining " + list);
            // deleteList(sitename,username,password,"Events");
            // addList(sitename,username,password,"TryingCode","TestingTitle",104);
            // updateListItems(sitename,username,password,"Shared
                // Documents","Delete");
            // getFieldsToShow(sitename,username,password,SINGLE_LIST_NAME,XslLocation);
        } catch (Exception e) {
            e.printStackTrace();


        }


    }

    public static List getFieldsToShow(String listSoapAddress, String username, String password, String listname, String xsltFilePath) throws Exception {
        ArrayList GeneratedFields = new ArrayList();
        String listAndViewXml = getListAndView(listSoapAddress, username, password, listname, "");
        String trXml = XMLUtils.transformViaXslt(listAndViewXml, xsltFilePath);
        System.out.println(listAndViewXml);
        ListFields fields = (ListFields) unmarshallFromXml("com.sun.portal.sharepoint.schemas.list.fields", trXml);
        List fieldsList = (List) fields.getListField();
        for (int i = 0; i < fieldsList.size(); i++) {
            ArrayList row = new ArrayList();
            ListField presentField = (ListField) fieldsList.get(i);
            String displayName = presentField.getDisplayName();
            String type = presentField.getType();
            String name = presentField.getName();
            String generatedField = HtmlGenerator.getHtmlForField(type, name);
            row.add(displayName);
            row.add(generatedField);
            GeneratedFields.add(row);
        }
        return GeneratedFields;
    }

    public static void updateListItems(String listSoapAddress, String username, String password, String title, String action, Map fieldMap) throws Exception, JAXBException, TransformerException, RemoteException {

        ListsSoapStub stub = returnStub(listSoapAddress, username, password);
        Batch impl = new Batch();
        Method method = new Method();
        if (action.equalsIgnoreCase("new")) {
            method.setCmd("New");
            method.setID("1");
            Set fields = fieldMap.keySet();
            Iterator it = fields.iterator();
            while (it.hasNext()) {

                String key = (String) it.next();
                if (!key.equalsIgnoreCase("itemadded") && !key.equalsIgnoreCase("AddItem")) {
                    if (fieldMap.containsKey(key)) {
                        Field field = new Field();
                        field.setName(key);
                        String[] values = (String[]) fieldMap.get(key);

                        field.setValue(values[0]);
                        method.getField().add(field);
                    }
                }

            }

// FieldTypeImpl field2 = new FieldTypeImpl();
// field2.setName("Body");
// field2.setValue("Just Trying it out.!!!");
// FieldTypeImpl field3 = new FieldTypeImpl();
// field3.setName("BaseName");
// field3.setValue("Spartan");

// method.getField().add(field2);
            // method.getField().add(field3);
            impl.getMethod().add(method);

        } else if (action.equalsIgnoreCase("delete")) {
            Field field1 = new Field();
            field1.setName("ID");
            field1.setValue("1");
            Field field2 = new Field();
            field2.setName("FileRef");
            field2.setValue("http://129.158.227.228/Shared Documents/LICENSE.txt");
            // MethodTypeImpl method = new MethodTypeImpl();
            method.setCmd("Delete");
            method.setID("1");
            method.getField().add(field1);
            method.getField().add(field2);
            impl.getMethod().add(method);

        }
        Marshaller marshaller = getMarshaller("com.sun.portal.sharepoint.schemas.batch");
        MessageElement elements[] = new MessageElement[1];
        elements[0] = new MessageElement();
        marshaller.marshal(impl, elements[0]);
        elements[0].setQName(QName.valueOf("Batch"));
        UpdateListItemsUpdates updates = new UpdateListItemsUpdates();
        updates.set_any(elements);
        UpdateListItemsResponseUpdateListItemsResult result = stub.updateListItems(title, updates);
        System.out.println(getXmlFragment(result.get_any()));

        // stub.updateList
    }


    private static void deleteList(String listSoapAddress, String username, String password, String title) throws RemoteException, ServiceException {
        ListsSoapStub stub = returnStub(listSoapAddress, username, password);
        stub.deleteList(title);
    }

    private static void addList(String listSoapAddress, String username, String password, String title, String description, int templateId) throws RemoteException, TransformerException, Exception {

        ListsSoapStub stub = returnStub(listSoapAddress, username, password);
        AddListResponseAddListResult result = stub.addList(title, description, templateId);
        // System.out.println(getXmlFromNode(ne));
    }


    private static void getAttachmentCollection(String listSoapAddress, String username, String password, String title, String id) throws RemoteException, TransformerException, ServiceException {
        ListsSoapStub stub = returnStub(listSoapAddress, username, password);
        Node ne = (Node) stub.getAttachmentCollection(title, id);
        // System.out.println(getXmlFromNode(ne));
    }


    private static String getListAndView(String listSoapAddress, String listName, String username, String password) throws Exception {
        ListsSoapStub stub = returnStub(listSoapAddress, username, password);

        GetListAndViewResponseGetListAndViewResult res = stub.getListAndView(listName, "");

        String xmlString = getXmlFragment(res.get_any());

        // System.out.println("LIST AND VIEW\n" + xmlString +
        // "\n_____________________________");

        return xmlString;
    }

    private static String getList(String listSoapAddress, String listName, String username, String password) throws Exception {
        ListsSoapStub stub = returnStub(listSoapAddress, username, password);

        GetListResponseGetListResult res = stub.getList(listName);

        String xmlString = getXmlFragment(res.get_any());

        // System.out.println("LIST AND VIEW\n" + xmlString +
        // "\n_____________________________");

        return xmlString;
    }

    /*
         * In the portlet class we are using the getNamedListData method with
         * portletRequest and portletResponse parameters also which is below
         * this method.This method will be removed later
         */
    private static List getNamedListData(String listSoapAddress, String listName, String username, String password, String xsltFilePath, String viewName) throws RemoteException, TransformerException,
            Exception, SAXParseException {
        List list = new ArrayList();
        String junkString = "ows_";

        String listAndViewXml = getListAndView(listSoapAddress, username, password, listName, viewName);
        String trXml = XMLUtils.transformViaXslt(listAndViewXml, xsltFilePath);
        Unmarshaller u = getUnmarshaller("com.sun.portal.sharepoint.schemas.list.single");
        WssSingleList wssList = (WssSingleList) u.unmarshal((Source) new StringReader(trXml));

        List<ViewField> viewFields = wssList.getDefaultView().getViewField();
        list = addViewFieldsAsList(list, viewFields);
        // The format for the returned List is as follows.
        // Row 0 - Contains a list of strings. Each element in this list is the
        // name of the viewable field.
        // Row 1... - Contains a list of strings. Each row is the row of
        // sharepoint site list, with values for the viewable fields in order.

        // printSingleListInfo(wssList);

        String xml = getListItemsFromWss(listSoapAddress, username, password, wssList);
        // System.out.println("List Items : " + xml);

        Document doc = XMLUtils.parseAndReturnRootNode(new InputSource(new StringReader(xml)));
        NodeList nodeList = doc.getElementsByTagNameNS("*", "row"); // List
                                                                        // of
                                                                        // all
                                                                        // rows
                                                                        // returned
        for (int i = 0; i < nodeList.getLength(); i++) {
            List thisRow = new ArrayList();
            Node n = nodeList.item(i); // i-th Row that was returned.
            NamedNodeMap nodemap = n.getAttributes(); // Get all attributes
                                                        // from this node

            Iterator iter = viewFields.iterator();
            while (iter.hasNext()) {
                ViewField element = (ViewField) iter.next();
                String fieldName = element.getName();
                // Does this ViewField exist in this row of data returned. we
                // need to check this
                // becasues WSS returns weird data. If you ask it to return 7
                // fields in the data.
                // For each row returned it will not have all 7 fields. Fields
                // which have no value
                // are not returned for that row.... WEIRD!!
                Node attributeNode = nodemap.getNamedItem(junkString + fieldName);
                if (attributeNode == null)
                    thisRow.add("");
                else
                    thisRow.add(attributeNode.getNodeValue());
            }

            /*
                 * System.out.println("Row Number : " + i ); for (int j = 0; j <
                 * nodemap.getLength(); j++) { Node attrNode = nodemap.item(j);
                 * if ( attrNode.getNodeName().startsWith(junkString))
                 * System.out.println(attrNode.getNodeName().replaceAll(junkString,"")+
                 * "===" + attrNode.getNodeValue()); }
                 */

            list.add(thisRow);
        }
        // System.out.println(list.toString());
        return list;
    }

    private static String getListItemsFromWss(String listSoapAddress, String username, String password, WssSingleList wssList) throws ServiceException, RemoteException, Exception {
        ListsSoapStub stub = returnStub(listSoapAddress, username, password);

        MessageElement msg = createViewFieldsMessage(wssList.getDefaultView().getViewField());
        GetListItemsViewFields fields = new GetListItemsViewFields();
        fields.set_any(new MessageElement[]{msg});

        GetListItemsResponseGetListItemsResult res = stub.getListItems(wssList.getName(), "", createQueryElement(), fields, "",
                createEmptyQueryOptions());

        String xml = getXmlFragment(res.get_any());
        return xml;
    }

    private static List addViewFieldsAsList(List list, List<ViewField> viewFields) {

        Iterator iterator = viewFields.iterator();
        List temp = new ArrayList();
        while (iterator.hasNext()) {
            ViewField element = (ViewField) iterator.next();
            temp.add(element.getName());
        }
        list.add(temp);
        return list;
    }


    public static List getNamedListData(HashMap requiredAttributes) throws
            Exception, JAXBException, IOException, ParserConfigurationException, TransformerException, SOAPException, ServiceException {

        // String AllViews =
        // WSSViewsHelper.getViewsCollection(listSoapAddress,username,password,listName);
        String listSoapAddress = (String) requiredAttributes.get(Constants.WSS_URL);
        String username = (String) requiredAttributes.get(Constants.WSS_USERNAME);
        String password = (String) requiredAttributes.get(Constants.WSS_PASSWORD);
        String xsltFilePath = (String) requiredAttributes.get(Constants.WSS_XSLT_FILE);
        String listName = (String) requiredAttributes.get(Constants.WSS_LIST_NAME);
        String viewName = (String) requiredAttributes.get(Constants.WSS_VIEW_NAME);
        List list = new ArrayList();
        // System.out.println("------->" + listSoapAddress + username + password
        // + listName + viewName);
        String listAndViewXml = getListAndView(listSoapAddress, username, password, listName, viewName);
        // System.out.println(listAndViewXml);
        String trXml = XMLUtils.transformViaXslt(listAndViewXml, xsltFilePath);
        // System.out.println(trXml);
        WssSingleList wssList = (WssSingleList) unmarshallFromXml("com.sun.portal.sharepoint.schemas.list.single", trXml);
        List viewFields = wssList.getDefaultView().getViewField();
        list = addViewFieldsAsList(list, viewFields);
        String xml = getListItemsFromWss(listSoapAddress, wssList, username, password, viewName);
        String bodyView = WSSViewsHelper.getBodyViewOfList(listSoapAddress, username, password, listName);
        // System.out.println("Wanted Xml" + xml);
        requiredAttributes.put(Constants.WSS_BODYVIEW, bodyView);
        requiredAttributes.put(Constants.WSS_LIST, wssList);
        requiredAttributes.put(Constants.WSS_XML, xml);
        List formattedvalues = Formatter.getAllFormattedListValues(requiredAttributes);
        list.addAll(formattedvalues);
        return list;


    }

    public static List getNamedListData(HashMap requiredAttributes, Properties proxyProperties) throws
            Exception, JAXBException, IOException, ParserConfigurationException, TransformerException, SOAPException, ServiceException {

        // String AllViews =
        // WSSViewsHelper.getViewsCollection(listSoapAddress,username,password,listName);
        String listSoapAddress = (String) requiredAttributes.get(Constants.WSS_URL);
        String username = (String) requiredAttributes.get(Constants.WSS_USERNAME);
        String password = (String) requiredAttributes.get(Constants.WSS_PASSWORD);
        String xsltFilePath = (String) requiredAttributes.get(Constants.WSS_XSLT_FILE);
        String listName = (String) requiredAttributes.get(Constants.WSS_LIST_NAME);
        String viewName = (String) requiredAttributes.get(Constants.WSS_VIEW_NAME);
        List list = new ArrayList();
        // System.out.println("------->" + listSoapAddress + username + password
        // + listName + viewName);
        String listAndViewXml = getListAndView(listSoapAddress, username, password, listName, viewName, proxyProperties);
        // System.out.println(listAndViewXml);
        String trXml = XMLUtils.transformViaXslt(listAndViewXml, xsltFilePath);
        // System.out.println(trXml);
        WssSingleList wssList = (WssSingleList) unmarshallFromXml("com.sun.portal.sharepoint.schemas.list.single", trXml);
        List viewFields = wssList.getDefaultView().getViewField();
        list = addViewFieldsAsList(list, viewFields);
        String xml = getListItemsFromWss(listSoapAddress, wssList, username, password, viewName, proxyProperties);
        String bodyView = WSSViewsHelper.getBodyViewOfList(listSoapAddress, username, password, listName);
        // System.out.println("Wanted Xml" + xml);
        requiredAttributes.put(Constants.WSS_BODYVIEW, bodyView);
        requiredAttributes.put(Constants.WSS_LIST, wssList);
        requiredAttributes.put(Constants.WSS_XML, xml);
        List formattedvalues = Formatter.getAllFormattedListValues(requiredAttributes);
        list.addAll(formattedvalues);
        return list;


    }

    private static String getListAndView(String listSoapAddress, String username, String password, String listName, String viewName) throws Exception, RemoteException, ServiceException {

        ListsSoapStub stub = returnStub(listSoapAddress, username, password);
        GetListAndViewResponseGetListAndViewResult res = stub.getListAndView(listName, viewName);

        String xmlString = getXmlFragment(res.get_any());
        return xmlString;

    }

    private static String getListAndView(String listSoapAddress, String username, String password, String listName, String viewName, Properties proxyProperties) throws Exception, RemoteException, ServiceException {

        ListsSoapStub stub = returnStub(listSoapAddress, username, password, proxyProperties);
        GetListAndViewResponseGetListAndViewResult res = stub.getListAndView(listName, viewName);

        String xmlString = getXmlFragment(res.get_any());
        return xmlString;

    }

    public static List getAllLists(String listSoapAddress, String username, String password, String xslFile) throws Exception, RemoteException, JAXBException, ServiceException {
        String xmlString = getListCollectionFromWss(listSoapAddress, username, password);
        // System.out.println("xmlString " +xmlString);
        String trXmlString = XMLUtils.transformViaXslt(xmlString, xslFile);
        // System.out.println("trXMLString " +trXmlString);
        WssListCollection collection = (WssListCollection) unmarshallFromXml("com.sun.portal.sharepoint.schemas.list.collections", trXmlString);

        return collection.getWssList();
    }

    public static List getAllLists(String listSoapAddress, String username, String password, String xslFile, Properties proxyProperties) throws Exception, RemoteException, JAXBException, ServiceException {
        String xmlString = getListCollectionFromWss(listSoapAddress, username, password, proxyProperties);
        // System.out.println("xmlString " +xmlString);
        String trXmlString = XMLUtils.transformViaXslt(xmlString, xslFile);
        // System.out.println("trXMLString " +trXmlString);
        WssListCollection collection = (WssListCollection) unmarshallFromXml("com.sun.portal.sharepoint.schemas.list.collections", trXmlString);

        return collection.getWssList();
    }

//
// private static List addViewFieldsAsList(List list, List viewFields) {
// Iterator iterator = viewFields.iterator();
// List temp = new ArrayList();
// while (iterator.hasNext()) {
// ViewField element = (ViewField) iterator.next();
// temp.add(element.getDisplayName());
// }
// list.add(temp);
// return list;
// }

    private static String getListItemsFromWss(String listSoapAddress, WssSingleList wssList, String username, String password, String viewName) throws Exception, RemoteException, TransformerException {
        ListsSoapStub stub = returnStub(listSoapAddress, username, password);
        MessageElement msg = createViewFieldsMessage(wssList.getDefaultView().getViewField());
        GetListItemsViewFields fields = new GetListItemsViewFields();
        fields.set_any(new MessageElement[]{msg});

        GetListItemsResponseGetListItemsResult res = stub.getListItems(wssList.getName(), "", createQueryElement(), fields, "",
                createEmptyQueryOptions());

        String xml = getXmlFragment(res.get_any());
        return xml;
    }

    private static String getListItemsFromWss(String listSoapAddress, WssSingleList wssList, String username, String password, String viewName, Properties proxyProperties) throws Exception, RemoteException, TransformerException {
        ListsSoapStub stub = returnStub(listSoapAddress, username, password, proxyProperties);
        MessageElement msg = createViewFieldsMessage(wssList.getDefaultView().getViewField());
        GetListItemsViewFields fields = new GetListItemsViewFields();
        fields.set_any(new MessageElement[]{msg});

        GetListItemsResponseGetListItemsResult res = stub.getListItems(wssList.getName(), "", createQueryElement(), fields, "",
                createEmptyQueryOptions());

        String xml = getXmlFragment(res.get_any());
        return xml;
    }

    private static MessageElement createViewFieldsMessage(List<ViewField> viewField) throws Exception {

        MessageElement msg = new MessageElement(new PrefixedQName(new QName("ViewFields")));

        Iterator iterator = viewField.iterator();
        while (iterator.hasNext()) {
            ViewField element = (ViewField) iterator.next();

            SOAPElement fieldRef = msg.addChildElement("FieldRef");
            PrefixedQName name = new PrefixedQName(new QName("Name"));
            fieldRef.addAttribute(name, element.getName());
        }
// System.out.println("ViewFields XML = " + msg.getAsString());
        return msg;

    }

    private static SOAPElement createViewFields(List list) throws SOAPException, TransformerException {

        SOAPFactory soapFactory = SOAPFactory.newInstance();
        Name name = soapFactory.createName("ViewFields");
        SOAPElement soapElement = soapFactory.createElement(name);
        Iterator iterator = list.iterator();
        Name fieldName = soapFactory.createName("FieldRef");
        SOAPElement fieldRef = soapFactory.createElement(fieldName);
        while (iterator.hasNext()) {
            ViewField field = (ViewField) iterator.next();
            Name qname = soapFactory.createName("Name");
            fieldRef.addAttribute(qname, field.getName());
            soapElement.addChildElement(fieldRef);
            // System.out.println(qname.getLocalName()+ element.getValue());
        }
        // System.out.print("ViewFields XML=");
        XMLUtils.getAsXml((Node) soapElement, new PrintWriter(System.out));
        // System.out.println("");
        // System.out.println("ViewFields XML = " +
        // soapElement.getAttribute("FieldRef"));
        return soapElement;

    }

    private static String getListCollectionFromWss(String listSoapAddress, String username, String password) throws Exception, TransformerException, ServiceException {

        ListsSoapStub stub = returnStub(listSoapAddress, username, password);
        GetListCollectionResponseGetListCollectionResult res = stub.getListCollection();
        return getXmlFragment(res.get_any());


    }

    private static String getListCollectionFromWss(String listSoapAddress, String username, String password, Properties proxyProperties) throws Exception, TransformerException, ServiceException {

        ListsSoapStub stub = returnStub(listSoapAddress, username, password, proxyProperties);
        GetListCollectionResponseGetListCollectionResult res = stub.getListCollection();
        return getXmlFragment(res.get_any());


    }

    private static SOAPElement createSOAPElement(String name) throws SOAPException {
        SOAPFactory soapFactory = SOAPFactory.newInstance();
        Name soapName = soapFactory.createName(name);
        SOAPElement soapElement = soapFactory.createElement(soapName);
        return soapElement;
    }


    private static SOAPElement createQueryOptions() throws SOAPException {
        SOAPFactory soapFactory = SOAPFactory.newInstance();
        Name soapName = soapFactory.createName("queryOptions", "SOAPSDK9", "http://schemas.microsoft.com/sharepoint/soap");
        SOAPElement soapElement = soapFactory.createElement(soapName);
        soapElement.addChildElement("QueryOptions");
        return soapElement;

    }

    private static ListsSoapStub returnStub(String listSoapAddress, String userName, String password) throws ServiceException {

        ListsLocator service = new ListsLocator();
        service.setEndpointAddress(LISTS_SOAP_PORT_NAME, listSoapAddress);
        ListsSoapStub stub = (ListsSoapStub) service.getListsSoap();
        stub.setUsername(userName);
        stub.setPassword(password);

        return stub;

    }

    private static ListsSoapStub returnStub(String listSoapAddress, String userName, String password, Properties props) throws ServiceException {

        ListsLocator service = new ListsLocator();
        service.setEndpointAddress(LISTS_SOAP_PORT_NAME, listSoapAddress);
        ListsSoapStub stub = (ListsSoapStub) service.getListsSoap();
        stub.setUsername(userName);
        stub.setPassword(password);
        stub._setProperty("http.proxyHost", props.getProperty(Constants.PROXY_HOST));
        stub._setProperty("http.proxyPort", props.getProperty(Constants.PROXY_PORT));
        stub._setProperty("http.proxyUser", props.getProperty(Constants.PROXY_USER));
        stub._setProperty("http.proxyPassword", props.getProperty(Constants.PROXY_PASSWORD));

        return stub;

    }

    public static String getListBody(String listSoapAddress, String username, String password, String listName, String id, String xsltFilePath) throws Exception, IOException, ParserConfigurationException, SAXException, SOAPException, JAXBException, ServiceException {
        String junkString = "ows_";
        String viewbody = WSSViewsHelper.getBodyViewOfList(listSoapAddress, username, password, listName);
        String listAndViewXml = getListAndView(listSoapAddress, username, password, listName, viewbody);
        String trXml = XMLUtils.transformViaXslt(listAndViewXml, xsltFilePath);
        WssSingleList wssList = (WssSingleList) unmarshallFromXml("com.sun.portal.sharepoint.schemas.list.single", trXml);

        String xml = getListItemsFromWss(listSoapAddress, wssList, username, password, viewbody);
        Document doc = XMLUtils.parseAndReturnRootNode(new InputSource(new StringReader(xml)));
        NodeList nodeList = doc.getElementsByTagNameNS("*", "row"); // List
                                                                        // of
                                                                        // all
                                                                        // rows
                                                                        // returned
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i); // i-th Row that was returned.
            NamedNodeMap nodemap = n.getAttributes(); // Get all attributes
                                                        // from this node
            String listid = nodemap.getNamedItem(junkString + "ID").getNodeValue();
            if (listid.equals(id)) {

                if (nodemap.getNamedItem(junkString + "Body") != null)
                    return nodemap.getNamedItem(junkString + "Body").getNodeValue();
                else return Constants.NO_BODY;


            }
        }
        return Constants.NO_BODY;
    }

    private static GetListItemsQuery createQueryElement() {
        GetListItemsQuery query = new GetListItemsQuery();
        query.set_any(new MessageElement[]{new MessageElement(new QName("Query"))});
        return query;
    }


    private static void debug(String methodName, String msg) {
        debug("WSSListsHelper", methodName, msg);
    }

    private static GetListItemsQueryOptions createEmptyQueryOptions() {
        GetListItemsQueryOptions options = new GetListItemsQueryOptions();
        MessageElement me = new MessageElement("queryOptions", "SOAPSDK9", "http://schemas.microsoft.com/sharepoint/soap/");

        options.set_any(new MessageElement[]{new MessageElement(new QName("QueryOptions"))});

        return options;
    }


    public static String getListBody(String listSoapAddress, String username, String password, String listName, String id, String xsltFilePath, Properties proxyProperties) throws Exception {
        String junkString = "ows_";
        String viewbody = WSSViewsHelper.getBodyViewOfList(listSoapAddress, username, password, listName);
        String listAndViewXml = getListAndView(listSoapAddress, username, password, listName, viewbody, proxyProperties);
        String trXml = XMLUtils.transformViaXslt(listAndViewXml, xsltFilePath);
        WssSingleList wssList = (WssSingleList) unmarshallFromXml("com.sun.portal.sharepoint.schemas.list.single", trXml);

        String xml = getListItemsFromWss(listSoapAddress, wssList, username, password, viewbody);
        Document doc = XMLUtils.parseAndReturnRootNode(new InputSource(new StringReader(xml)));
        NodeList nodeList = doc.getElementsByTagNameNS("*", "row"); // List
                                                                        // of
                                                                        // all
                                                                        // rows
                                                                        // returned
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i); // i-th Row that was returned.
            NamedNodeMap nodemap = n.getAttributes(); // Get all attributes
                                                        // from this node
            String listid = nodemap.getNamedItem(junkString + "ID").getNodeValue();
            if (listid.equals(id)) {
                if (nodemap.getNamedItem(junkString + "Body").getNodeValue() != null)
                    return nodemap.getNamedItem(junkString + "Body").getNodeValue();
                else return Constants.NO_BODY;

            }
        }
        return Constants.NO_BODY;
    }


}
