package com.example.mymap;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BookmarkAdapter extends ArrayAdapter<Bookmark> {


	private final List<Bookmark> list;
	private final Activity context;

	public BookmarkAdapter(Activity context, List<Bookmark> list) {
		super(context, R.layout.row, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected TextView headerText;
		protected TextView lat;
		protected TextView lon;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.row, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.headerText = (TextView) view.findViewById(R.id.header);
			viewHolder.lat = (TextView) view.findViewById(R.id.lat);
			viewHolder.lon = (TextView) view.findViewById(R.id.lan);
			
			
			/*viewHolder.checkbox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							Bookmark element = (Bookmark) viewHolder.checkbox
									.getTag();
							element.setSelected(buttonView.isChecked());

						}
					});*/
			view.setTag(viewHolder);
			//viewHolder.checkbox.setTag(list.get(position));
		} else {
			view = convertView;
			//((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.headerText.setText(list.get(position).Name);
		holder.lat.setText(list.get(position).lat+"");
		holder.lon.setText(list.get(position).lon+"");
		
		//holder.checkbox.setChecked(list.get(position).isSelected());
		return view;
	}
} 