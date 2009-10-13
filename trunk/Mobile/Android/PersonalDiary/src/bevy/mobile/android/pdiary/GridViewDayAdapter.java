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
package bevy.mobile.android.pdiary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * @author Sandeep Soni
*/

public class GridViewDayAdapter extends BaseAdapter implements ListAdapter {

    private Context _context;
    private Calendar _cal;
    private PersonalDiaryDB _db;
    
    private List<String> _daysHavingEvents;
    SimpleDateFormat _sdf = new SimpleDateFormat();
    
    
    public GridViewDayAdapter(Context c, Date date, PersonalDiaryDB db) {
	_context = c;
	_cal = Calendar.getInstance();
	_cal.setTime(date);
	_db = db;
	_daysHavingEvents = _db.getDaysHavingEventsInMonth(_cal.getTime());
	_sdf.applyPattern("yyyy-MM-dd");
	
	Log.d("PDIARY", "Days having events : " + _daysHavingEvents);
    }

    

    @Override
    public boolean areAllItemsEnabled() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isEnabled(int position) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public int getCount() {
	return _cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Object getItem(int arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public long getItemId(int position) {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getItemViewType(int position) {
	// TODO Auto-generated method stub
	return 0;
    }

    public View getViewd(int position, View convertView, ViewGroup parent) {
	TextView tv;

	if (convertView == null) {
	    tv = new TextView(_context);
	    tv.setText(position + "");

	} else {
	    tv = (TextView) convertView;
	}
	return tv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

	TextView tv;
	position = position + 1;
	if (convertView == null) {
	    tv = new TextView(_context);
	    tv.setText(position + "");
	    tv.setClickable(true);
	    

	} else {
	    tv = (TextView) convertView;
	}

	if (dayHasEvents(position)) {
		tv.setBackgroundColor(Color.GRAY);
	}

	tv.setMinLines(3);
	return tv;
    }

    private boolean dayHasEvents(int position) {
	String date = Utils.getStringFromDate(_cal.getTime());
	String toTest = date.replaceFirst("[0-9]{2}$", String.format("%02d",position));
	    
	if ( _daysHavingEvents.contains(toTest)) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public boolean hasStableIds() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isEmpty() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
	// TODO Auto-generated method stub

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
	// TODO Auto-generated method stub
    }

}