/**
 * Copyright (c) 2021, Android Final Project
 *
*/

package com.example.finalprojectstocknews.ui.gallery;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalprojectstocknews.DatabaseOP;
import com.example.finalprojectstocknews.R;
import com.example.finalprojectstocknews.TickerModel;

import java.util.List;

/**
 * This class contains the main frament loader for stock news search
 *
 * @author Ian Peralta
 *
*/

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    Button getStockTicker;
    EditText tickerInput;
    ListView searchTickerNewsList;
    //ArrayAdapter tickerSearchNewsAdapter;

    /**
     *  This sections loads the app for stock news search
     *  * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        getStockTicker = (Button) view.findViewById(R.id.btn_getStockTicker); // button add
        tickerInput = (EditText) view.findViewById(R.id.tickerInput);
        searchTickerNewsList = (ListView) view.findViewById(R.id.searchTickerNewsList);

        final  SearchStockDataServices searchDataService = new SearchStockDataServices(getActivity());

        getStockTicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Toast.makeText(getActivity(), tickerInput.getText().toString(), Toast.LENGTH_SHORT).show();
                searchDataService.searchNewsResult(tickerInput.getText().toString(), new SearchStockDataServices.SearchByIdResponse() {
                    @Override
                    public void onError(String error) {
                        Toast.makeText(getActivity(), "Return Value " + error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReponse(List<SearchReportModel> searchReportModel) {
                        //Toast.makeText(getActivity(), searchReportModel.toString(), Toast.LENGTH_SHORT).show();
                        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, searchReportModel);
                        searchTickerNewsList.setAdapter(arrayAdapter); //  this will load the array data
                    }
                });
            }
        });
        return view;
    }


}