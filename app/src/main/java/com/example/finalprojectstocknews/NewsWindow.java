package com.example.finalprojectstocknews;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalprojectstocknews.R;
import com.example.finalprojectstocknews.ui.gallery.SearchReportModel;
import com.example.finalprojectstocknews.ui.gallery.SearchStockDataServices;
import com.example.finalprojectstocknews.ui.home.NewWindowModel;
import com.example.finalprojectstocknews.ui.home.StockNewsModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NewsWindow extends AppCompatActivity {

    private NewWindowModel newsWindow;

    ListView theStockNewsList;
    Context context;
    String ticker;
    String data;

    ListView stockNews;

    private ProgressBar progress;
    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_window);
        ticker = (String) getIntent().getSerializableExtra("ticker");

        progress = findViewById(R.id.myprogressbar);
        progress.setVisibility(View.VISIBLE);
        progress.setProgress(0);

        setTitle("Latest " + ticker + " Stock News");

        NewsWindowAsync query = new NewsWindowAsync();
        query.execute();
    }

    public class NewsWindowAsync extends AsyncTask<String, Integer, String> {

        int count =1;

        public String doInBackground(String ... args){
            for (; count <= 100; count++) {
                try {
                    Thread.sleep(10);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "Complete";
        }

        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0]);
        }

        public void onPostExecute(String fromDoInBackground)
        {
            progress.setVisibility(View.GONE);
            final SearchStockDataServices searchDataService = new SearchStockDataServices(NewsWindow.this);
            searchDataService.searchNewsResult(ticker, new SearchStockDataServices.SearchByIdResponse() {
                @Override
                public void onError(String error) {
                    Toast.makeText(NewsWindow.this, "Return Value " + error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onReponse(List<SearchReportModel> searchReportModel) {
                    //stockNews = (ListView) searchReportModel;

                    theStockNewsList = (ListView) findViewById(R.id.theStockNewsList);
                    //Toast.makeText(NewsWindow.this, searchReportModel.toString(), Toast.LENGTH_SHORT).show();
                    ArrayAdapter arrayAdapter = new ArrayAdapter( NewsWindow.this, android.R.layout.simple_list_item_1, searchReportModel);
                    theStockNewsList.setAdapter(arrayAdapter);
                }
            });
        }
    }
}