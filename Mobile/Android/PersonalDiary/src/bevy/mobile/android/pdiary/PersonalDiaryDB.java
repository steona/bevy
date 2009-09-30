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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import bevy.mobile.android.pdiary.models.Avatar;
import bevy.mobile.android.pdiary.models.DiaryEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

/**
 * @author Sandeep Soni
*/

public class PersonalDiaryDB extends SQLiteOpenHelper {

	public static final String KEY_TITLE = "title";
	public static final String KEY_BODY = "body";
	public static final String KEY_ROWID = "_id";
	
    private Context _context;

    public PersonalDiaryDB(Context context) {
	super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
	_context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
	String ddl = "Drop Table If Exists [Avatar];"
		+ "CREATE TABLE IF NOT EXISTS  \"Avatar\"([id] integer PRIMARY KEY AUTOINCREMENT NOT NULL,[avatar] VARCHAR2(30) NOT NULL,[password] VARCHAR2(10) NOT NULL);"
		+ "Insert  Into [Avatar] ([id],[avatar],[password]) Values(\"1\",\"soni\",\"soni\");"
		+ "Drop Table If Exists [entries];"
		+ "CREATE TABLE IF NOT EXISTS  \"entries\"([id] integer PRIMARY KEY AUTOINCREMENT NOT NULL,[title] NTEXT,[entry] NTEXT,[date_added] TIMESTAMP NOT NULL ON CONFLICT Rollback,[last_modified] TIME ,[avatar_id] BINARY NOT NULL ON CONFLICT Rollback, FOREIGN KEY ([avatar_id]) REFERENCES [Avatar] ([id]) );"
		+ "Insert  Into [entries] ([id],[title],[entry],[date_added],[last_modified],[avatar_id]) Values(NULL,\"this is a test1\",\"this is a test\",\"2009-09-22 17:13:33\",\"15:12:11\",\"1\");"
		+ "Insert  Into [entries] ([id],[title],[entry],[date_added],[last_modified],[avatar_id]) Values(NULL,\"this is a test2\",\"this is a test 2\",\"2009-09-24 17:15:33\",\"15:12:11\",\"1\");"
		+ "Insert  Into [entries] ([id],[title],[entry],[date_added],[last_modified],[avatar_id]) Values(NULL,\"this is a test3\",\"this is a test 3\",\"2009-09-12 17:15:33\",\"15:12:11\",\"1\");"
		+ "Insert  Into [entries] ([id],[title],[entry],[date_added],[last_modified],[avatar_id]) Values(NULL,\"this is a test4\",\"this is a test 4\",\"2009-09-16 17:15:33\",\"15:12:11\",\"1\");"
		+ "Insert  Into [entries] ([id],[title],[entry],[date_added],[last_modified],[avatar_id]) Values(NULL,\"this is a test5\",\"this is a test 5\",\"2009-10-24 17:15:33\",\"15:12:11\",\"1\");";

	db.beginTransaction();
	try {
	    // Create tables & test data
	    executeBatchSQL(db, ddl.split(";"));
	    db.setTransactionSuccessful();
	} catch (SQLException e) {
	    Log.e("Error creating tables and debug data", e.toString());
	} finally {
	    db.endTransaction();
	}

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {
	// onCreate(sqlitedatabase);
    }

    private void executeBatchSQL(SQLiteDatabase db, String[] sql) {
	for (String s : sql) {
	    if (s.trim().length() > 0) {
		db.execSQL(s);
	    }
	}
    }

    public void createAccount(String avatarName, String avatarPassword) {
	SQLiteDatabase db = getWritableDatabase();
	String query = String.format(
			"Insert  Into Avatar (avatar,password) Values('%s','%s')",
			avatarName, avatarPassword);
	Log.d(Utils.APPLICATION_LOG_KEY, query);
	try {
	    db.execSQL(query);
	} catch (SQLException e) {
	    Log.e("Error creating new account", e.toString());
	}
    }
    
    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param title the title of the note
     * @param body the body of the note
     * @return rowId or -1 if failed
     */
/*    public long createNote(String title, String body, Date date) {
    	SQLiteDatabase db = getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_BODY, body);
        
        return db.insert("entries", null, initialValues);
    	String query = String.format(
    			"Insert  Into entries (entry,date_added,last_modified,avatar_id) Values('%s','%s')",
    			body, avatarPassword);
    	Log.d(Utils.APPLICATION_LOG_KEY, query);
    	try {
    	    db.execSQL(query);
    	} catch (SQLException e) {
    	    Log.e("Error creating new note", e.toString());
    	}
    }*/

    public List<Avatar> getAllAvatars() {
	SQLiteDatabase db = getReadableDatabase();
	List<Avatar> avatars = new ArrayList<Avatar>();
	AvatarCursor c = (AvatarCursor) db.rawQueryWithFactory(
		new AvatarCursor.Factory(), AvatarCursor.QUERY, null, null);
	c.moveToFirst();
	while (!c.isAfterLast()) {
	    Avatar a = new Avatar();
	    a.setAvatarName(c.getColAvatar());
	    a.setId(c.getColId() + "");
	    a.setPassword(c.getColPassword());
	    avatars.add(a);
	    c.moveToNext();
	}
	return avatars;
    }

    public Avatar getAvatar(String avatarName) {
	SQLiteDatabase db = getReadableDatabase();

	String query = "SELECT id, password " + "FROM Avatar where avatar='"
		+ avatarName + "'";

	AvatarCursor c = (AvatarCursor) db.rawQueryWithFactory(
		new AvatarCursor.Factory(), query, null, null);

	Avatar a = new Avatar();

	if (c.getCount() > 0) {
	    c.moveToFirst();

	    a.setAvatarName(avatarName);
	    a.setId(c.getColId() + "");
	    a.setPassword(c.getColPassword());
	}

	return a;
    }

    public List<DiaryEntry> getEntriesForDay(Date d) {
	DateFormat df = DateFormat.getDateInstance();
	String dateStr = df.format(d);

	String query = DiaryEntryCursor.QUERY;
	query.replace("%DATE%", dateStr);

	List<DiaryEntry> entries = new LinkedList<DiaryEntry>();

	SQLiteDatabase db = getReadableDatabase();
	DiaryEntryCursor c = (DiaryEntryCursor) db.rawQueryWithFactory(
		new DiaryEntryCursor.Factory(), DiaryEntryCursor.QUERY, null,
		null);

	return entries;
    }

    public List<String> getDaysHavingEventsInMonth(Date time) {
	SQLiteDatabase db = getReadableDatabase();
	Log.d(Utils.APPLICATION_LOG_KEY, 
		DiaryEntryCursor.getMonthlyQuery(time));

	List<String> days = new ArrayList<String>();
	DiaryEntryCursor c = (DiaryEntryCursor) db.rawQueryWithFactory(
		new DiaryEntryCursor.Factory(), DiaryEntryCursor
			.getMonthlyQuery(time), null, null);
	c.moveToFirst();
	while (!c.isAfterLast()) {
	    try {
		days.add(Utils.getStringFromDate(c.getColDateAdded()));
	    } catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    c.moveToNext();
	}

	return days;
    }

    public static class AvatarCursor extends SQLiteCursor {

	private static final String QUERY = "SELECT id, avatar, password "
		+ "FROM Avatar " + "ORDER BY avatar";

	private AvatarCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
		String editTable, SQLiteQuery query) {
	    super(db, driver, editTable, query);
	}

	private static class Factory implements SQLiteDatabase.CursorFactory {
	    @Override
	    public Cursor newCursor(SQLiteDatabase db,
		    SQLiteCursorDriver driver, String editTable,
		    SQLiteQuery query) {
		return new AvatarCursor(db, driver, editTable, query);
	    }
	}

	public long getColId() {
	    return getLong(getColumnIndexOrThrow("id"));
	}

	public String getColAvatar() {
	    return getString(getColumnIndexOrThrow("avatar"));
	}

	public String getColPassword() {
	    return getString(getColumnIndexOrThrow("password"));
	}
    }

    public static class DiaryEntryCursor extends SQLiteCursor {
	private static final String QUERY = "SELECT id, entry, date_added,last_modified,avatar_id "
		+ "FROM entries where date_added = '%DATE%'"
		+ "ORDER BY date_added";

	private static final String QUERY_MONTHLY = "select date_added from entries where date_added between '%MONTH_START%' and '%MONTH_END%'";

	public DiaryEntryCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
		String editTable, SQLiteQuery query) {
	    super(db, driver, editTable, query);
	}

	private static class Factory implements SQLiteDatabase.CursorFactory {
	    @Override
	    public Cursor newCursor(SQLiteDatabase db,
		    SQLiteCursorDriver driver, String editTable,
		    SQLiteQuery query) {
		return new DiaryEntryCursor(db, driver, editTable, query);
	    }
	}

	public static String getMonthlyQuery(Date d) {
	    String q = QUERY_MONTHLY;
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(d);

	    SimpleDateFormat sdf = new SimpleDateFormat();
	    sdf.applyPattern("yyyy-MM-dd");

	    String dateStr = sdf.format(cal.getTime());
	    String start = dateStr.replaceFirst("[0-9]{2}$", "01");
	    String end = dateStr.replaceFirst("[0-9]{2}$", "31");

	    return q.replace("%MONTH_START%", start)
		    .replace("%MONTH_END%", end);

	}

	public long getColId() {
	    return getLong(getColumnIndexOrThrow("id"));
	}

	public String getColEntry() {
	    return new String(getBlob(getColumnIndexOrThrow("entry")));
	}

	public Date getColDateAdded() throws ParseException {
	    return Utils
		    .getDateFromString(getString(getColumnIndexOrThrow("date_added")));
	}

	public long getColLastModified() {
	    return getLong(getColumnIndexOrThrow("last_modified"));
	}

	public long getColAvatarId() {
	    return getLong(getColumnIndexOrThrow("avatar_id"));
	}
    }

}
