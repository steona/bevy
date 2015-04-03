### Summary ###

This is a sample portlet that shows how to generate charts using the [JFreeChart](http://www.jfree.org/jfreechart/) library. JFreeChart is an open-source graphing and charting library. I have written an article on Sun Developer Network that talks in brief detail on how to develop a portlet using this library.

This sample portlet here is an extension to that article. It shows how you can read data from a database and create dynamic charts from that data. The difference between this portlet and the one which the article talks about is that in this portlet the chart is generated dynamically and not saved in any temporary location. In addition to this, the current portlet also allows the user to select the type of chart he wants to generate and then dynamically fetches the data from the database ( based on the SQL that the user specifies ) and renders the chart.


To read more about the Chart Portlet please read the included documentation (index.html) with the portlet in the "docs" folder ( available on the "Source" tab above )

You can also download the WAR file available on the "Downloads" tab above. But make sure you read the documentation included before proceeding.

You can access the documentation [here](http://bevy.googlecode.com/svn/trunk/JavaEE/Portlets/ChartPortlet/docs/index.html).