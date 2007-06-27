<%@ page import="java.util.List" %>
<%@ page import="com.sun.portal.sharepoint.schemas.sites.Web" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<portlet:defineObjects/>

<c:set var="wssWebSitesData" value="${requestScope.WSS_WEB_SITES_LIST}"/>

<%
    String cssPath = renderResponse.encodeURL(renderRequest.getContextPath() + "/css/wssListDisplay.css");
%>

<link rel="stylesheet" type="text/css" href="<%=cssPath%>">
<form action="#">

    <%--<table border="0" cellpadding="5" cellspacing="3">--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<b>--%>
    <%--SiteTitle--%>
    <%--</b>--%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<b>--%>
    <%--SiteUrl--%>
    <%--</b>--%>
    <%--</td>--%>
    <%--</tr>--%>

    <%--<%--%>
    <%--for(int i=0;i<list.size();i++){--%>
    <%--Web web = (Web)list.get(i);--%>

    <%--%>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<a href="<%=web.getUrl()%>"><%=web.getTitle()%> </a> --%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<%=web.getUrl()%>--%>
    <%--</td>--%>
    <%--</tr>   --%>

    <%--<%--%>
    <%--}--%>
    <%--%>--%>
    <%--</table>--%>


    <table border="0" cellpadding="5" cellspacing="3">
        <tr>
            <td>
                SiteTitle
            </td>
            <td>
                SiteUrl
            </td>
        </tr>
        <c:forEach items="${wssWebSitesData}" var="aRow" varStatus="status">

            <tr>
                <c:forEach items="${aRow}" var="aColumn">
                    <td>
                        <c:out value="${aColumn}" escapeXml="false"/>
                    </td>
                </c:forEach>
            </tr>


        </c:forEach>
    </table>

</form>