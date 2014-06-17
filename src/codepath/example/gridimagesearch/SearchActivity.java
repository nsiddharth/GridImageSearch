package codepath.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

public class SearchActivity extends Activity {	
	
	private EditText etSearch;
	private GridView gvImages;
	private Button btnSearch;
	ArrayList<ImageResult> imageList;
	ImageResultArrayAdapter imageAdapter ;

	UserPreference userPref;
	String imgtype="";
	String as_sitesearch="";
	String imgcolor="";
	String imgsz="";
	int requestCode = 200;
	

	private static final String USER_PREFERENCE = "user_preference";

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setUpViews();
		imageList = new ArrayList<ImageResult>();
		imageAdapter = new ImageResultArrayAdapter(this, imageList);
		gvImages.setAdapter(imageAdapter);
		gvImages.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//Intent i = new Intent(getApplicationContext(),ImageDisplayActivity.class);
				ImageResult imageResult = imageList.get(position);
				loadPhoto(view, 100, 100, imageResult);
				//i.putExtra("result", imageResult);
				//startActivity(i);
			}
		});
		
		gvImages.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				
				loadMoreImages(page);
			}
		});
	}
	
	
	private void setUpViews(){
		etSearch=(EditText)findViewById(R.id.etSearch);
		gvImages=(GridView)findViewById(R.id.imageGrid);
		btnSearch=(Button)findViewById(R.id.searchButton);
		
		
		}
	
	public void searchImages(View v){
		
		imageAdapter.clear();
		loadMoreImages(0);
	}
	
	public void loadMoreImages(int page){
		
String searchTerm = etSearch.getText().toString();
		
		AsyncHttpClient client = new AsyncHttpClient();
		StringBuilder url =  new StringBuilder();
		url.append("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=" + (page*8) + "&v=1.0&q=" + Uri.encode(searchTerm));
		if(imgtype.length()>0)
			url.append("&imgtype=" + imgtype);
		if(as_sitesearch.length()>0)
			url.append("&as_sitesearch="+ as_sitesearch);
		if(imgcolor.length()>0)
			url.append("&imgcolor="+ imgcolor);
		if(imgsz.length()>0)
			url.append("&imgsz="+ imgsz);
		
		
		client.get(url.toString(), 
				new JsonHttpResponseHandler(){
			
			
			@Override
			public void onSuccess(JSONObject response) {
				// TODO Auto-generated method stub
				JSONArray imageJsonResults =null;
				
				try{
					
					imageJsonResults=response.getJSONObject("responseData").getJSONArray("results");
					//imageList.clear();
					imageList.addAll(ImageResult.fromJSONArray(imageJsonResults));
					imageAdapter.addAll(imageList);	
					Log.d("INFO", imageList.toString());
					
				}
				catch(JSONException e){
					
					e.printStackTrace();
					
				}
				
				
				
			}
			
			
		});
				
		
		
	}
	
	
	
	private void loadPhoto(View view, int width, int height, ImageResult imageResult) {

		


        AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.custom_image_dialog,
                (ViewGroup) findViewById(R.id.layout_root));
        SmartImageView image = (SmartImageView) layout.findViewById(R.id.ivResult2);
        image.setImageUrl(imageResult.getFullUrl());
        image.setImageDrawable(image.getDrawable());
        imageDialog.setView(layout);
        imageDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });


        imageDialog.create();
        imageDialog.show();     
    }
	
	
	
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.layout.menu_preferences, menu);
      return true;
  }


	public void onPreferences(MenuItem mi){
		//Toast.makeText(this, "Preferences!", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, PreferencesActivity.class);
		//get User Preferences

		startActivityForResult(intent, requestCode);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Toast.makeText(getApplicationContext(), "GotActivityResult", Toast.LENGTH_LONG).show();
		if (resultCode == RESULT_OK && requestCode == 200) {
			userPref = (UserPreference)data.getSerializableExtra(USER_PREFERENCE);
			if(userPref !=null){
				imgtype = userPref.getType();
				as_sitesearch = userPref.getSiteFilter();
				imgcolor = userPref.getColorFilter();
				imgsz = userPref.getSize();
				//Toast.makeText(getApplicationContext(), imgsz, Toast.LENGTH_LONG).show();
			}

		}



	}
	
	
}
