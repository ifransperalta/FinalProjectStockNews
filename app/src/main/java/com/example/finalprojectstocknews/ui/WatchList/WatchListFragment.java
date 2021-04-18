

package com.example.finalprojectstocknews.ui.WatchList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

/**
 * This class contains fragment to save watchlist
 *
 * @author Ian Peralta
 *
 */

public class WatchListFragment extends Fragment implements View.OnClickListener{

    Button btAddTicker;
    EditText et_name;
    ListView tickerList;
    ArrayAdapter tickerArrayAdapter;
    DatabaseOP databaseop;

    private WatchlistshowViewModel watchlisthowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        watchlisthowViewModel = new ViewModelProvider(this).get(WatchlistshowViewModel.class);
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        btAddTicker = (Button) view.findViewById(R.id.btAddTicker); // button add
        btAddTicker.setOnClickListener(this);
        et_name = (EditText) view.findViewById(R.id.et_name);
        tickerList = (ListView) view.findViewById(R.id.lv_customerList);

        databaseop = new DatabaseOP( getActivity());
        getDataList(databaseop);

        tickerList.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Delete Ticker");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        TickerModel clickedItem = (TickerModel) parent.getItemAtPosition(position);
                        databaseop.DeleteEntry(clickedItem);
                        getDataList(databaseop);
                        Toast.makeText(getActivity(), "Deleted " + clickedItem, Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btAddTicker:
                TickerModel tickerData = new TickerModel( 1, et_name.getText().toString());
                Toast.makeText( getActivity(), "New Ticker Added To Watchlist: " + et_name.getText().toString(), Toast.LENGTH_SHORT).show();
                DatabaseOP databaseop = new DatabaseOP( getActivity());
                boolean success = databaseop.addOne(tickerData);
                getDataList(databaseop);
                et_name.setText("");
            break;
        }
    }

    public void onItem(){ }

    private void getDataList(DatabaseOP databaseop2) {
        tickerArrayAdapter = new ArrayAdapter<TickerModel>(getActivity(), android.R.layout.simple_list_item_1, databaseop2.getEveryone());
        tickerList.setAdapter(tickerArrayAdapter);
    }
}