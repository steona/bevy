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
package bevy.mobile.android.pdiary.activities;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import bevy.mobile.android.pdiary.GridViewDayAdapter;
import bevy.mobile.android.pdiary.PersonalDiaryDB;
import bevy.mobile.android.pdiary.R;

/**
 * @author Sandeep Soni
*/

public class MainScreen extends Activity {

    private PersonalDiaryDB _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	_db = new PersonalDiaryDB(this);
	
	setContentView(R.layout.main);
	setUpCalendar();
    }

    private void setUpCalendar() {
	GridView calView = (GridView) findViewById(R.id.calGridView);
	calView.setAdapter(new GridViewDayAdapter(this,Calendar.getInstance().getTime(),_db));
    }
}
