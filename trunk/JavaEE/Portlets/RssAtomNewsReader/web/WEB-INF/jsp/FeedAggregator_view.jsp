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

<jsp:include page="/WEB-INF/jsp/dojoLoad.jsp" />


<%
    
    PortletPreferences prefs = renderRequest.getPreferences();
	ResourceURL allFeedsXmlUrl = renderResponse.createResourceURL();
	allFeedsXmlUrl.setResourceID("get_all_feeds");
	
	ResourceURL xpathJsUrl = renderResponse.createResourceURL();
	xpathJsUrl.setResourceID("js");
	xpathJsUrl.setParameter("path" , "/WEB-INF/js/xpath.js");
	
	ResourceURL spryDataUrl = renderResponse.createResourceURL();
	spryDataUrl.setResourceID("js");
	spryDataUrl.setParameter("path" , "/WEB-INF/js/SpryData.js");
	
	String screenCss = renderResponse.encodeURL(renderRequest.getContextPath() + "/css/screen.css");
%> 


<link href="<%=screenCss%>" rel="stylesheet" type="text/css" media="all" />



<script src="<%=xpathJsUrl.toString()%>" type="text/javascript"></script>
<script src="<%=spryDataUrl.toString()%>" type="text/javascript"></script>

<script type="text/javascript">
<!--
var feedList = new Spry.Data.XMLDataSet("<%=allFeedsXmlUrl.toString()%>", "feed-list/feed-entry", { sortOnLoad:"name",sortOrderOnLoad:"ascending"});

var titleList = new Spry.Data.XMLDataSet() ;


function <portlet:namespace/>_loadUrl( url , id , type ) {
	var fullUrl = url + "&feed_id=" + id;
	//alert(fullUrl);
	
	// Get the region which is going to display the title lists and tell it to 
	// go into the loading state and then make the dataset load the data.
	var rgn = Spry.Data.getRegion('<portlet:namespace/>allFeedTitles');
  	if (rgn)
	  rgn.setState('titleListLoading');
    
	titleList.setURL(fullUrl);
	//alert(id);
	//alert(type);
	
	if ( type.indexOf("atom") != -1 ) {
		titleList.setXPath("//entry"); // atom feed
	} else if (type.indexOf("rss") != -1) {
		titleList.setXPath("//item"); // rss feed
	} 
	
	titleList.loadData();
}

function <portlet:namespace/>_showInline(url) {
	//alert(url);
	var articlesFrame = document.getElementById("<portlet:namespace/>_articleFrame");
	articlesFrame.src = url ;
}

//-->
</script>
<style type="text/css">
<!--
#<portlet:namespace/>wrap {
	position:relative;
	left:0;
	top:0;
	width:100%;
	height:500px;
	z-index:1;
}

#<portlet:namespace/>wrap a {
	text-decoration: none;
}

#<portlet:namespace/>allFeeds {
	position:absolute;
	left:0px;
	top:0px;
	width:20%;
	height:100%;
	z-index:1;
	overflow: auto;
	
	border-right-width: medium;
	border-right-style: groove;
	border-right-color: #202020;
	margin: 1px;	
	
}

#<portlet:namespace/>feedTitles {
	position:absolute;
	left:20%;
	top:0px;
	width:80%;
	height:30%;
	z-index:1;
	overflow: auto;
	border-bottom-width: medium;
	border-bottom-style: groove;
	border-bottom-color: #202020;
	margin: 1px;
}

#<portlet:namespace/>articles {
	position:absolute;
	left:20%;
	top:30%;
	width:80%;
	height:70%;
	z-index:1;
	overflow: auto;
	margin: 1px;

}
-->
</style>

<portlet:resourceURL var="feedResourceUrl" id="selected_feed"/>
	  
<div id="<portlet:namespace/>wrap">

  <div id="<portlet:namespace/>allFeeds" spry:region="feedList" spry:loadingstate="feedlist-loading">
    <div spry:repeat="feedList">
    	<div spry:even="EvenRow" class="Feed"><a spry:select="selectedFeed" href="javascript:<portlet:namespace/>_loadUrl('<%=feedResourceUrl.toString()%>' , {feedList::@id} , '{feedList::@type}')" title="{feedList::name}">{feedList::name}</a></div>
    </div>
    <div spry:state="dataLoading" class="RSSTitleListLoading">List of configured feed is loading.... Please wait </div>
  </div>
  
  

  <div id="<portlet:namespace/>feedTitles">
    <div id="<portlet:namespace/>allFeedTitles" spry:region="titleList" spry:readystate="showTitles" spry:loadingstate="titleListLoading">
      <div spry:repeat="titleList" spry:state="showTitles">
      
      	<div spry:choose="spry:choose">
			<div spry:when="'{titleList::link/@href}' != 'undefined'">
				<div spry:select="selectedFeedTitle" class="FeedTitle" spry:even="EvenRow"><a  href="javascript:<portlet:namespace/>_showInline('{titleList::link/@href}')">{titleList::title}</a></div>
			</div>
			<div spry:when="'{titleList::link}' != 'undefined'">
				<div spry:select="selectedFeedTitle" class="FeedTitle" spry:even="EvenRow"><a  href="javascript:<portlet:namespace/>_showInline('{titleList::link}')">{titleList::title}</a></div>
			</div>
			<div spry:default="spry:default">Unexpected value Link</div>
		</div>
      </div>	<!--  end spry:repeat -->
      
      <div spry:state="titleListLoading" class="RSSTitleListLoading">Feed Data Is Still Loading.... Please wait </div>
    </div>
  
    
  </div> <!-- end of div feedTitles -->
  <div id="<portlet:namespace/>articles">
  	<iframe id="<portlet:namespace/>_articleFrame" src="" width="100%" height="100%" frameborder="0"> </iframe>
  </div> <!-- articles -->
</div> <!--  end of wrap -->


