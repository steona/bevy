package bevy.mobile.android.pdiary.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import bevy.mobile.android.pdiary.PersonalDiaryDB;
import bevy.mobile.android.pdiary.R;

public class ListAllNotesActivity extends ListActivity {

	private PersonalDiaryDB mDbHelper;
	
	public static final int VIEW_ID = Menu.FIRST;
    public static final int DELETE_ID = Menu.FIRST + 1;
    private static final int ACTIVITY_CREATE=0;
    private long selectedItem = -1;
    
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mDbHelper = new PersonalDiaryDB(this);
		showContent();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
	super.onCreateOptionsMenu(menu);
	menu.add(0, VIEW_ID, 0, R.string.menu_edit).setShortcut('1', 'e')
	    .setIcon(R.drawable.icon_delete);
	menu.add(0, DELETE_ID, 0, R.string.menu_delete).setShortcut('2', 'h')
	    .setIcon(R.drawable.icon_delete);
	return true;
	}
	
	@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case VIEW_ID:
            viewNote();
            return true;
        case DELETE_ID:
        	deleteNote();
        	return true;
        }
       
        return super.onMenuItemSelected(featureId, item);
    }
    
    private void viewNote() {
        Intent i = new Intent(this, NoteViewActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }
    
    private void deleteNote(){
    	
    	if(mDbHelper==null){
    		mDbHelper = new PersonalDiaryDB(this);
    	}
    	mDbHelper.deleteNote(++selectedItem);
    	showContent();
    }
    
    private void showContent(){
		
		String[] notes = mDbHelper.getAllNotes();
		if(notes != null){
		setListAdapter(new ArrayAdapter<String>(this,
		          android.R.layout.simple_list_item_1, notes));
		  getListView().setTextFilterEnabled(true);
		  getListView().setClickable(true);
		  ListView v = getListView();
		  final Context c = this;
		  
		  v.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent i = new Intent(c, NoteViewActivity.class);
				i.putExtra("id", id);
				c.startActivity(i);
			}
			  
		  });
		  
		  v.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selectedItem = id;
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			  
		  });
		  		  
		}else{
			notes = new String[1];
			notes[0] = "There are no entries";
			setListAdapter(new ArrayAdapter<String>(this,
			      android.R.layout.simple_list_item_1, notes));
			getListView().setTextFilterEnabled(true);
		}
   	
    }
	
}
