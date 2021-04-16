package com.example.finalprojectstocknews.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalprojectstocknews.DatabaseOP;
import com.example.finalprojectstocknews.R;
import com.example.finalprojectstocknews.TickerModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    ArrayAdapter stockNews;
    ListView stockNewsList;

    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View home = inflater.inflate(R.layout.fragment_home, container, false);

        stockNewsList = (ListView) home.findViewById(R.id.stockNews);
        return home;
    }


    private void getStockNews(DatabaseOP databaseop2) {
        stockNews = new ArrayAdapter<TickerModel>(getActivity(), android.R.layout.simple_list_item_1, databaseop2.getEveryone());
        stockNewsList.setAdapter(stockNews);
    }

}