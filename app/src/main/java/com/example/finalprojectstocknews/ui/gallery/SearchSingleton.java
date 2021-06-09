package com.example.finalprojectstocknews.ui.gallery;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * SearchSingleton
 *
 * @author Ian Peralta
 *
 */

public class SearchSingleton {
    private static SearchSingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private SearchSingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized SearchSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new SearchSingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
