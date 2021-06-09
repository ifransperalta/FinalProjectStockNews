package com.example.finalprojectstocknews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalprojectstocknews.ui.gallery.GalleryViewModel;
import com.example.finalprojectstocknews.ui.gallery.SearchReportModel;
import com.example.finalprojectstocknews.ui.gallery.SearchStockDataServices;
import com.example.finalprojectstocknews.ui.home.StockNewsModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class that will save watchlist to database
 *
 * @author Ian Peralta
 *
 */

public class DatabaseOP extends SQLiteOpenHelper{

    public static final String CUSTOMER_TABLE = "STOCKTICKER";
    public static final String TICKER = "TICKER";
    public static final String ID = "ID";

    //
    Context context;
    private static final String QUERY_FOR_NAME_ID = "https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=";
    String dataResult;


    public DatabaseOP(@Nullable Context context) {
        super(context, "ticker.db", null, 1);
    }

    // This section the create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TICKER + " TEXT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + CUSTOMER_TABLE );
        onCreate(db);
    }

    public boolean addOne(TickerModel customerModel){

        SQLiteDatabase db = this.getWritableDatabase(); // Writing
        ContentValues cv = new ContentValues();
        cv.put(TICKER, customerModel.getTicker());

        db.insert( CUSTOMER_TABLE, null, cv);
        return true;
    }

    public boolean DeleteEntry(TickerModel customerModel){

        SQLiteDatabase db = this.getWritableDatabase();
        String deleteData = "DELETE FROM " + CUSTOMER_TABLE  + " WHERE " + ID + " = " + customerModel.getId();

        Cursor cursor = db.rawQuery(deleteData, null);

        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    private TickerModel getCustomerModel(TickerModel tickerModel) {
        return tickerModel;
    }


    public List<TickerModel> getEveryone(){
        List<TickerModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + CUSTOMER_TABLE + " ORDER BY " +  ID + " DESC ";
        SQLiteDatabase db = this.getReadableDatabase();
        // result set
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            // loo through the cursor result
            do {
                int tickerID = cursor.getInt(0);
                String ticker = cursor.getString(1);

                TickerModel newCustomer = new TickerModel(tickerID, ticker);
                returnList.add(newCustomer);

            }while (cursor.moveToNext());

        }else{
            // failure . nothing
        }

        cursor.close();
        db.close();
        return returnList;
    }

    //experiment - This is working
    public List<StockNewsModel> getTickers(){
        List<StockNewsModel> stockNewsList = new ArrayList<>();

        String queryString = "SELECT * FROM " + CUSTOMER_TABLE + " ORDER BY " +  ID + " DESC ";
        SQLiteDatabase db = this.getReadableDatabase();
        // result set
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            // loo through the cursor result
            do {

                final  SearchStockDataServices searchDataService = new SearchStockDataServices(context);
                int tickerID = cursor.getInt(0);
                String ticker = cursor.getString(1);

                String url = QUERY_FOR_NAME_ID + ticker.toString();

                StockNewsModel stockNews = new StockNewsModel(ticker, url);
                stockNewsList.add(stockNews);

            }while (cursor.moveToNext());

        }else{
            // failure . nothing
        }

        cursor.close();
        db.close();
        return stockNewsList;
    }
}