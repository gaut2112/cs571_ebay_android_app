package com.gautam21.ebay_search.com.ajax;

import org.json.JSONObject;

/**
 * Created by gautam21 on 4/21/2015.
 */
public class shippinginfo {
    public String getType() {
        return type.replaceAll("(?!^)([A-Z])", " $1");
    }

    public String getHandleTime() {
        return handleTime;
    }

    public String getReturnAcc() {
        return returnAcc;
    }

    public String getOneDayShip() {
        return oneDayShip;
    }

    public String getExpediteShip() {
        return expediteShip;
    }

    public String getShipLoc() {
        return shipLoc;
    }

    String type;
    String shipLoc;
    String expediteShip;
    String oneDayShip;
    String returnAcc;
    String handleTime;

    public shippinginfo(String shippingstr)
    {
        try {
            JSONObject shipping = new JSONObject(shippingstr);
            type =shipping.getString("shippingType");
            shipLoc = shipping.getString("shipToLocations");
            expediteShip = shipping.getString("expeditedShipping");
            oneDayShip = shipping.getString("oneDayShippingAvailable");
            returnAcc = shipping.getString("returnsAccepted");
            handleTime = shipping.getString("handlingTime");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
