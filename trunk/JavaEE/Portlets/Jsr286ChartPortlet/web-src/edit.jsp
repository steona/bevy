<%--
Copyright 2004 The Apache Software Foundation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Developed by : Sandeep Soni [ http://sonisandeep.blogspot.com, 
    			      Email : Sandeep.Soni at yahoo.com 
    			    ]
--%>

<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ page import="javax.portlet.*"%>
<%@ page import="com.sun.portal.os.portlets.Constants"%>
<portlet:defineObjects />
<% PortletURL url = renderResponse.createActionURL(); 
   PortletPreferences prefs = renderRequest.getPreferences();
   String chartType = prefs.getValue(Constants.PREF_CHART_TYPE,"pie");
   
   String barSql = prefs.getValue(Constants.PREF_BAR_CHART_SQL,"Enter 2 column SQL");
   String pieSql = prefs.getValue(Constants.PREF_PIE_CHART_SQL,"Enter 1 column SQL");
   String timeSeriesSql = prefs.getValue(Constants.PREF_TIME_SERIES_SQL,"Enter 2 column SQL"); 
%>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}
//-->
</script>

<form action="<%=url.toString()%>" name="chartTypeSelect" method=post>
  <table width="100%"  border="0" cellspacing="5">
    <tr>
      <th width="6%" scope="col">SELECT</th>
      <th width="15%" scope="col">CHART TYPE </th>
      <th width="79%" scope="col">SQL</th>
    </tr>
    <tr>
      <td><input name="<%=Constants.PREF_CHART_TYPE%>" type="radio" onClick="MM_showHideLayers('pieLayer','','show','barLayer','','hide','timeLayer','','hide');MM_showHideLayers('pieLayer','','show','barLayer','','hide','timeLayer','','hide')" value="pie" 
      <%=chartType.equals("pie") ? "checked" : ""%> ></td>
      <td>Pie Chart</td>
      <td rowspan="3"><div id="pieLayer" style="position:relative; width:787px; height:71px; z-index:1; visibility: hidden;">
          <input type="text" name="<%=Constants.PREF_PIE_CHART_SQL%>" value="<%=pieSql%>" size="100">
        </div>
        <div id="barLayer" style="position:relative; width:787px; height:71px; z-index:2; visibility: hidden; left: 0; top: 0;">
          <input type="text" name="<%=Constants.PREF_BAR_CHART_SQL %>" value="<%=barSql%>" size="100">
        </div>
        <div id="timeLayer" style="position:relative; width:787px; height:71px; z-index:3; visibility: hidden;left: 0; top: 0;">
          <input type="text" name="<%=Constants.PREF_TIME_SERIES_SQL%>" value="<%=timeSeriesSql%>" size="100">
        </div>
      </td>
    </tr>
    <tr>
      <td><input name="<%=Constants.PREF_CHART_TYPE%>" type="radio" onClick="MM_showHideLayers('barLayer','','show');MM_showHideLayers('pieLayer','','hide','barLayer','','show','timeLayer','','hide')" value="bar"
      <%=chartType.equals("bar") ? "checked" : ""%> ></td>
      <td>Bar Chart</td>
    </tr>
    <tr>
      <td><input name="<%=Constants.PREF_CHART_TYPE%>" type="radio" onClick="MM_showHideLayers('pieLayer','','hide','barLayer','','hide','timeLayer','','show');MM_showHideLayers('pieLayer','','hide','barLayer','','hide','timeLayer','','show')" value="time"
      <%=chartType.equals("time") ? "checked" : ""%> ></td>
      <td>Time Series Chart</td>
    </tr>
  </table>
  <input type="submit" name="saveChartConfig" value="Generate Chart">
</form>
