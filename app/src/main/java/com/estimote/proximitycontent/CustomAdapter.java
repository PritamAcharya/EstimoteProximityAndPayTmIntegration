package com.estimote.proximitycontent;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

/**
 * Created by Anurag145 on 10/1/2016.
 */

public class CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static Context context;
    private String MY_JSON;
    int n=0;
    public static String JSON_ARRAY="data";
     Bitmap bitmap1[];
    int l=0;
    public static final String Link="link";
    public static  final String Contact_Person="contact_person";
    private JSONArray users = null;
    private static int randomInt = 0;
    private static PaytmPGService Service = null;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
         String s;
        final TextView textView3;
        final TextView textView2;
        final ImageView imageView;

        public ViewHolder(View v) {
            super(v);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            textView3=(TextView)v.findViewById(R.id.time) ;
            textView=(TextView)v.findViewById(R.id.date);
            textView2=(TextView)v.findViewById(R.id.delivery);
            imageView=(ImageView)v.findViewById(R.id.photomy);


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog loading = ProgressDialog.show(context,"Uploading...","Please wait...",false,false);
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, ""+s,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {

                                    loading.dismiss();

                                    Toast.makeText(context, s , Toast.LENGTH_LONG).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                    loading.dismiss();


                                    System.out.println(volleyError.getMessage().toString());
                                    Toast.makeText(context, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {


                            Map<String,String> params = new Hashtable<String, String>();



                            params.put("id", s);


                            return params;
                        }
                    };

                    //Creating a Request Queue
                    RequestQueue requestQueue = Volley.newRequestQueue(context);

                    //Adding request to the queue
                    requestQueue.add(stringRequest);

                    Random randomGenerator = new Random();
                    randomInt = randomGenerator.nextInt(100000);

                    Service = PaytmPGService.getStagingService();

                    PaytmMerchant Merchant = new PaytmMerchant("", "");
                    Map<String, String> paramMap = new HashMap<String, String>();
                    paramMap.put("REQUEST_TYPE", "DEFAULT");
                    paramMap.put("ORDER_ID", "2HACK"+String.valueOf(randomInt));
                    //MID provided by paytm
                    paramMap.put("MID", "WorldP64425807474247");
                    paramMap.put("CUST_ID", "CUST123");
                    paramMap.put("CHANNEL_ID", "WAP");
                    paramMap.put("INDUSTRY_TYPE_ID", "Retail");
                    paramMap.put("WEBSITE", "worldpressplg");
                    paramMap.put("TXN_AMOUNT", "1");
                    paramMap.put("THEME", "merchant");
                    paramMap.put("CALLBACK_URL","");
                    PaytmOrder Order = new PaytmOrder(paramMap);
                    Service.initialize(Order, Merchant,null);


                    Service.startPaymentTransaction(context, false, false, new PaytmPaymentTransactionCallback() {

                        @Override
                        public void onTransactionSuccess(Bundle bundle) {
                            Log.e("STATUS","Transaction Success :" + bundle);
                            final ProgressDialog loading = ProgressDialog.show(context,"Uploading...","Please wait...",false,false);
                            StringRequest stringRequest = new StringRequest(Request.Method.PUT, ""+s,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String s) {

                                            loading.dismiss();

                                            Toast.makeText(context, s , Toast.LENGTH_LONG).show();
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {

                                            loading.dismiss();


                                            System.out.println(volleyError.getMessage().toString());
                                            Toast.makeText(context, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {


                                    Map<String,String> params = new Hashtable<String, String>();



                                    params.put("id", s);


                                    return params;
                                }
                            };

                            //Creating a Request Queue
                            RequestQueue requestQueue = Volley.newRequestQueue(context);

                            //Adding request to the queue
                            requestQueue.add(stringRequest);

                        }

                        @Override
                        public void onTransactionFailure(String s, Bundle bundle) {
                            Log.e("STATUS","Transaction Failure :" + s + "\n" + bundle);
                        }

                        @Override
                        public void networkNotAvailable() {
                            Log.e("STATUS","network unavailable :");
                        }

                        @Override
                        public void clientAuthenticationFailed(String s) {
                            Log.e("STATUS","clientAuthenticationFailed :" + s);
                        }

                        @Override
                        public void someUIErrorOccurred(String s) {
                            Log.e("STATUS","someUIErrorOccurred :" + s);
                        }

                        @Override
                        public void onErrorLoadingWebPage(int i, String s, String s2) {
                            Log.e("STATUS","errorLoadingWebPage :" + i + "\n" + s + "\n" + s2);
                        }
                    });



                }
            });
        }

    }


    public CustomAdapter(Context context, String s) {
     this.context=context;
      this. MY_JSON=s;

        try {
            JSONObject jsonObject = new JSONObject(MY_JSON);
            users = jsonObject.getJSONArray(JSON_ARRAY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        l=users.length();
        bitmap1= new Bitmap[l];
        someMethod();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_resource, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
       try {
           viewHolder.s=users.getJSONObject(position).getString("id");
           viewHolder.textView3.setText(users.getJSONObject(position).getString(Contact_Person));
           viewHolder.textView2.setText(users.getJSONObject(position).getString("price"));
           viewHolder.textView.setText(users.getJSONObject(position).getString("address"));
           viewHolder.imageView.setImageBitmap(bitmap1[position]);
       }catch (JSONException e)
       {
           e.printStackTrace();
       }



    }

    @Override
    public int getItemCount() {
        return l;
    }
    private Target target = new Target() {

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
          bitmap1[n]=bitmap;
            n++;
            if(n<l)
            someMethod();

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    private void someMethod() {
        try {
            Picasso.with(context).load(users.getJSONObject(n).getString(Link)).into(target);
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}