package com.example.finalprojectstocknews.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalprojectstocknews.DatabaseOP;
import com.example.finalprojectstocknews.NewsWindow;
import com.example.finalprojectstocknews.R;
import com.example.finalprojectstocknews.ui.gallery.SearchReportModel;
import com.example.finalprojectstocknews.ui.gallery.SearchStockDataServices;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    ListView stockNewsList;
    ArrayAdapter stockNews;
    DatabaseOP databaseop;

    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View home = inflater.inflate(R.layout.fragment_home, container, false);

        stockNewsList = (ListView) home.findViewById(R.id.stockNewsList);

        databaseop = new DatabaseOP( getActivity());
        getStockNews(databaseop);

        final SearchStockDataServices searchDataService = new SearchStockDataServices(getActivity());

        stockNewsList.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View home, int position, long id) {
                StockNewsModel clickedItem = (StockNewsModel) parent.getItemAtPosition(position);

                searchDataService.searchNewsResult(clickedItem.toString(), new SearchStockDataServices.SearchByIdResponse() {
                    @Override
                    public void onError(String error) {
                        Toast.makeText(getActivity(), "Return Value " + error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReponse(List<SearchReportModel> searchReportModel) {
                        Intent intent = new Intent( home.getContext(), NewsWindow.class);
                        intent.putExtra("ticker", clickedItem.toString());
                        startActivity(intent);
                        //Toast.makeText(getActivity(), clickedItem.toString(), Toast.LENGTH_SHORT).show();
                        //ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, searchReportModel);
                        //stockNewsList.setAdapter(arrayAdapter); //  this will load the array data
                    }
                });
            }
        });
        return home;
    }

    public void onItem(){ }

    private void getStockNews(DatabaseOP databaseop2) {
        stockNews = new ArrayAdapter<StockNewsModel>(getActivity(), android.R.layout.simple_list_item_1, databaseop2.getTickers());
        stockNewsList.setAdapter(stockNews);
    }
}