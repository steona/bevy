<%@ page
        import="javax.portlet.PortletPreferences,java.util.List" %>


<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<portlet:defineObjects/>

<portlet:actionURL var="AddItem" portletMode="view">
    <portlet:param name="additem" value="additem"/>
</portlet:actionURL>
<%
    PortletPreferences prefs = renderRequest.getPreferences();
    String stringNumberOfRows = prefs.getValue("numberOfRows", "10");
    int numberOfRows = new Integer(stringNumberOfRows);
    pageContext.setAttribute("numberOfRows", numberOfRows);

%>
<script type="text/javascript">
    function changeHidden(div_id, div_id1, state)
    {
        if (state == 'preferred')
        {
            document.getElementById(div_id1).style.display = 'none';
            document.getElementById(div_id).style.display = 'block';
        }
        else
        {
            document.getElementById(div_id).style.display = 'none';
            document.getElementById(div_id1).style.display = 'block';
        }
    }
</script>
<%
    String cssPath = renderResponse.encodeURL(renderRequest.getContextPath() + "/css/wssListDisplay.css");
%>

<link rel="stylesheet" type="text/css" href="<%=cssPath%>">

<c:set var="wssListViewData" value="${requestScope.WSS_SINGLE_LIST}"/>
<c:set var="viewList" value="${requestScope.WSS_VIEWS_LIST}"/>

<%
    List viewList = (List) request.getAttribute("WSS_VIEWS_LIST");
    Long millis = System.currentTimeMillis();
    String millisecs = millis.toString();
    if (viewList != null && viewList.size() > 1)

    {

%>

<form name="Radio">
<div class="showRows" align="right">
    <font color="#ff9999;" size="-2">
        Preferred:<input type="radio" name="checkhidden" value="preferred" checked="checked"
                         onclick="changeHidden('visible<%=millisecs%>','hidden<%=millisecs%>','preferred')"/>
        All :<input type="radio" name="checkhidden" value="All"
                    onclick="changeHidden('visible<%=millisecs%>','hidden<%=millisecs%>','All')"/>
    </font>
</div>
<div align="center">
    <font color="#0033cc" size="2">
        <a href="<%=AddItem.toString()%>">AddItem</a>
    </font>
</div>

<div id="visible<%=millisecs%>">
    <table border="0">
        <tr>
            <td valign="top">
                <table border="0">

                    <c:forEach items="${viewList}" var="lists" varStatus="status">
                        <tr>
                            <td>
                                <c:out value="${lists}" escapeXml="false"/>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </td>
            <td>
                <table border="0" cellpadding="5" cellspacing="3">
                    <c:forEach items="${wssListViewData}" var="aRow" varStatus="status">
                        <c:choose>
                            <c:when test="${status.first}">
                                <tr>
                                    <c:forEach items="${aRow}" var="aColumn">
                                        <td><strong>
                                            <c:out value="${aColumn}" escapeXml="true"/>
                                        </strong></td>
                                    </c:forEach>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${status.count <= numberOfRows+1 }">
                                        <tr>
                                            <c:forEach items="${aRow}" var="aColumn">
                                                <td>
                                                    <c:out value="${aColumn}" escapeXml="false"/>
                                                </td>
                                            </c:forEach>
                                        </tr>
                                    </c:when>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>
                </table>
            </td>
        </tr>

    </table>
</div>

<div id="hidden<%=millisecs%>" style="display:none;">
    <table border="0">
        <tr>
            <td valign="top">
                <table border="0">

                    <c:forEach items="${viewList}" var="lists" varStatus="status">
                        <tr>
                            <td>
                                <c:out value="${lists}" escapeXml="false"/>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </td>
            <td>
                <table border="0" cellpadding="5" cellspacing="3">


                    <c:forEach items="${wssListViewData}" var="aRow" varStatus="status">
                        <c:choose>
                            <c:when test="${status.first}">
                                <tr>
                                    <c:forEach items="${aRow}" var="aColumn">
                                        <td><strong>
                                            <c:out value="${aColumn}" escapeXml="true"/>
                                        </strong></td>
                                    </c:forEach>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <c:forEach items="${aRow}" var="aColumn">
                                        <td>
                                            <c:out value="${aColumn}" escapeXml="false"/>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>
</div>
</form>


<%

} else {

%>
<form name="Radio">
    <div class="showRows" align="right">
        <font color="#ff9999;" size="-2">
            Preferred:<input type="radio" name="checkhidden" checked="checked" value="preferred"
                             onclick="changeHidden('visible<%=millisecs%>','hidden<%=millisecs%>','preferred')"/>
            All :<input type="radio" name="checkhidden" value="All"
                        onclick="changeHidden('visible<%=millisecs%>','hidden<%=millisecs%>','All')"/>
        </font>
    </div>

    <div align="center">
        <font color="#0033cc" size="2">
            <a href="<%=AddItem.toString()%>">AddItem</a>
        </font>
    </div>

    <div id="visible<%=millisecs%>">
        <table border="0" cellpadding="5" cellspacing="3">
            <c:forEach items="${wssListViewData}" var="aRow" varStatus="status">
                <c:choose>
                    <c:when test="${status.first}">
                        <tr>
                            <c:forEach items="${aRow}" var="aColumn">
                                <td><strong>
                                    <c:out value="${aColumn}" escapeXml="true"/>
                                </strong></td>
                            </c:forEach>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${status.count <= numberOfRows+1 }">
                                <tr>
                                    <c:forEach items="${aRow}" var="aColumn">
                                        <td>
                                            <c:out value="${aColumn}" escapeXml="false"/>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:when>
                        </c:choose>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
        </table>

    </div>

    <div id="hidden<%=millisecs%>" style="display:none;">
        <table border="0" cellpadding="5" cellspacing="3">


            <c:forEach items="${wssListViewData}" var="aRow" varStatus="status">
                <c:choose>
                    <c:when test="${status.first}">
                        <tr>
                            <c:forEach items="${aRow}" var="aColumn">
                                <td><strong>
                                    <c:out value="${aColumn}" escapeXml="true"/>
                                </strong></td>
                            </c:forEach>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <c:forEach items="${aRow}" var="aColumn">
                                <td>
                                    <c:out value="${aColumn}" escapeXml="false"/>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
        </table>
    </div>
</form>

<% } %>

