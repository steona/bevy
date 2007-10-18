<%-- 
~ Licensed to the Apache Software Foundation (ASF) under one
 ~ or more contributor license agreements.  See the NOTICE file
 ~ distributed with this work for additional information
 ~ regarding copyright ownership.  The ASF licenses this file
 ~ to you under the Apache License, Version 2.0 (the
 ~ "License"); you may not use this file except in compliance
 ~ with the License.  You may obtain a copy of the License at
 ~
 ~    http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing,
 ~ software distributed under the License is distributed on an
 ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 ~ KIND, either express or implied.  See the License for the
 ~ specific language governing permissions and limitations
 ~ under the License.
 ~
 ~
 ~ Author Of This Software : Sandeep Soni [ Sandeep.Soni at gmail dot com ]
 ~ Do Let Me Know by Email if you used the software and found it useful.
 --%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ page import="javax.portlet.*"%>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<portlet:defineObjects />

<style type="text/css">
<!--
#<portlet:namespace/>wrap {
	position:relative;
	left:0;
	width:100%;
	height:500px;

}
#<portlet:namespace/>newFeedLayer {
	display: none;
}

#<portlet:namespace/>wrap th{
	background-color: #FF9900;

}

#<portlet:namespace/>wrap .table_heading {
	font-size: 14px;
	font-weight: bold;
	color: #000066;
}
-->
</style>


<script type="text/javascript">
<!--
function <portlet:namespace/>_show() {
	var newFeedLayer = document.getElementById("<portlet:namespace/>newFeedLayer");
	newFeedLayer.style.display = 'block';
}

function <portlet:namespace/>_hide() {
	var newFeedLayer = document.getElementById("<portlet:namespace/>newFeedLayer");
	newFeedLayer.style.display = 'none';
}

function <portlet:namespace/>_addAndHide() {
	var addFeedform = document.<portlet:namespace/>_addFeedForm ;

	var feedsTable = document.getElementById("<portlet:namespace/>_feedsTable");
    var row=feedsTable.insertRow(-1);
    
    var action = row.insertCell(0);
    var fName=row.insertCell(1);
    var fUrl=row.insertCell(2);
    
    action.innerHTML = "action here";
    fName.innerHTML=addFeedform.feedName.value;
    fUrl.innerHTML=addFeedform.feedUrl.value;
	
	var newFeedLayer = document.getElementById("<portlet:namespace/>newFeedLayer");
	newFeedLayer.style.display = 'none';
}

function isHttpUrl(s) {
	var regexp = /^(http|https):\/\//
	alert(regexp.test(s));
	//return regexp.test(s);
	return true;
}


//-->
</script>

<div id="<portlet:namespace/>wrap">

  <p><a href="javascript:<portlet:namespace/>_show()">Add New Feed </a></p>
  <div id="<portlet:namespace/>newFeedLayer">
      <form id="<portlet:namespace/>_addFeedForm" name="<portlet:namespace/>_addFeedForm" method="post" action="<portlet:actionURL/>">
	  <dl>
	  	<dt>Feed Name</dt>
		<dd>
		  <input name="feedName" type="text" id="feedName" size="50"/>
		</dd>
	  	<dt>Feed URL</dt>
		<dd>
		  <input name="feedUrl" type="text" id="<portlet:namespace/>_feedUrl" size="50"/>
		</dd>
		<dt>&nbsp;</dt>
		<!--  <dt><a href="javascript:<portlet:namespace/>_addAndHide()">Add<a>&nbsp;&nbsp;&nbsp;<a href="javascript:<portlet:namespace/>_hide()">Cancel<a></dt>-->
		<dt><input type="submit" name="add_feed" value="Add">&nbsp;&nbsp;&nbsp;<input type="submit" name="cancel" value="Cancel"></dt>		
	  </dl>		
  </form>
  </div>
	
	<p class="table_heading">Configured Feeds</p>
    
      <table id="<portlet:namespace/>_feedsTable" width="100%" border="0" cellspacing="2" cellpadding="2">
        <tr>
          <th scope="col">Action</th>
          <th scope="col">Feed Name </th>
          <th scope="col">Feed URL </th>
          <th scope="col">Feed Type </th>
        </tr>
        <c:forEach items="${requestScope.feed_list_map}" var="feed" varStatus="status">
         <tr>
          	<td>
          		<portlet:actionURL var="deleteFeedUrl">
          			<portlet:param name="delete_feed" value="${feed.key}"/>
          		</portlet:actionURL>
          		<a href="<%=deleteFeedUrl.toString()%>">Delete</a>
          	</td>
          <c:forEach items="${feed.value}" var="value">
                <td>${value}</td>
          </c:forEach>
          </tr>
        </c:forEach>

      </table>

  <p>&nbsp;    </p>
</div>  <!--  End wrap  -->
