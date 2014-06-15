package codepath.example.gridimagesearch;

import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult {
	
	
	private String fullUrl; 
	private String thumbUrl;


	public ImageResult(JSONObject json){
	try{
	this.setFullUrl(json.getString("url"));
	this.setThumbUrl(json.getString("tbUrl"));
	}catch(JSONException e){
	this.setFullUrl(null);
	this.setThumbUrl(null);
	}

}


	public String getFullUrl() {
		return fullUrl;
	}


	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}


	public String getThumbUrl() {
		return thumbUrl;
	}


	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	
	public String toString(){
		return this.thumbUrl;
		}


	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray imageJsonResults) {
		
		ArrayList<ImageResult> imgRes = new ArrayList<ImageResult>();
		
		for(int i=0; i<imageJsonResults.length();i++)
		{
			try {
				imgRes.add(new ImageResult(imageJsonResults.getJSONObject(i)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// TODO Auto-generated method stub
		return imgRes;
	}
	
	
	
}