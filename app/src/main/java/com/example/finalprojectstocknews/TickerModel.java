package com.example.finalprojectstocknews;

public class TickerModel {

    private int id;
    private String ticker;

    // constructor

    public TickerModel(int id, String watchlist) {
        this.id = id;
        this.ticker = watchlist;
    }

    @Override
    public String toString() {
        /*return "CustomerModel{" +
                "id=" + id +
                ", watchlist='" + ticker + '\'' +
                '}';*/
        return ticker;
    }

    // getter and setters

    public int getId() {
        return id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTicker(String watchlist) {
        this.ticker = watchlist;
    }
}