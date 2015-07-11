package com.gautam21.ebay_search.com.ajax;

import org.json.JSONObject;

/**
 * Created by gautam21 on 4/21/2015.
 */
public class sellerinfo {
    public String getUserName() {
        return userName;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getFeedbackPerc() {
        return feedbackPerc;
    }

    public String getFeedbackRating() {
        return feedbackRating;
    }

    public String getTopRatedSeller() {
        return topRatedSeller;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreURL() {
        return storeURL;
    }

    String userName;
    String feedback;
    String feedbackPerc;
    String feedbackRating;
    String topRatedSeller;
    String storeName;
    String storeURL;


    public sellerinfo(String sellerstr)
    {
        try {
            JSONObject seller = new JSONObject(sellerstr);
            userName = seller.getString("sellerUserName");
            feedback = seller.getString("feedbackScore");
            feedbackPerc = seller.getString("positiveFeedbackPercent");
            feedbackRating = seller.getString("feedbackRatingStar");
            topRatedSeller = seller.getString("topRatedSeller");
            storeName = seller.getString("sellerStoreName");
            storeURL = seller.getString("sellerStoreURL");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
