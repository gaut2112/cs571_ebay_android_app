package com.gautam21.ebay_search.com.ajax;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by gautam21 on 4/21/2015.
 */
public class requestAjax extends AsyncTask<String, Integer, String>{

    private String URL = "http://gautambhcs571hw8-env.elasticbeanstalk.com/?k_input=";
    private StringBuffer response;
    protected String doInBackground(String... params)
    {
        response= new StringBuffer();
        try {
        String keyword = URLEncoder.encode(params[0],"UTF-8");
        String p1 = params[1];
        String p2 = params[2];
        String sort1 = params[3];
        String sort="";
        if (sort1.equals("Best Match"))
            {
            sort="BestMatch";
            }
        if (sort1.equals("Price: highest first"))
            {
                sort="CurrentPriceHighest";
            }
        if (sort1.equals("Price + Shipping: highest first"))
            {
                sort="PricePlusShippingHighest";
            }
        if (sort1.equals("Price + Shipping: lowest first"))
            {
                sort="PricePlusShippingLowest";
            }
        URL += keyword+"&p1="+p1+"&p2="+p2+"&SORT="+sort+"&Page_Entry=5&pageNo=1";
        URL url = new URL(URL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("User-Agent","Mozilla/5.0");
        //urlConnection.connect();
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        response.append(responseReader.readLine());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
            return response.toString();
    }
}
