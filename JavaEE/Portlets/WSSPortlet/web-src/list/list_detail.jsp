<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<portlet:defineObjects/>

<portlet:actionURL var="back" portletMode="view">
    <portlet:param name="body" value="back"/>
</portlet:actionURL>


<%
    String cssPath = renderResponse.encodeURL(renderRequest.getContextPath() + "/css/wssListDisplay.css");
%>

<link rel="stylesheet" type="text/css" href="<%=cssPath%>">


<c:set var="ListBody" value="${requestScope.LIST_BODY}"/>

<c:out value="${ListBody}" escapeXml="false"/>

<FORM><a href="<%=back.toString()%>">Back </a>


</FORM>