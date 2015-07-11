package com.gautam21.ebay_search;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.gautam21.ebay_search.com.ajax.customAdapter;
import org.json.JSONObject;
import com.gautam21.ebay_search.com.ajax.items;
import java.util.ArrayList;

public class DisplayEbayResult extends ListActivity {


    ArrayList<items> itemList = new ArrayList<items>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ebay_result);
        try {
            Intent intent = getIntent();
            String JSON = intent.getStringExtra("res");
            String kw = intent.getStringExtra("keyword");
            JSONObject json = new JSONObject(JSON);
            //items item = new items(json.getString("item0"));
            TextView v = (TextView) findViewById(R.id.result_i);
            v.setText("Results for '"+kw+"'");
            for(int i=0;i<5;i++)
            {
                itemList.add(new items(json.getString("item"+i)));
            }
            customAdapter listViewAdapter = new customAdapter(this, itemList);
            setListAdapter(listViewAdapter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_ebay_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

