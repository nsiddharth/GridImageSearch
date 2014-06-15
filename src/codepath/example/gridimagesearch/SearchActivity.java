package codepath.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class SearchActivity extends Activity {

	
	
	private EditText etSearch;
	private GridView gvImages;
	private Button btnSearch;
	ArrayList<ImageResult> imageList = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setUpViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageList);
		gvImages.setAdapter(imageAdapter);
	}
	
	
	private void setUpViews(){
		etSearch=(EditText)findViewById(R.id.etSearch);
		gvImages=(GridView)findViewById(R.id.imageGrid);
		btnSearch=(Button)findViewById(R.id.searchButton);
		
		
		}
	
	public void searchImages(View v){
		
		String searchTerm = etSearch.getText().toString();
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+ "start="+ 0 +"&v=1.0&q=" + Uri.encode(searchTerm), 
				new JsonHttpResponseHandler(){
			
			
			@Override
			public void onSuccess(JSONObject response) {
				// TODO Auto-generated method stub
				JSONArray imageJsonResults =null;
				
				try{
					
					imageJsonResults=response.getJSONObject("responseData").getJSONArray("results");
					imageList.clear();
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
	
}
