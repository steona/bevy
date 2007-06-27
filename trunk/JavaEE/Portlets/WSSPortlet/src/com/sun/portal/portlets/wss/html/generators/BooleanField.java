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
package com.sun.portal.portlets.wss.html.generators;

/**
 * Created by IntelliJ IDEA.
 * User: Murali
 * Date: Jun 20, 2007
 * Time: 3:21:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class BooleanField implements FieldGenerator {
    public String getHtmlOfTheField(String DisplayName) {
        String element = "<input type=\"radio\" name=\"" + DisplayName + "\" value=\"TRUE\"/>" + "<input type=\"radio\" name=\"" + DisplayName + "\" value=\"FALSE\"/>";
        return element;
    }
}

