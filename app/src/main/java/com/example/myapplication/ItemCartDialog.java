package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemCartDialog extends AppCompatDialogFragment {

    PaymentSheet paymentSheet;
    String paymentIntentClientSecret ;
    PaymentSheet.CustomerConfiguration configuration;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.item_dialog, null);
        fetchAPI();
        TextView name = view.findViewById(R.id.textView11);
        TextView cat = view.findViewById(R.id.textView18);
        TextView price = view.findViewById(R.id.textView19);
        TextView dis = view.findViewById(R.id.textView20);
        ImageView iv = view.findViewById(R.id.imageView8);
        Button Buy = view.findViewById(R.id.button6);

        Buy.setText("Buy");
        Button back = view.findViewById(R.id.button7);
        back.setText("Remove");


        Bundle bundle = getArguments();

        assert bundle != null;
        String ItemName = bundle.getString("Name");
        String ItemCat = bundle.getString("Category");
        String ItemPrice = bundle.getString("Price");
        String ItemDis = bundle.getString("Dis");
        String ItemUrl = bundle.getString("Url");
        String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        String ItemKey = bundle.getString("Key");

        name.setText(ItemName);
        cat.setText(ItemCat);
        price.setText(ItemPrice);
        dis.setText(ItemDis);
        Picasso.get().load(ItemUrl).fit().centerCrop().into(iv);

        //Java-to-PHP

       /* OkHttpClient client = new OkHttpClient();

        String url = "http://183.171.184.4:8000/";

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{"
                + "\"Name\": \"" + ItemName + "\","
                + "\"Category\": \"" + ItemCat + "\","
                + "\"Price\": \"" + ItemPrice + "\","
                + "\"Dis\": \"" + ItemDis + "\","
                + "\"Url\": \"" + ItemUrl + "\","
                + "\"Key\": \"" + ItemKey + "\""
                + "}");

        okhttp3.Request okHttpRequest = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(okHttpRequest);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle error
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                // Handle response
            }
        });*/

        back.setOnClickListener(v -> {
            Log.d(TAG, "Removing value with key: " + ItemKey);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(user).child("Cart").child(ItemKey);
            reference.removeValue().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), Feed.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                }
            });
        });



        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymentIntentClientSecret != null) {
                    paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret,
                            new PaymentSheet.Configuration("Prototype Gateway", configuration));
                }else{
                    Toast.makeText(getContext(), "API is loading  .  .  . ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();

    }


    private  void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult){
        if(paymentSheetResult instanceof PaymentSheetResult.Canceled){
            Toast.makeText(getContext(), "Payment has been canceled", Toast.LENGTH_SHORT).show();
        }
        if(paymentSheetResult instanceof PaymentSheetResult.Failed){
            Toast.makeText(getContext(), ((PaymentSheetResult.Failed) paymentSheetResult).getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(paymentSheetResult instanceof PaymentSheetResult.Completed){
            Toast.makeText(getContext(), "Payment is complete", Toast.LENGTH_SHORT).show();
        }
    }


    public void fetchAPI(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url ="http://192.168.15.233:8000/stripe/";//replace with demo server if using local host use ipv4

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //System.out.println("Response is "+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            configuration = new PaymentSheet.CustomerConfiguration(
                                    jsonObject.getString("customer"),
                                    jsonObject.getString("ephemeralKey")
                            );
                            paymentIntentClientSecret = jsonObject.getString("paymentIntent");
                            PaymentConfiguration.init(getActivity().getApplicationContext(),
                                    jsonObject.getString("publishableKey"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error response: " + error.getMessage());
                error.printStackTrace();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String,String> paramV = new HashMap<>();
                paramV.put("authKey", "abc");
                System.out.println(paramV);
                System.out.println(paymentIntentClientSecret);
                return paramV;
            }


        };
        Log.d(TAG, "Sending request...");
        queue.add(stringRequest);
        Log.d(TAG, "Request sent.");

    }

}
