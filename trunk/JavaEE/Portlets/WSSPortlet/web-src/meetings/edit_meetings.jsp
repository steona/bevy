<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<portlet:defineObjects/>
<%
    String site = (String) renderRequest.getAttribute("site");
    if (site != null && site.equals("invalid")) {
%>
<h3>Please enter a valid site credentials in Edit Site </h3>
<%
    }

%>

<portlet:actionURL var="portletEditSiteUrl" portletMode="edit">
    <portlet:param name="edit" value="site"/>
</portlet:actionURL>

<portlet:actionURL var="portletEditDisplayedMeetings" portletMode="edit">
    <portlet:param name="edit" value="meetings"/>
</portlet:actionURL>


<a href="<%=portletEditSiteUrl.toString()%>">Edit Site</a><br/>
<a href="<%=portletEditDisplayedMeetings.toString()%>">List Meetings</a><br/>






