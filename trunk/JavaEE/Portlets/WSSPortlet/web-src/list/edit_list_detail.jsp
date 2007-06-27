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
    <title>WSS Site - All Lists</title>
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
    <script type="text/javascript">

        function isInteger(s)
        {
            var i;
            for (i = 0; i < s.length; i++)
            {
                // Check that current character is number.
                var c = s.charAt(i);
                if (((c < "0") || (c > "9"))) return false;
            }
            // All characters are numbers.
            return true;
        }

        function checkForValidNumber() {
            var rows = document.getElementById('numberOfRows').value;
            if ((rows == null) || rows == "" || !isInteger(rows)) {
                alert('Please enter a valid number');
                return false;
            } else {
                document.getElementById('hiddenvar').value = "Show this list";
                document.getElementById('form1').submit();

            }

        }


    </script>
</head>

<body>
<form id="form1" name="form1" method="post" action="<%=portletUrl%>">


    <table cellSpacing="5" cellPadding="5" width="100%" border="0">
        <caption>Select the list from the WSS site to display</caption>
        <tbody>
            <tr>
                <th scope="col">Select</th>
                <th scope="col">List Title</th>
                <th scope="col">Item Count</th>
                <th scope="col">Last Updated</th>
            </tr>


            <c:forEach items="${requestScope.WSS_LIST_COLLECTION}" var="aList">

                <!--c:out value="${aList.description}"/-->
                <tr>

                    <td vAlign="middle" align="center"><input type="radio"
                                                              name="wss.site.list.name"
                                                              value="${aList.ID},${aList.title}"/></td>
                    <td vAlign="middle">${aList.title}</td>
                    <td vAlign="middle" align="center">${aList.itemCount}</td>
                    <td>${aList.modified}</td>
                </tr>

            </c:forEach>
        </tbody>
    </table>
    <br>&nbsp <br>
    Default Number Of Rows:<input type="text" id="numberOfRows" name="numberOfRows" value="10" size="5"/> <br> &nbsp<br>
    <input type="button" name="button" value="ShowList" onclick="checkForValidNumber();return false;"/>
    <input type="hidden" id="hiddenvar" name="saveListSetting" value=""></form>
</body>
</html>
