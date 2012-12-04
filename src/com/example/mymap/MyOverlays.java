package com.example.mymap;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyOverlays extends ItemizedOverlay<OverlayItem> {
	private static int maxNum = 1000;
	private OverlayItem overlays[] = new OverlayItem[maxNum];
	private List<Bookmark> myOverlays=new ArrayList<Bookmark>();
	private int index = 0;
	private boolean full = false;
	private Context context;
	private OverlayItem previousoverlay;
	private BookmarksDataSource datasource;

	public MyOverlays(Context context, Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;
		datasource=new BookmarksDataSource(context);
	}

	@Override
	protected OverlayItem createItem(int i) {
		return overlays[i];
	}

	@Override
	public int size() {
		if (full) {
			return overlays.length;
		} else {
			return index;
		}

	}
	
	public void addOverlay(OverlayItem overlay,Bookmark bookmark) {
		if (previousoverlay != null) {
			if (index < maxNum) {
				overlays[index] = previousoverlay;
			} else {
				index = 0;
				full = true;
				overlays[index] = previousoverlay;
			}
			index++;
			populate();
		}
		this.myOverlays.add(bookmark);
		this.previousoverlay = overlay;
	}
	public void addOverlay(OverlayItem overlay) {
		if (previousoverlay != null) {
			if (index < maxNum) {
				overlays[index] = previousoverlay;
			} else {
				index = 0;
				full = true;
				overlays[index] = previousoverlay;
			}
			index++;
			populate();
		}
		this.previousoverlay = overlay;
	}

	protected boolean onTap(int index) {
		//OverlayItem overlayItem = overlays[index];
		Bookmark obj=myOverlays.get(index);
		if(obj!=null){
		Intent i=new Intent(context,BookmarkDetails.class);
		//Bookmark obj=datasource.getBookmarkById(temp.itemIndex);
		i.putExtra("title", obj.Name);
		i.putExtra("details", obj.Description);
		i.putExtra("lat", obj.lat+"");
		i.putExtra("lon", obj.lon+"");
		i.putExtra("src", "map");
		i.putExtra("id", obj.id);
		context.startActivity(i);
		}
		/*
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("This will end the activity");
		builder.setCancelable(true);
		builder.setPositiveButton("I agree", new OkOnClickListener());
		builder.setNegativeButton("No, no", new CancelOnClickListener());
		AlertDialog dialog = builder.create();
		dialog.show();*/
		return true;
	};

	
} 
