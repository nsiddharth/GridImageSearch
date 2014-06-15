package codepath.example.gridimagesearch;

import java.util.List;


import com.loopj.android.image.SmartImageView;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultArrayAdapter(Context context,
			List<ImageResult> objects) {
		super(context, R.layout.item_image_result, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ImageResult imgInfo = this.getItem(position);
		SmartImageView smartImg;
		
		if(convertView==null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			smartImg = (SmartImageView) inflater.inflate(R.layout.item_image_result, parent,false);
		}
		else{
			smartImg = (SmartImageView) convertView;
			smartImg.setImageResource(android.R.color.transparent);
			
		}
		smartImg.setImageUrl(imgInfo.getThumbUrl());
		return smartImg;
	}

}
