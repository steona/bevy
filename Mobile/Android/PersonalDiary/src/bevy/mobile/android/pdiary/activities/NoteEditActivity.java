/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bevy.mobile.android.pdiary.activities;

import java.util.Calendar;

import bevy.mobile.android.pdiary.PersonalDiaryDB;
import bevy.mobile.android.pdiary.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class NoteEditActivity extends Activity {

	private EditText mTitleText;
    private EditText mBodyText;
    private TextView mDateDisplay;
    private Long mRowId;
    private PersonalDiaryDB mDbHelper;
    private int mMonth;
    private int mDay;
    private int mYear;
    private StringBuilder mTimestamp;
    private Button mPickDate;
    static final int DATE_DIALOG_ID = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new PersonalDiaryDB(this);
        
        setContentView(R.layout.note_edit);
        
       
        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);
        mPickDate = (Button) findViewById(R.id.pickDate);
        mDateDisplay = (TextView) findViewById(R.id.date);
        
     // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        
        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        // display the current date
        updateDisplay();
        
         
        Button confirmButton = (Button) findViewById(R.id.save);
       
        mRowId = savedInstanceState != null ? savedInstanceState.getLong(PersonalDiaryDB.KEY_ROWID) 
                							: null;
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();            
			mRowId = extras != null ? extras.getLong(PersonalDiaryDB.KEY_ROWID) 
									: null;
		}

        confirmButton.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View view) {
        	    setResult(RESULT_OK);
        	    finish();
        	}
          
        });
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }
   
 // updates the date we display in the TextView
    private void updateDisplay() {
    	mTimestamp = new StringBuilder()
        .append(mYear).append("-")
        .append(mMonth + 1).append("-")
        .append(mDay).append(" ")
        .append("23").append(":")
        .append("12").append(":")
        .append("23");
    	
    	mDateDisplay.setText(mTimestamp);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PersonalDiaryDB.KEY_ROWID, mRowId);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        //populateFields();
    }
    
    private void saveState() {
        String title = mTitleText.getText().toString();
        String body = mBodyText.getText().toString();
        String date = mTimestamp.toString();//mDateText.getText().toString();

        if (mRowId == null) {
            long id = mDbHelper.createNote(title, body, date);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            //mDbHelper.updateNote(mRowId, title, body);
        }
    }
    
 // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
}
