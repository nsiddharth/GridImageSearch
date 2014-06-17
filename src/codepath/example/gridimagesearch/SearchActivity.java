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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

public class SearchActivity extends Activity {

	
	
	private EditText etSearch;
	private GridView gvImages;
	private Button btnSearch;
	ArrayList<ImageResult> imageList;
	ImageResultArrayAdapter imageAdapter ;

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
		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+ "start="+ (page*8) +"&v=1.0&q=" + Uri.encode(searchTerm), 
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
}
