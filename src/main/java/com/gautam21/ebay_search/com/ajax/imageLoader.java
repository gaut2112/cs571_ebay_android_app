package com.gautam21.ebay_search.com.ajax;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by gautam21 on 4/21/2015.
 */
public class imageLoader extends AsyncTask<String, String , Bitmap> {


    protected Bitmap doInBackground(String... params)
    {

        Bitmap bitmap= null;
        try {
           bitmap = BitmapFactory.decodeStream((InputStream) new URL(params[0]).getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
 }

