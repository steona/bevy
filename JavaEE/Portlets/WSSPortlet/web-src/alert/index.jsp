<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<style type="text/css">
    <!--
    body {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: 10px;
    }

    -->
</style>


<portlet:defineObjects/>

<portlet:actionURL var="portletUrl"/>

<ul>
    <c:forEach var="alert" items="${requestScope.WSS_ALERTS_ARRAY}">
        <li><b>
            <c:out value="${alert.title}" escapeXml="true"/>
        </b>
            <ul>
                <li>
                    <c:out value="${alert.id}" escapeXml="true"/>
                </li>
                <li>
                    <c:out value="${alert.eventType}" escapeXml="true"/>
                </li>
                <li>
                    <c:out value="${alert.active}" escapeXml="true"/>
                </li>
            </ul>
        </li>
    </c:forEach>
</ul>
