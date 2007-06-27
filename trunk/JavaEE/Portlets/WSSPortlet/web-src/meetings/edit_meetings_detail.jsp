<%@ page
        import="javax.portlet.PortletPreferences" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<portlet:defineObjects/>

<portlet:actionURL var="portletUrl"/>

<%
    PortletPreferences prefs = renderRequest.getPreferences();
%>
<html>
<head>
    <title>WSS Site - All Meetings</title>
    <style type="text/css">
        body {
            font-family: Verdana, Arial, Helvetica,
            sans-serif;
            font-size: 10px;
        }

        td {
            font-family:
            Verdana, Arial, Helvetica, sans-serif;
            font-size:
            10px;
        }

        .tableHeader {
            font-family: Verdana, Arial,
            Helvetica, sans-serif;
            font-size: 10px;
            font-weight:
            bold;
            color: #660000;
        }

    </style>
</head>

<body>
<form id="form1" name="form1" method="post" action="<%=portletUrl%>">


    <table cellSpacing="5" cellPadding="5" width="100%" border="0">
        <caption>Select the Meetings from the WSS sites to display</caption>
        <tbody>
            <tr>
                <th scope="col">Select</th>
                <th scope="col">Title</th>

            </tr>


            <c:forEach items="${requestScope.WSS_MEETINGS_COLLECTION}" var="aList">


                <tr>

                    <td vAlign="middle" align="center"><input type="radio"
                                                              name="wss.site.meeting.name" value="${aList.url}"/></td>
                    <td vAlign="middle">${aList.title}</td>

                </tr>

            </c:forEach>
        </tbody>
    </table>
    <input type="submit" name="saveMeetingsSetting" value="Show this Meeting"/></form>
</body>
</html>
