<%--
Copyright 2004 The Apache Software Foundation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Developed by : Sandeep Soni [ http://sonisandeep.blogspot.com, 
    			      Email : Sandeep.Soni at yahoo.com 
    			    ]
--%>


<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ page import="javax.portlet.*"%>
<portlet:defineObjects />

<%
	//String context = config.getServletContext().getServletContextName();
	//pConfig.getPortletContext().getRealPath("/no-chart.png")
	ResourceURL chartUrl = renderResponse.createResourceURL();
	chartUrl.setResourceID("chart-image");
%>
<img src="<%=chartUrl.toString()%>"/>
