package com.gautam21.ebay_search;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import com.gautam21.ebay_search.com.ajax.requestAjax;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = (Spinner) findViewById(R.id.sort_i);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sort_val, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void clearFormData(View view){
        EditText key = (EditText) findViewById(R.id.keyword_i);
        key.setText("");
        EditText price_from = (EditText) findViewById(R.id.price_from_i);
        price_from.setText("");
        EditText price_to = (EditText) findViewById(R.id.price_to_i);
        price_to.setText("");
        Spinner mySpinner=(Spinner) findViewById(R.id.sort_i);
        mySpinner.setSelection(0);
        TextView v = (TextView) findViewById(R.id.error_l);
        v.setText("");
    }

    public void validateForm(View view){
        //validate form
        int flag=0;
        int from_check=0;
        int to_check=0;
        double pr1=-1.0,pr2=-1.0;
        String p1=null,p2=null;
        EditText key = (EditText) findViewById(R.id.keyword_i);
        String keyword = key.getText().toString();
        EditText price_from = (EditText) findViewById(R.id.price_from_i);
        EditText price_to = (EditText) findViewById(R.id.price_to_i);
        TextView v = (TextView) findViewById(R.id.error_l);
        v.setTextColor(Color.RED);
        if (keyword.trim().length() == 0)
        {
            v.setText("Please enter a keyword");
            flag=1;
        }
        else
        {
            p1 = price_from.getText().toString();
            p2= price_to.getText().toString();
            if (!(p1.trim().length()==0 )) {
                try {
                    pr1 = Double.parseDouble(price_from.getText().toString());
                    if (pr1 < 0)
                    {
                        v.setText("Price From:Must be a positive integer or decimal number");
                        flag = 1;
                        from_check = 1;
                    }
                } catch (NumberFormatException nfe) {
                    v.setText("Price From:Must be a positive integer or decimal number");
                    flag = 1;
                    from_check = 1;
                }
            }
            if (!(p2.trim().length()==0) && from_check==0) {
                try {
                    pr2 = Double.parseDouble(price_to.getText().toString());
                    if (pr2<0)
                    {
                        v.setText("Price To:Must be a positive integer or decimal number");
                        flag = 1;
                        to_check = 1;
                    }
                } catch (NumberFormatException nfe) {
                    v.setText("Price To:Must be a positive integer or decimal number");
                    flag = 1;
                    to_check = 1;
                }
            }
            if (from_check == 0 && to_check == 0)
            {
                if(pr1>pr2 && !(p2.trim().length()==0) && !(p1.trim().length()==0 ))
                {
                    v.setText("Price To must not be less than Price From");
                    flag = 1;
                }
            }

        }

       // String JSON="";
        Intent intent = new Intent(this, DisplayEbayResult.class);
        //Code to get input data
        if(flag==0) {
            v.setText("");
            //double p2 = Double.parseDouble(price_to.getText().toString());
            Spinner mySpinner = (Spinner) findViewById(R.id.sort_i);
            String sort_by = mySpinner.getSelectedItem().toString();
            //Ajax Call to AWS
            try {
                 String JSON = new requestAjax().execute(keyword, p1, p2, sort_by).get();
                //Convert to JSON and Check
                JSONObject json = new JSONObject(JSON);
                String res = json.getString("ack");
                if (!(res.equalsIgnoreCase("Success"))){
                    v.setTextColor(Color.BLACK);
                    v.setText("No Results Found");
                }
                else
                {
                    //start intent
                    intent.putExtra("res", JSON);
                    intent.putExtra("keyword", keyword);
                    startActivity(intent);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
