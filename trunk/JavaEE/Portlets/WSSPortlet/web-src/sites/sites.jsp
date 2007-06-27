<%@ page
        import="javax.portlet.PortletPreferences,java.util.List,com.microsoft.schemas.sharepoint.soap.sites.Template" %>
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
<form action="#">
    <table>
        <tr>
            <td>
                <b>
                    Site Title
                </b>
            </td>
            <td>
                <b>
                    Site Name
                </b>
            </td>
            <td>
                <b>
                    Site Description
                </b>
            </td>
        </tr>

        <%
            Template templates[] = (Template[]) request.getAttribute("WSS_TEMPLATES_LIST");

            for (int i = 0; i < templates.length; i++) {


        %>
        <tr>
            <td>
                <%=templates[i].getTitle()%>
            </td>
            <td>
                <%=templates[i].getName()%>
            </td>
            <td>
                <%=templates[i].getDescription()%>
            </td>

        </tr>


        <%
            }
        %>
    </table>
</form>