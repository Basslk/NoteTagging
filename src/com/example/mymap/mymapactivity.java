package com.example.mymap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class mymapactivity extends MapActivity {
	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private MyOverlays itemizedoverlay;
	private MyLocationOverlay myLocationOverlay;
	
	
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_map);
	        
	        
	        
	        
	        mapView = (MapView) findViewById(R.id.map);
			mapView.setBuiltInZoomControls(true);
			mapView.setSatellite(true);
			mapController = mapView.getController();
			mapController.setZoom(14); // Zoon 1 is world view
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	       
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
					0, new GeoUpdateHandler());
			
			myLocationOverlay = new MyLocationOverlay(this, mapView);
			mapView.getOverlays().add(myLocationOverlay);

			myLocationOverlay.runOnFirstFix(new Runnable() {
				public void run() {
					mapView.getController().animateTo(myLocationOverlay.getMyLocation());
				}
			});

			Drawable drawable = this.getResources().getDrawable(R.drawable.ic_action_search);
			itemizedoverlay = new MyOverlays(this, drawable);
			createMarker();
			
			
		}

		@Override
		protected boolean isRouteDisplayed() {
			return false;
		}
		
		double lat,lng;
		
		public class GeoUpdateHandler implements LocationListener {

			@Override
			public void onLocationChanged(Location location) {
				lat =  (location.getLatitude() * 1E6);
				lng =  (location.getLongitude() * 1E6);
				GeoPoint point = new GeoPoint((int)lat, (int)lng);
				createMarker();
				mapController.animateTo(point); // mapController.setCenter(point);

			}

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
		}

		private void createMarker() {
			GeoPoint p = mapView.getMapCenter();
			OverlayItem overlayitem = new OverlayItem(p, "", "");
			itemizedoverlay.addOverlay(overlayitem);
			if (itemizedoverlay.size() > 0) {
				mapView.getOverlays().add(itemizedoverlay);
			}
		}

		@Override
		protected void onResume() {
			super.onResume();
			myLocationOverlay.enableMyLocation();
		}

		@Override
		protected void onPause() {
			super.onPause();
			myLocationOverlay.disableMyLocation();
		}
		BookmarksDataSource datasource;
		public void bookmarkBtnClicked(View v)
		{
			LayoutInflater inflater=LayoutInflater.from(this);
			final View addView=inflater.inflate(R.layout.activity_newbookmark, null);
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(	this);
	 
				// set title
				alertDialogBuilder.setTitle("New Bookmark");
	 			// set dialog message
				alertDialogBuilder
				.setView(addView)
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog,int id)
					{
						EditText e1=(EditText) addView.findViewById(R.id.editText1);
						EditText e2=(EditText) addView.findViewById(R.id.editText2);
						
						datasource=new BookmarksDataSource(mymapactivity.this);
						datasource.open();
						datasource.createComment(new Bookmark(e1.getText().toString(),e2.getText().toString(),lat,lng));
						
						Toast.makeText(mymapactivity.this, "Latitude: "+lat+" Langitude: "+lng+"", Toast.LENGTH_LONG).show();
					}
				})
				.setNegativeButton("No",new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog,int id) 
					{
						dialog.cancel();
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
		}
}