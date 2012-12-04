package com.example.mymap;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button mapBtn;
	Button findLocationBtn;
	ListView bookmarksList;
	ArrayAdapter<Bookmark> adapter;
	static List<Bookmark> list = new ArrayList<Bookmark>();
	private BookmarksDataSource datasource;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        
        
        bookmarksList=(ListView)findViewById(R.id.listView1);
        datasource = new BookmarksDataSource(this);
		datasource.open();
		list = datasource.getAllComments();

		
		bookmarksList.setAdapter(adapter);
        adapter = new BookmarkAdapter(this,
				list);
        bookmarksList.setAdapter(adapter);
        
		bookmarksList.setOnItemClickListener(listener);
        bookmarksList.refreshDrawableState();
       
    }
    OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) 
		{
			Intent i=new Intent(MainActivity.this,BookmarkDetails.class);
			Bookmark temp=list.get(arg2);
			i.putExtra("title", temp.Name);
			i.putExtra("details", temp.Description);
			i.putExtra("lat", temp.lat+"");
			i.putExtra("lon", temp.lon+"");
			i.putExtra("src", "list");
			i.putExtra("id",temp.id);
			startActivity(i);
			
		}
    	
	};
    
    public void mapBtnClick(View v)
    {
    		Intent i=new Intent(MainActivity.this,BookmarksOnMap.class);
		startActivity(i);
    }
    
    public void findLocationBtnClick(View v)
    {
    		Intent i=new Intent(this,mymapactivity.class);
		startActivity(i);
    }
    
    
    @Override
	protected void onResume() {
		datasource.open();
list = datasource.getAllComments();

		
		bookmarksList.setAdapter(adapter);
        adapter = new BookmarkAdapter(this,
				list);
        bookmarksList.setAdapter(adapter);
		super.onResume();
	}

	@Override
	protected void onPause() {
		
		datasource.close();
		super.onPause();
	}

    
    public static void AddToList(String header,String Description,double lat,double lon)
    {
    	list.add(get(header,lat,lon));
    }
    
	private static Bookmark get(String s,double lat,double lon) {
		return new Bookmark(s,lat,lon);
	}
    
}