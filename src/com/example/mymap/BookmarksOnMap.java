package com.example.mymap;


import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.os.Bundle;

import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import com.google.android.maps.OverlayItem;

public class BookmarksOnMap extends MapActivity 
{
	
	
	private BookmarksDataSource datasource;
	private MapController mapController;
	private MapView mapView;

	private MyOverlays itemizedoverlay;
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarksonmap);

        
        mapView = (MapView) findViewById(R.id.map);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);
		mapController = mapView.getController();
		mapController.setZoom(14); // Zoon 1 is world view
		

		Drawable drawable = this.getResources().getDrawable(R.drawable.ic_action_search);
		itemizedoverlay = new MyOverlays(this, drawable);
		createMarker();
		
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	double lat,lng;
	
	

	private void createMarker() {
		datasource=new BookmarksDataSource(BookmarksOnMap.this);
		datasource.open();
		List<Bookmark> result=datasource.getAllComments();
		for (Bookmark bookmark : result) 
		{
			GeoPoint p = new GeoPoint((int)bookmark.lat, (int)bookmark.lon);
			OverlayItem overlayitem = new OverlayItem(p, "", "");
			MyOverlayItem temp= new MyOverlayItem();
			temp.item=overlayitem;
			temp.itemIndex=bookmark.id;
			itemizedoverlay.addOverlay(overlayitem,bookmark);
			if (itemizedoverlay.size() > 0) {
				mapView.getOverlays().add(itemizedoverlay);
			}
		}
		
	}

	@Override
	protected void onResume() {
		createMarker();
		super.onResume();
		
	}

	@Override
	protected void onPause() {
		createMarker();
		super.onPause();
		
	}


	public void bookmarkBtnClicked(View v)
    {
    		Intent i=new Intent(this,mymapactivity.class);
		startActivity(i);
    }
	public void listBtnClicked(View v)
	{
		Intent i=new Intent(BookmarksOnMap.this,MainActivity.class);
		startActivity(i);
	}
}