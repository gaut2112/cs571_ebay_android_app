package com.gautam21.ebay_search.com.ajax;

/**
 * Created by gautam21 on 4/21/2015.
 */

import com.gautam21.ebay_search.com.ajax.basicinfo;
import com.gautam21.ebay_search.com.ajax.sellerinfo;
import com.gautam21.ebay_search.com.ajax.shippinginfo;

import org.json.JSONObject;


public class items {
    public basicinfo getBasic() {
        return basic;
    }

    public sellerinfo getSeller() {
        return seller;
    }

    public shippinginfo getShip() {
        return ship;
    }

    basicinfo basic;
    sellerinfo seller;
    shippinginfo ship;

    public String getJson_compatible() {
        return json_compatible;
    }

    String json_compatible;
    public items(String itm)
    {
        try{
            JSONObject item = new JSONObject(itm);
            json_compatible = itm;
            basic = new basicinfo(item.getString("basicinfo"));
            seller = new sellerinfo(item.getString("sellerinfo"));
            ship = new shippinginfo(item.getString("shippinginfo")) ;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
