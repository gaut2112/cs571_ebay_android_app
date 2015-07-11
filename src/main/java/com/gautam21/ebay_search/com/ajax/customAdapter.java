package com.gautam21.ebay_search.com.ajax;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gautam21.ebay_search.DetailsActivity;
import com.gautam21.ebay_search.com.ajax.imageLoader;

import com.gautam21.ebay_search.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by gautam21 on 4/22/2015.
 */
public class customAdapter extends ArrayAdapter<items> {
    private LayoutInflater inflater;
    private final Context context;
    ArrayList <items> itemList ;

    public customAdapter(Context c, ArrayList<items> itemList)
    {
        super(c, R.layout.listviewrow, itemList);
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemList = itemList;
        this.context = c;

    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent){
        Bitmap bitmap=null;
        View view = inflater.inflate(R.layout.listviewrow, parent, false);
        try {

            TextView title = (TextView) view.findViewById(R.id.title_i);
            title.setText(itemList.get(position).getBasic().getTitle());
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("item",itemList.get(position).getJson_compatible());
                    context.startActivity(intent);
                }
            });
            TextView price = (TextView) view.findViewById(R.id.price_i);
            String shipping = "";
            if (itemList.get(position).getBasic().getShipping().equals("0.0")||itemList.get(position).getBasic().getShipping().equals("0")||itemList.get(position).getBasic().getShipping().equals(""))
            {
                shipping="(FREE Shipping)";
            }
            else
            {
                shipping="(+"+itemList.get(position).getBasic().getShipping()+" Shipping)";
            }
            price.setText("Price: $"+itemList.get(position).getBasic().getPrice()+" "+shipping);
            imageLoader img = new imageLoader();
            bitmap = img.execute(itemList.get(position).getBasic().getImgURL()).get();
            ImageButton imgBtn = (ImageButton) view.findViewById(R.id.item_image);
            imgBtn.setImageBitmap(bitmap);
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri redirect = Uri.parse(itemList.get(position).getBasic().getItemURL());
                    Intent browser = new Intent(Intent.ACTION_VIEW,redirect);
                    context.startActivity(browser);
                }
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return view;

    }
}
