package com.example.finalprojectstocknews.ui.slideshow;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalprojectstocknews.NewsWindow;
import com.example.finalprojectstocknews.R;
import com.example.finalprojectstocknews.ui.gallery.SearchReportModel;
import com.example.finalprojectstocknews.ui.gallery.SearchStockDataServices;
import com.example.finalprojectstocknews.ui.home.NewWindowModel;

import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private NewWindowModel newsWindow;

    ListView stocksRelatedNews;
    Context context;
    String ticker = "stock%20market%20news";
    String data;

    private ProgressBar progress;

    ListView stockNews;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);


        stocksRelatedNews = (ListView) root.findViewById(R.id.stocksRelatedNews);
        progress = (ProgressBar) root.findViewById(R.id.myprogressbar);
        progress.setVisibility(View.VISIBLE);
        progress.setProgress(0);

        NewsWindowAsync query = new NewsWindowAsync();
        query.execute();

        return root;
    }

    private class NewsWindowAsync extends AsyncTask<String, Integer, String> {

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
            final SearchStockDataServices searchDataService = new SearchStockDataServices(getActivity());

            searchDataService.searchNewsResult(ticker, new SearchStockDataServices.SearchByIdResponse() {
                @Override
                public void onError(String error) {
                    Toast.makeText(getActivity(), "Return Value " + error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onReponse(List<SearchReportModel> searchReportModel) {
                    //Toast.makeText(getActivity(), searchReportModel.toString(), Toast.LENGTH_SHORT).show();
                    ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, searchReportModel);
                    stocksRelatedNews.setAdapter(arrayAdapter); //  this will load the array data
                }
            });
        }
    }
}