package com.gautam21.ebay_search;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.gautam21.ebay_search.com.ajax.imageLoader;
import com.gautam21.ebay_search.com.ajax.items;

import org.w3c.dom.Text;


public class DetailsActivity extends ActionBarActivity {
    Bitmap bitmap =null;
    String imageUrl="";
    String shipping ="";
    private String fb_message="";
    ShareDialog shareDialog;
    CallbackManager callbackManager;
    items item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        FacebookSdk.sdkInitialize(getApplicationContext());

        try{
            Intent intent = getIntent();
            String JSON = intent.getStringExtra("item");
            item = new items(JSON);
            ImageView imgV = (ImageView) findViewById(R.id.itemImage);
            imageLoader img = new imageLoader();
            if (item.getBasic().getImgSuperURL().equals("NA") || item.getBasic().getImgSuperURL().equals(""))
            {
                imageUrl = item.getBasic().getImgURL();
            }
            else
            {
                imageUrl = item.getBasic().getImgSuperURL();
            }
            bitmap = img.execute(imageUrl).get();
            imgV.setImageBitmap(bitmap);
            TextView title = (TextView) findViewById(R.id.titleDetail);
            title.setText(item.getBasic().getTitle());
            TextView price = (TextView) findViewById(R.id.priceDetails);
            if (item.getBasic().getShipping().equals("0.0")||item.getBasic().getShipping().equals("0")||item.getBasic().getShipping().equals(""))
            {
                shipping="(FREE Shipping)";
            }
            else
            {
                shipping="(+"+item.getBasic().getShipping()+" Shipping)";
            }
            price.setText("Price: $"+item.getBasic().getPrice()+" "+shipping);
            TextView loc = (TextView) findViewById(R.id.locDetails);
            loc.setText(item.getBasic().getLocation());
            Button buyNow = (Button) findViewById(R.id.buyNow);
            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri redirect = Uri.parse(item.getBasic().getItemURL());
                    Intent browser = new Intent(Intent.ACTION_VIEW,redirect);
                    startActivity(browser);
                }
            });

            if (item.getBasic().getTopRated().equals("true"))
            {
                ImageView imgTop = (ImageView) findViewById(R.id.topRated);
                Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.toprated);
                imgTop.setImageBitmap(b);
            }
             fb_message ="Price: $"+item.getBasic().getPrice();
            if (item.getBasic().getShipping().equals("NA")||item.getBasic().getShipping().equals("")||item.getBasic().getShipping().equals("0.0")||item.getBasic().getShipping().equals("0"))
            {
                fb_message +=" (FREE Shiping) Location"+item.getBasic().getLocation();
            }
            else
            {
                fb_message += " (+ "+item.getBasic().getShipping()+" Shipping) Location:"+item.getBasic().getLocation();
            }
            ImageButton imgBtn =  (ImageButton) findViewById(R.id.facebook);
            shareDialog = new ShareDialog(this);
            callbackManager = CallbackManager.Factory.create();
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        try {
                            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                    .setContentTitle(item.getBasic().getTitle())
                                    .setContentDescription(fb_message)
                                    .setContentUrl(Uri.parse(item.getBasic().getItemURL()))
                                    .setImageUrl(Uri.parse(item.getBasic().getImgURL()))
                                    .build();
                            shareDialog.show(linkContent);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            });
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    if(result.getPostId() != null) {
                        Toast.makeText(getApplicationContext(), "Posted Succesfully,ID:" + result.getPostId(), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Posted Cancelled", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getApplicationContext(),"Post Cancelled",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onError(FacebookException e) {
                    Toast.makeText(getApplicationContext(),"Error in Posting",Toast.LENGTH_LONG).show();

                }
            });
            final Button button2 =(Button) findViewById(R.id.button);
            final Button button = (Button) findViewById(R.id.button2);
            final Button button3 = (Button) findViewById(R.id.button3);
            button.setBackgroundColor(Color.DKGRAY);
            button2.setBackgroundColor(Color.LTGRAY);
            button3.setBackgroundColor(Color.LTGRAY);
            TextView b1 = (TextView) findViewById(R.id.category_i);
            b1.setText(item.getBasic().getCategory());
            TextView b2 = (TextView) findViewById(R.id.condition_i);
            b2.setText(item.getBasic().getCondition());
            TextView b3 = (TextView) findViewById(R.id.buying_i);
            String listing = item.getBasic().getListing();
            if (item.getBasic().getListing().equals("FixedPrice")||item.getBasic().getListing().equals("StoreInventory"))
            {
                listing ="Buy It Now";
            }
            else if (listing.equals("Classified"))
            {
                listing ="Classified Ads";
            }
            b3.setText(listing);
            TextView s1 = (TextView)findViewById(R.id.user_i);
            s1.setText(item.getSeller().getUserName());
            TextView s2 = (TextView)findViewById(R.id.feedbacks_i);
            s2.setText(item.getSeller().getFeedback());
            TextView s3 = (TextView)findViewById(R.id.feedbackp_i);
            s3.setText(item.getSeller().getFeedbackPerc());
            TextView s4 = (TextView)findViewById(R.id.feedbackr_i);
            s4.setText(item.getSeller().getFeedbackRating());
            TextView s5 = (TextView)findViewById(R.id.store_i);
            s5.setText(item.getSeller().getStoreName());
            ImageView s6 = (ImageView)findViewById(R.id.top_rated_i);
            if (item.getSeller().getTopRatedSeller().equals("true"))
            {
                s6.setImageResource(R.drawable.checkgreen);
            }
            else
            {
                s6.setImageResource(R.drawable.crossred);
            }
            TextView sh1 = (TextView)findViewById(R.id.ship_i);
            sh1.setText(item.getShip().getType());
            TextView sh2 = (TextView)findViewById(R.id.handling_i);
            sh2.setText(item.getShip().getHandleTime());
            TextView sh3 = (TextView)findViewById(R.id.location_i);
            sh3.setText(item.getShip().getShipLoc());
            ImageView sh4 = (ImageView)findViewById(R.id.expedite_i);
            if (item.getShip().getExpediteShip().equals("true"))
            {
                sh4.setImageResource(R.drawable.checkgreen);
            }
            else
            {
                sh4.setImageResource(R.drawable.crossred);
            }
            ImageView sh5 = (ImageView)findViewById(R.id.one_day_i);
            if (item.getShip().getOneDayShip().equals("true"))
            {
                sh5.setImageResource(R.drawable.checkgreen);
            }
            else
            {
                sh5.setImageResource(R.drawable.crossred);
            }
            ImageView sh6 = (ImageView)findViewById(R.id.returna_i);
            if (item.getShip().getReturnAcc().equals("true"))
            {
                sh6.setImageResource(R.drawable.checkgreen);
            }
            else
            {
                sh6.setImageResource(R.drawable.crossred);
            }

            final RelativeLayout basic =(RelativeLayout)findViewById(R.id.basic);
            final RelativeLayout seller = (RelativeLayout)findViewById(R.id.seller);
            final RelativeLayout shipping = (RelativeLayout) findViewById(R.id.shipping);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    basic.setVisibility(View.VISIBLE);
                    seller.setVisibility(View.INVISIBLE);
                    shipping.setVisibility(View.INVISIBLE);
                    button.setBackgroundColor(Color.DKGRAY);
                    button2.setBackgroundColor(Color.LTGRAY);
                    button3.setBackgroundColor(Color.LTGRAY);

                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    basic.setVisibility(View.INVISIBLE);
                    seller.setVisibility(View.VISIBLE);
                    shipping.setVisibility(View.INVISIBLE);
                    button2.setBackgroundColor(Color.DKGRAY);
                    button.setBackgroundColor(Color.LTGRAY);
                    button3.setBackgroundColor(Color.LTGRAY);

                }
            });
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    basic.setVisibility(View.INVISIBLE);
                    seller.setVisibility(View.INVISIBLE);
                    shipping.setVisibility(View.VISIBLE);
                    button3.setBackgroundColor(Color.DKGRAY);
                    button2.setBackgroundColor(Color.LTGRAY);
                    button.setBackgroundColor(Color.LTGRAY);

                }
            });


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
