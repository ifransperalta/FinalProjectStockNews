package com.example.finalprojectstocknews.ui.home;

public class StockNewsModel {

    private String ticker;
    private String newsData;
    //private String newsUrl;
    //private String webPublicationDate;
    //private String sectionName;

    public StockNewsModel(String ticker, String newsData) {
        this.ticker = ticker;
        this.newsData = newsData;
        //this.newsUrl = newsUrl;
        //this.webPublicationDate = webPublicationDate;
        //this.sectionName = sectionName;
    }

    @Override
    public String toString() {
        /*return "StockNews{" +
                "ticker='" + ticker + '\'' +
                ", newsData='" + newsData + '\'' +
                '}';*/
        return ticker;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getNewsTitle() {
        return newsData;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsData = newsTitle;
    }
}
