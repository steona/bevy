<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<portlet:defineObjects/>

<portlet:actionURL var="portletUrl"/>

<%
    String cssPath = renderResponse.encodeURL(renderRequest.getContextPath() + "/css/wssListDisplay.css");
%>

<link rel="stylesheet" type="text/css" href="<%=cssPath%>">

<c:import url="${requestScope.WSS_PORTLET_XSL_URL}" var="xsl"/>

<x:transform
        xml="${requestScope.WSS_PORTLET_XML}"
        xslt="${xsl}">
    <x:param name="static_link_part" value="<%=portletUrl%>"/>
    <x:param name="list_name" value="${requestScope.LIST_NAME}"/>
</x:transform>
 
