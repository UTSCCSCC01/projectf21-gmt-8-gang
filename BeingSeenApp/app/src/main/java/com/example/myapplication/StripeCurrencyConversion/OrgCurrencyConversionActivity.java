package com.example.myapplication.StripeCurrencyConversion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.NavbarActivities.OrgMainNavbarActivity;
import com.example.myapplication.ProfileInfo;
import com.example.myapplication.R;
import com.example.myapplication.VolleyCallBack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrgCurrencyConversionActivity extends AppCompatActivity {

    private static final String BACKEND_URL = "http://10.0.2.2:8080/";
//    private static final String BACKEND_URL = "http://192.168.1.6:4242/";

    private String publishableKey;

    // The code below is not original and was
    // inspired by https://codingwithtashi.medium.com/stripe-payment-integration-with-android-4c588e78f3ea
    // and https://www.youtube.com/watch?v=DsWjws60Ss0

    EditText amountText;
    CardInputWidget cardInputWidget;
    Button payButton;

    // we need paymentIntentClientSecret to start transaction
    private String paymentIntentClientSecret;
    //declare stripe
    private Stripe stripe;

    Double amountDouble=null;

    private OkHttpClient httpClient;

    static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_currency_conversion);

//        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //old
//        fetchPublishableKey();
//        Log.e("pubKey", publishableKey);


        //new

        amountText = findViewById(R.id.amount_id);
        cardInputWidget = findViewById(R.id.cardInputWidget);
        payButton = findViewById(R.id.payButton);
        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Transaction in progress");
        progressDialog.setCancelable(true);

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Amount and card information field cannot be empty");

        // add the buttons
        builder.setNegativeButton("Cancel", null);


        httpClient = new OkHttpClient();

        //Initialize
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51JrgXOKkv3Y92s58N11ewVcHgzksho49BSOhYOaFfZ9m7E8wBgEO3vkyayLgDm24FkxSwx60JoVoEr2rQdXwb9ZF00zU0lljv9")
        );


        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.d("Cardinfo", cardInputWidget.getCardParams().toString());
                Log.d("Amtinfo", amountText.getText().toString());
                if(!amountText.getText().toString().equals("") && !cardInputWidget.toString().equals("")) {
                    //get Amount
                    amountDouble = Double.valueOf(amountText.getText().toString());
                    //call checkout to get paymentIntentClientSecret key
                    progressDialog.setTitle("Transaction in progress");
                    progressDialog.show();
                    startCheckout();
                }
                else{
                    Log.d("EmptyInp", "card/amt info was empty");

                    // create and show the alert dialog
//                    AlertDialog dialog = builder.create();
//                    alertDialog.setTitle("Amount and card information field was missing");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }


    private void startCheckout() {
        {
            Log.e("StartingCheckout", "starting to run checkout procedure");
            MediaType mediaType = MediaType.get("application/json; charset=utf-8");
//        String json = "{"
//                + "\"currency\":\"usd\","
//                + "\"items\":["
//                + "{\"id\":\"photo_subscription\"}"
//                + "]"
//                + "}";
            String token = ProfileInfo.getToken();
            double amount=amountDouble*100;
            Map<String,Object> payMap=new HashMap<>();
//            Map<String,Object> itemMap=new HashMap<>();
//            List<Map<String,Object>> itemList =new ArrayList<>();
//            payMap.put("currency","CAD");
//            itemMap.put("id","photo_subscription");
            payMap.put("amount",amount);
//            itemList.add(itemMap);
//            payMap.put("items",itemList);
            String json = new Gson().toJson(payMap);
            RequestBody body = RequestBody.create(json, mediaType);
            Request request = new Request.Builder()
                    .url(BACKEND_URL + "create-payment-intent")
                    .post(body)
                    .header("Authorization", token)
                    .build();
            httpClient.newCall(request)
                    .enqueue(new PayCallback(this));
        }
    }

    private static final class PayCallback implements Callback {
        @NonNull
        private final WeakReference<OrgCurrencyConversionActivity> activityRef;
        PayCallback(@NonNull OrgCurrencyConversionActivity activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            progressDialog.dismiss();
            final OrgCurrencyConversionActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            activity.runOnUiThread(() ->
                    Toast.makeText(
                            activity, "Internal Error: " + e.toString(), Toast.LENGTH_LONG
                    ).show()
            );
        }
        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final OrgCurrencyConversionActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Payment Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }


    private void onPaymentSuccess(@NonNull final Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );
        paymentIntentClientSecret = responseMap.get("clientSecret");
//        paymentIntentClientSecret = paymentIntentClientSecret.trim();

        //once you get the payment client secret start transaction
        //get card detail
        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
        if (params != null) {
            //now use paymentIntentClientSecret to start transaction
            ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
            //start payment
            stripe.confirmPayment(OrgCurrencyConversionActivity.this, confirmParams);
        }
        Log.i("TAG", "onPaymentSuccess: "+paymentIntentClientSecret);
    }

    private void creditCardChargeSuccess() {
        /// successful then add currency
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        String token = ProfileInfo.getToken();
        double amount=amountDouble*100;
        Map<String,Object> payMap=new HashMap<>();
//            Map<String,Object> itemMap=new HashMap<>();
//            List<Map<String,Object>> itemList =new ArrayList<>();
//            payMap.put("currency","CAD");
//            itemMap.put("id","photo_subscription");
        payMap.put("amount",amount);
//            itemList.add(itemMap);
//            payMap.put("items",itemList);
        String json = new Gson().toJson(payMap);
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "payment-success-intent")
                .post(body)
                .header("Authorization", token)
                .build();
        httpClient.newCall(request)
                .enqueue(new AddCurrencyCallback(this));

    }

    private final class AddCurrencyCallback implements Callback {
        @NonNull
        private final WeakReference<OrgCurrencyConversionActivity> activityRef;
        AddCurrencyCallback(@NonNull OrgCurrencyConversionActivity activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final OrgCurrencyConversionActivity activity = activityRef.get();
            if (!response.isSuccessful()) {
//                // Payment completed successfully
//                Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                Toast toast =Toast.makeText(OrgCurrencyConversionActivity.this, "Ordered Successful", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
            } else {
                // Payment completed successfully
//                Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                Toast toast =Toast.makeText(OrgCurrencyConversionActivity.this, "Ordered Successful", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
            }
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));

    }

    private final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<OrgCurrencyConversionActivity> activityRef;
        PaymentResultCallback(@NonNull OrgCurrencyConversionActivity activity) {
            activityRef = new WeakReference<>(activity);
        }
        //If Payment is successful
        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            progressDialog.dismiss();
            final OrgCurrencyConversionActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {


                creditCardChargeSuccess();

//                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Toast toast =Toast.makeText(activity, "Ordered Successful", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }
        //If Payment is not successful
        @Override
        public void onError(@NonNull Exception e) {
            progressDialog.dismiss();
            final OrgCurrencyConversionActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            Log.e("StripeInvalid", e.toString());
            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
        Intent intent = new Intent(getApplicationContext(), OrgMainNavbarActivity.class);
        startActivity(intent);
        return true;
    }
}