<%@ page import="javax.portlet.PortletPreferences" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<portlet:defineObjects/>

<%PortletPreferences prefs = renderRequest.getPreferences();%>

<portlet:actionURL var="portletUrl"/>

<script type="text/javascript">
    function changeproxystatus() {

        if (document.getElementById("proxysetting").checked) {
            //             alert('it is checked');
            document.getElementById("proxy").style.display = 'block';

        } else {
            //            alert('it is not checked');
            document.getElementById("proxy").style.display = 'none';
        }
    }


</script>

<form id="form1" name="form1" method="post"
      action="<%=portletUrl.toString()%>">
    <table width="100%" border="0" cellspacing="5" cellpadding="5">
        <tr>
            <td>WSS Site URL :</td>
            <td><input type="text" name="wss.site.url" value="<%=prefs.getValue("wss.site.url","")%>"/></td>
        </tr>
        <tr>
            <td>WSS Site Username :</td>
            <td><input type="text" name="wss.site.username" value="<%=prefs.getValue("wss.site.username","")%>"/></td>
        </tr>
        <tr>
            <td>WSS Site Password :</td>
            <td><input type="password" name="wss.site.password" value="<%=prefs.getValue("wss.site.password","")%>"/>
            </td>
        </tr>

    </table>
    <%--<div>--%>
    <%--setProxy:<input id="proxysetting" name="proxysetting" type="checkbox" onclick="changeproxystatus()"/>--%>
    <%--</div>--%>
    <%--<div id="proxy" style="display:none;">--%>
    <%--<table width="100%" border="0" cellspacing="5" cellpadding="5">--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--ProxyHost:--%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<input type="text" name="proxyhost" value="http://"/>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--ProxyPort:--%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<input type="text" name="proxyport" value=""/>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--ProxyUserName:--%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<input type="text" name="proxyuser" value="">--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--ProxyPassword:--%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<input type="text" name="proxypassword" value="">--%>
    <%--</td>--%>
    <%--</tr>--%>

    <%--</table>--%>
    <%--</div>--%>

    <input type="hidden" name="typeOfSubmit"/>
    <input type="submit" name="saveSite" value="Store Site Properties"
           onclick="document.form1.typeOfSubmit.value = this.name"/>
    <input type="submit" name="Cancel" value="Cancel" onclick="document.form1.typeOfSubmit.value = this.name"/>
</form>







