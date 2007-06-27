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
 * SharePointLogger.java
 *
 * Created on August 24, 2006, 4:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author root
 */
package com.sun.portal.portlets.wss.helpers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SharePointLogger {
    static Logger logger = null;

    /**
         * Creates a new instance of SharePointLogger
         */
    public SharePointLogger() {
    }

    static void initialize() {

	if (logger == null) {
	    System.out.println("Inside Initializer");
	    logger = Logger.getLogger("SharePointLogger", null);
	    logger.setLevel(Level.FINEST);
	    System.out.println("Initialized Logger");
	}
    }

    public static Logger getLogger() {
	initialize();
	return logger;
    }

}
