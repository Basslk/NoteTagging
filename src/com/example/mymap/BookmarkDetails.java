package com.example.mymap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BookmarkDetails extends Activity {
	
	
	TextView titleView,detailsView,latView,lanView;
	String source="";
	Integer selectedID=0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	 super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_bookmarkdetails);
    
    	
    	
    	titleView=(TextView)findViewById(R.id.textView1);
    	detailsView=(TextView)findViewById(R.id.textView2);
    	latView=(TextView)findViewById(R.id.textView3);
    	lanView=(TextView)findViewById(R.id.textView4);
    	
    	
    	Bundle extras = getIntent().getExtras();
    	if (extras == null) {
    			return;
    			}
    	
    	// Get data via the key
    	String value1 = extras.getString("title");
    	String value2 = extras.getString("details");
    	String value3 = extras.getString("lat");
    	String value4 = extras.getString("lon");
    	this.selectedID=extras.getInt("id");
    this.source=extras.getString("src");
  
    	titleView.setText(titleView.getText()+": "+value1);
    	detailsView.setText(detailsView.getText()+": "+value2);
    	latView.setText(latView.getText()+": "+value3);
    	lanView.setText(lanView.getText()+": "+value4);
    }

    public void backBtnClicked(View view)
    {
    		backLogic();
   
    }
    public void backLogic()
    {
    	if(source.contains("map"))
		{
			Intent i=new Intent(this,BookmarksOnMap.class);
			startActivity(i);
		}
		else 
		{
			Intent i=new Intent(this,MainActivity.class);
			startActivity(i);
		}
    }
    
    BookmarksDataSource datasouce=new BookmarksDataSource(this);
    public void deleteBtnClicked(View v)
    {
    	datasouce.open();
    	datasouce.deleteComment(selectedID);
    	datasouce.close();
    	backLogic();

    }
}