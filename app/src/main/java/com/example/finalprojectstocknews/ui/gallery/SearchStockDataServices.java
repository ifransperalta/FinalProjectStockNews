package com.example.finalprojectstocknews.ui.gallery;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchStockDataServices
 * This class is the main driver of data building. It will connect to https api to get a json format data.
 * In this section to process the data I am using a built in library called Volley.
 *
 * @author Ian Peralta
 *
 */

public class SearchStockDataServices {
    private static final String QUERY_FOR_NAME_ID = "https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=";

    Context context;
    String result;

    public SearchStockDataServices(Context context) {
        this.context = context;
    }

    // Callback
    public interface VolleyResponseListener {
        void onError(String message);
        void onReponse(String response);
    }

    // Callback
    public interface SearchByIdResponse {
        void onError(String message);
        void onReponse(List<SearchReportModel> weatherReportModel);
    }

    /**
     * A callback function that will retrieve data from API
     *
     */
    public void searchNewsResult(String stockTicker, SearchByIdResponse searchByIdResponse) {

        List<SearchReportModel> searchReportModels = new ArrayList<>();
        String url = QUERY_FOR_NAME_ID + stockTicker;
        //Toast.makeText(context, url, Toast.LENGTH_SHORT).show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(context, "Return Value " + response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONObject stockNewsList = response.getJSONObject("response");
                    JSONArray results = stockNewsList.getJSONArray("results");

                    for(int i=0; i < results.length(); i++) {
                        SearchReportModel one_news = new SearchReportModel();
                        JSONObject stockNews = (JSONObject) results.getJSONObject(i);
                        one_news.setType(stockNews.getString("type"));
                        one_news.setSectionId(stockNews.getString("sectionId"));
                        one_news.setSectionName(stockNews.getString("sectionName"));
                        one_news.setWebPublicationDate(stockNews.getString("webPublicationDate"));
                        one_news.setWebTitle(stockNews.getString("webTitle"));
                        one_news.setWebUrl(stockNews.getString("webUrl"));
                        one_news.setApiUrl(stockNews.getString("apiUrl"));
                        one_news.setHosted(stockNews.getBoolean("isHosted"));
                        one_news.setPillarId(stockNews.getString("pillarId"));
                        one_news.setPillarName(stockNews.getString("pillarName"));
                        searchReportModels.add(one_news);
                    }
                    searchByIdResponse.onReponse(searchReportModels);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { }
        });
        SearchSingleton.getInstance(context).addToRequestQueue(request);
    }
}


