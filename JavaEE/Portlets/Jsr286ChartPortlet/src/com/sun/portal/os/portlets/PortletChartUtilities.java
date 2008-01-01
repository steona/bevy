/*
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
 */

package com.sun.portal.os.portlets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;
import org.jfree.data.jdbc.JDBCPieDataset;
import org.jfree.data.jdbc.JDBCXYDataset;

public class PortletChartUtilities {

    private static final String DEFAULT_TIME_SERIES_SQL = "SELECT date, series1 , series2 , series3 FROM timeseries";

    private static final String DEFAULT_BAR_CHART_SQL = "SELECT category, series1 , series2 , series3 FROM barchart";

    private static final String DEFAULT_PIE_CHART_SQL = "SELECT product_category, bug_count FROM piechart";

    private static String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private static final long serialVersionUID = 1L;

    private static final String CONTENT_TYPE_IMG_PNG = "image/png";

    private static final String TIME_CHART = "time";

    private static final String BAR_CHART = "bar";

    private static final String PIE_CHART = "pie";

    private static String JDBC_URL = "jdbc:mysql://localhost/chart?user=chart&password=chart";

    public static void writeChartImageData(ResourceRequest request, ResourceResponse response, String defaultImagePath)
	    throws IOException {
	response.setContentType(CONTENT_TYPE_IMG_PNG);
	OutputStream out = response.getPortletOutputStream();

	try {
	    JFreeChart chart = createChart(request);
	    if (chart != null) {
		ChartUtilities.writeChartAsPNG(out, chart, 400, 300);
	    } else {
		// A problem occurred - Stream out the default image
		writeDefaultImage(out, defaultImagePath);
	    }
	} catch (Exception e) {
	    System.err.println(e.toString());
	} finally {
	    out.close();
	}
    }

    private static void writeDefaultImage(OutputStream out, String defaultImagePath) throws IOException {

	FileInputStream fin = null;
	try {
	    byte[] b = new byte[1024];
	    fin = new FileInputStream(defaultImagePath);
	    while (fin.read(b) != -1) {
		out.write(b);
	    }
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    fin.close();
	}
    }

    private static Connection getDatabaseConnection(ResourceRequest request) throws SQLException,
	    NoPreferredDbSetException {
	PortletPreferences prefs = request.getPreferences();

	if (prefs == null)
	    throw new NoPreferredDbSetException("No Preferences Set.. Don't know what DB to connect to");

	String driverUrl = prefs.getValue(Constants.PREF_JDBC_DRIVER_URL, JDBC_URL);
	String driverClass = prefs.getValue(Constants.PREF_JDBC_DRIVER_CLASS, MYSQL_JDBC_DRIVER);

	debugMessage("Driver Class: " + driverClass);
	debugMessage("Driver URL : " + driverUrl);

	try {
	    Class.forName(driverClass);
	} catch (ClassNotFoundException e) {
	    System.err.print("ClassNotFoundException: ");
	    System.err.println(e.getMessage());
	}
	return DriverManager.getConnection(driverUrl);
    }

    private static JFreeChart createChart(ResourceRequest request) {
	JFreeChart chart = null;
	try {
	    PortletPreferences prefs = request.getPreferences();

	    if (prefs == null)
		throw new NoPreferredDbSetException("Preferences not passed in from portlet");

	    String type = prefs.getValue(Constants.PREF_CHART_TYPE, PIE_CHART);

	    debugMessage("Chart type :" + type);

	    if (type.equals(PIE_CHART)) {
		String sql = prefs.getValue(Constants.PREF_PIE_CHART_SQL, DEFAULT_PIE_CHART_SQL);
		PieDataset data = (PieDataset) generatePieDataSet(getDatabaseConnection(request), sql);
		chart = ChartFactory.createPieChart("Pie Chart", data, true, true, false);
	    } else if (type.equals(BAR_CHART)) {
		String sql = prefs.getValue(Constants.PREF_BAR_CHART_SQL, DEFAULT_BAR_CHART_SQL);
		JDBCCategoryDataset data = (JDBCCategoryDataset) generateBarChartDataSet(
			getDatabaseConnection(request), sql);
		chart = ChartFactory.createBarChart3D("Bar Chart", "Category", "Value", data, PlotOrientation.VERTICAL,
			true, true, false);
	    } else if (type.equals(TIME_CHART)) {
		String sql = prefs.getValue(Constants.PREF_TIME_SERIES_SQL, DEFAULT_TIME_SERIES_SQL);
		JDBCXYDataset data = (JDBCXYDataset) generateXYDataSet(getDatabaseConnection(request), sql);
		chart = ChartFactory
			.createTimeSeriesChart("Time Series Chart", "Date", "Rate", data, true, true, false);
	    }

	} catch (NoPreferredDbSetException npdbs) {
	    npdbs.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return chart;
    }

    private static JDBCXYDataset generateXYDataSet(Connection con, String sql) {
	JDBCXYDataset data = null;

	try {
	    data = new JDBCXYDataset(con);
	    data.executeQuery(sql);
	    con.close();
	} catch (SQLException e) {
	    System.err.print("SQLException: ");
	    System.err.println(e.getMessage());
	} catch (Exception e) {
	    System.err.print("Exception: ");
	    System.err.println(e.getMessage());
	}
	return data;
    }

    private static JDBCCategoryDataset generateBarChartDataSet(Connection con, String sql) {
	JDBCCategoryDataset data = null;

	try {
	    data = new JDBCCategoryDataset(con);
	    data.executeQuery(sql);
	    con.close();
	} catch (SQLException e) {
	    System.err.print("SQLException: ");
	    System.err.println(e.getMessage());
	} catch (Exception e) {
	    System.err.print("Exception: ");
	    System.err.println(e.getMessage());
	}
	return data;
    }

    protected static Dataset generatePieDataSet(Connection con, String sql) {
	JDBCPieDataset data = null;

	try {
	    data = new JDBCPieDataset(con);

	    data.executeQuery(sql);
	    con.close();
	} catch (SQLException e) {
	    System.err.print("SQLException: ");
	    System.err.println(e.getMessage());
	} catch (Exception e) {
	    System.err.print("Exception: ");
	    System.err.println(e.getMessage());
	}
	return data;
    }

    protected static void debugMessage(String message) {
	System.out.println("CHARTPORTLET ===> " + message);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
	// ChartUtilities.writeChartAsPNG(new
	// FileOutputStream("c:\\temp\\test.png"), main.createChart(TIME_CHART),
	// 400, 300);
    }

}
