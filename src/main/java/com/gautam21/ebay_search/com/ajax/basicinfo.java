package com.gautam21.ebay_search.com.ajax;

import org.json.JSONObject;

/**
 * Created by gautam21 on 4/21/2015.
 */
public class basicinfo {
    public String getTitle() {
        return title;
    }

    public String getItemURL() {
        return itemURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getImgSuperURL() {
        return imgSuperURL;
    }

    public String getPrice() {
        return price;
    }

    public String getShipping() {
        return shipping;
    }

    public String getCondition() {
        return condition;
    }

    public String getListing() {
        return listing;
    }

    public String getLocation() {
        return location;
    }

    public String getTopRated() {
        return topRated;
    }

    public String getCategory() {
        return category;
    }

    String title;
    String itemURL;
    String imgURL;
    String imgSuperURL;
    String price;
    String shipping;
    String condition;
    String listing;
    String location;
    String topRated;
    String category;

    public basicinfo(String basicstr)
    {
        try {
            JSONObject basic = new JSONObject(basicstr);
            title = basic.getString("title");
            itemURL = basic.getString("viewItemURL");
            imgURL =basic.getString("galleryURL");
            imgSuperURL =basic.getString("pictureURLSuperSize");
            price = basic.getString("convertedCurrentPrice");
            shipping =basic.getString("shippingServiceCost");
            condition =basic.getString("conditionDisplayName");
            listing = basic.getString("listingType");
            location =basic.getString("location");
            topRated =basic.getString("topRatedListing");
            category = basic.getString("categoryName");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
