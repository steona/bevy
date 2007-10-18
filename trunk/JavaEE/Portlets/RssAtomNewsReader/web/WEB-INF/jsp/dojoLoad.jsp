<%-- 
~ Licensed to the Apache Software Foundation (ASF) under one
 ~ or more contributor license agreements.  See the NOTICE file
 ~ distributed with this work for additional information
 ~ regarding copyright ownership.  The ASF licenses this file
 ~ to you under the Apache License, Version 2.0 (the
 ~ "License"); you may not use this file except in compliance
 ~ with the License.  You may obtain a copy of the License at
 ~
 ~    http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing,
 ~ software distributed under the License is distributed on an
 ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 ~ KIND, either express or implied.  See the License for the
 ~ specific language governing permissions and limitations
 ~ under the License.
 ~
 ~
 ~ Author Of This Software : Sandeep Soni [ Sandeep.Soni at gmail dot com ]
 ~ Do Let Me Know by Email if you used the software and found it useful.
 --%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="javax.portlet.*"%>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<portlet:defineObjects />

<div id="<portlet:namespace/>_dojo_scripts">
	<script type="text/javascript">
	
	<%
	    ResourceURL jsResourceURL = renderResponse.createResourceURL();
	    jsResourceURL.setResourceID("dojojs");
	%>
	
	/* Load Dojo library, if it hasn't already */
	if (typeof dojo == "undefined") {
	    /* build script tag */
	    var script = document.createElement("script");
	    script.src = "<%=jsResourceURL.toString() %>";
	    script.type= "text/javascript";
	    /* dynamically insert with other scripts */
	    var <portlet:namespace/>_scripts = document.getElementById("<portlet:namespace/>_dojo_scripts");
	    <portlet:namespace/>_scripts.appendChild(script);
	}
	</script>
</div>