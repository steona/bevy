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
package com.sun.portal.portlets.wss.utils;

import javax.portlet.PortletURL;

/**
 * Created by IntelliJ IDEA.
 * User: Murali
 * Date: May 30, 2007
 * Time: 6:56:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class UrlWrapper {

    PortletURL url = null;
    String urlstring = null;

    public UrlWrapper(PortletURL url) {
        this.url = url;
    }

    public UrlWrapper(String url) {
        this.urlstring = url;
    }

    public void setParameter(String key, String value) {
        if (url != null) {
            url.setParameter(key, value);
        } else {
            StringBuffer buffer = new StringBuffer(urlstring);
            buffer.append("?").append(key).append("=").append(value);
            this.urlstring = buffer.toString();
        }
    }

    public String toString() {
        if (url != null) {
            return url.toString();
        } else {
            return urlstring;
        }
    }

}
