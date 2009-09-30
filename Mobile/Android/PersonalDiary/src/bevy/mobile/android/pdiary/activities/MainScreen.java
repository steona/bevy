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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import bevy.mobile.android.pdiary.GridViewDayAdapter;
import bevy.mobile.android.pdiary.PersonalDiaryDB;
import bevy.mobile.android.pdiary.R;

/**
 * @author Sandeep Soni
*/

public class MainScreen extends Activity {

    private PersonalDiaryDB _db;
    
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    
    public static final int EDIT_ID = Menu.FIRST;
    public static final int HOME_ID = Menu.FIRST + 1;
    public static final int LIST_ID = Menu.FIRST + 3;
    public static final int DELETE_ID = Menu.FIRST + 4;
    public static final int ABOUT_ID = Menu.FIRST + 5;
    public static final int EXPORT_ID = Menu.FIRST + 6;
    public static final int IMPORT_ID = Menu.FIRST + 7;
    public static final int EMAIL_ID = Menu.FIRST + 8;

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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	super.onCreateOptionsMenu(menu);
	menu.add(0, EDIT_ID, 0, R.string.menu_edit).setShortcut('1', 'e')
	    .setIcon(R.drawable.icon_delete);
	menu.add(0, HOME_ID, 0, R.string.menu_start).setShortcut('4', 'h')
	    .setIcon(R.drawable.icon_start);
	menu.add(0, LIST_ID, 0, R.string.menu_recent).setShortcut('3', 'r')
	    .setIcon(R.drawable.icon_recent);
	menu.add(0, DELETE_ID, 0, R.string.menu_delete).setShortcut('2', 'd')
	    .setIcon(R.drawable.icon_delete);
	menu.add(0, ABOUT_ID, 0, R.string.menu_about).setShortcut('5', 'a')
	    .setIcon(android.R.drawable.ic_dialog_info);
	menu.add(0, EXPORT_ID, 0, R.string.menu_export).setShortcut('7', 'x')
	    .setIcon(android.R.drawable.ic_dialog_info);
	menu.add(0, IMPORT_ID, 0, R.string.menu_import).setShortcut('8', 'm')
	    .setIcon(android.R.drawable.ic_dialog_info);	
	menu.add(0, EMAIL_ID, 0, R.string.menu_email).setShortcut('6', 'm')
	    .setIcon(android.R.drawable.ic_dialog_info);
	return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case EDIT_ID:
            createNote();
            return true;
        }
       
        return super.onMenuItemSelected(featureId, item);
    }
    
    private void createNote() {
        Intent i = new Intent(this, NoteEditActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }
}
