package bevy.mobile.android.pdiary.activities;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import bevy.mobile.android.pdiary.PersonalDiaryDB;
import bevy.mobile.android.pdiary.R;


public class NoteViewActivity extends Activity {
	private PersonalDiaryDB mDbHelper;
	private TextView mTitleValue;
	private TextView mBodyValue;
	private TextView mCreatedDateValue;
	private TextView mLastModifiedValue;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mDbHelper = new PersonalDiaryDB(this);
		setContentView(R.layout.note_view);
		mTitleValue = (TextView) findViewById(R.id.titlevalue); 
		mBodyValue = (TextView) findViewById(R.id.bodyvalue);
		mCreatedDateValue = (TextView) findViewById(R.id.createdvalue);
		mLastModifiedValue = (TextView) findViewById(R.id.lastmodifiedvalue);
		
		Bundle extras = getIntent().getExtras();
		long id;
		if(extras != null){
			id = extras.getLong("id");
			id++;
			SQLiteDatabase db = mDbHelper.getReadableDatabase();
			Cursor c = db.query("entries", null, "id = "+id, null, null, null, null);
			if(c.moveToFirst()){
				String title = c.getString(c.getColumnIndex("title"));
				String createdDate = c.getString(c.getColumnIndex("date_added"));
				String body = c.getString(c.getColumnIndex("entry"));
				String lastModified = c.getString(c.getColumnIndex("last_modified"));
				if(title != null){
					mTitleValue.setText(title);
					
				}
				if(createdDate != null){
					mCreatedDateValue.setText(createdDate);
				}
				if(body != null){
					mBodyValue.setText(body);
				}
				if(lastModified != null){
					mLastModifiedValue.setText(lastModified);
				}
			}
		}
		
		
		
		
		
		
	}

}
