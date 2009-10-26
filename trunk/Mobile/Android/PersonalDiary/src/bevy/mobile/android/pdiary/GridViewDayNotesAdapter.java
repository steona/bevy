package bevy.mobile.android.pdiary;

import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

public class GridViewDayNotesAdapter extends BaseAdapter implements ListAdapter{
	
	private Map<Integer,String> notes;
	private Map<Long,Integer> ids;
	private Context c;
	private int size;
	public GridViewDayNotesAdapter(Context c,Map<Integer,String> notesMap, Map<Long,Integer>idMap){
		this.c = c;
		notes = notesMap;
		ids = idMap;
		size = notes.size()*2;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			if(position%2 == 0){
				TextView v = new TextView(c);
				Long l = new Long(position+"");
				l = l/2;
				int id = ids.get(l);
				String textToSet = notes.get(id);
				v.setText(textToSet);
				return v;
			}else{
				TextView del = new TextView(c);
				del.setText("X");
				del.setGravity(Gravity.RIGHT);
				del.setTextColor(Color.RED);
				return del;
			}
		}else{
			return convertView;
		}
	}
	
}
