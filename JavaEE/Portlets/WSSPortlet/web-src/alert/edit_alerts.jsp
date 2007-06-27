<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<portlet:defineObjects/>

<portlet:actionURL var="portletEditSiteUrl" portletMode="edit">
    <portlet:param name="edit" value="site"/>
</portlet:actionURL>


<a href="<%=portletEditSiteUrl.toString()%>">Edit Site</a><br/>

