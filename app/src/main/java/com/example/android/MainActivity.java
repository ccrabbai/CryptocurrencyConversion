package com.example.android.bitcoinconversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

//For Volley Library
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    //(Volley) Declaring the requestQuest
    RequestQueue requestQueue;

    //Declaring the BitcoinConverterAdapter
    BitcoinConverterAdapter bitcoinConverterAdapter;

    //Url that fetches the conversion detail
    String urll="https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=NGN,ZAR,USD,KES,AFN,UGX,JPY,GBP,AUD,CAD,CHF,CNY,HUF,KMF,NZD,MYR,BND,RON,RUB,INR";

    //ProgressBar
    View loadingIndicator;
    private int showProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the ListView, and attach the adapter to the listView.
        final ListView listView = (ListView) findViewById(R.id.list);

        loadingIndicator = findViewById(R.id.loading_indicator);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        //Networking Part. Using volley
        requestQueue = Volley.newRequestQueue(this); // 'this' is the Context
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, urll, null,
                new Response.Listener<JSONObject>() {

                    // Create an ArrayList of BitcoinConverter objects
                    ArrayList<BitcoinConverter> convertersDetails = new ArrayList<>();

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject btc_rates = response.getJSONObject("BTC".trim());
                            JSONObject eth_rates = response.getJSONObject("ETH".trim());

                            Iterator<?> keysBTC = btc_rates.keys();
                            Iterator<?> keysETH = eth_rates.keys();

                            while (keysBTC.hasNext() && keysETH.hasNext()) {
                                String keyBTC = (String) keysBTC.next();
                                String keyETH = (String) keysETH.next();
                                convertersDetails.add(new BitcoinConverter(keyBTC, currency(keyBTC), imageResId(keyBTC), btc_rates.getDouble(keyBTC), eth_rates.getDouble(keyETH)));
                            }

                            // Create a new adapter that takes an empty list of BitcoinConverter as input
                            bitcoinConverterAdapter = new BitcoinConverterAdapter(MainActivity.this, convertersDetails);

                            // Set the adapter on the {@link ListView}
                            // so the list can be populated in the user interface
                            listView.setAdapter(bitcoinConverterAdapter);
                            showProgressBar = 1;

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error occurred while parsing data", Toast.LENGTH_SHORT).show();
                        }

                        if (showProgressBar == 1){
                            // Hide loading indicator because the data has been loaded
                            loadingIndicator.setVisibility(loadingIndicator.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley","An Error Just Ocurred.");
                    }
                }
        );

        requestQueue.add(jor);

        // Set an item click listener on the ListView, which sends an intent to DetailsActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current item that was clicked on
                final BitcoinConverter currentItem = bitcoinConverterAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Double bitRate = currentItem.getBitRate();
                Double ethRate = currentItem.getEthRate();
                String baseCurrencyShortName = currentItem.getBaseCurrencyShortName();
                String baseCurrencyName = currentItem.getBaseCurrencyName();

                // Create a new intent to view the details to complete activity_detail
                Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);

                //Data that will be sent with the intent
                detailsIntent.putExtra(IntentConstant.BIT_RATE, bitRate);
                detailsIntent.putExtra(IntentConstant.ETH_RATE, ethRate);
                detailsIntent.putExtra(IntentConstant.BASE_CURRENCY_SHORT_NAME, baseCurrencyShortName);
                detailsIntent.putExtra(IntentConstant.BASE_CURRENCY_NAME, baseCurrencyName);

                // Send the intent to launch a new activity
                startActivity(detailsIntent);
            }
        });
    }

    //Returns the base currency's full name
    private String currency(String currency){
        switch (currency){
            case "NGN": return "Nigerian Naira";
            case "ZAR": return "South African Rand";
            case "USD": return "United States Dollar";
            case "KES": return "Kenyan Shilling";
            case "AFN": return "Afghanistan Afghani";
            case "UGX": return "Ugandan Shilling";
            case "JPY": return "Japanese Yen";
            case "GBP": return "United Kingdom Pound Shilling";
            case "AUD": return "Australian Dollar";
            case "CAD": return "Canadian Dollar";
            case "CHF": return "Swiss Franc";
            case "CNY": return "Chinese Yuan Renminbi";
            case "HUF": return "Hungarian Forint";
            case "KMF": return "Comorian Franc";
            case "NZD": return "New Zealand Dollar";
            case "MYR": return "Malaysian Ringgit";
            case "BND": return "Brunei Dollar";
            case "RON": return "Georgian Lari";
            case "RUB": return "Russian Ruble";
            case "INR": return "Indian Rupee";
            case "GHC": return "Ghanian Cedi";
            case "HTG": return "Haitian Gourde";
        }
        return currency;
    }

    //Returns the base currency's country flag
    private int imageResId(String currency){
        switch (currency){
            case "NGN": return R.drawable.ngn;
            case "ZAR": return R.drawable.zar;
            case "USD": return R.drawable.usd;
            case "KES": return R.drawable.kes;
            case "AFN": return R.drawable.afn;
            case "UGX": return R.drawable.ugx;
            case "JPY": return R.drawable.jpy;
            case "GBP": return R.drawable.gpb;
            case "AUD": return R.drawable.aud;
            case "CAD": return R.drawable.cad;
            case "CHF": return R.drawable.chf;
            case "CNY": return R.drawable.cny;
            case "HUF": return R.drawable.huf;
            case "KMF": return R.drawable.kmf;
            case "NZD": return R.drawable.nzd;
            case "MYR": return R.drawable.myr;
            case "BND": return R.drawable.bnd;
            case "RON": return R.drawable.ron;
            case "RUB": return R.drawable.rub;
            case "INR": return R.drawable.inr;
            case "GHC": return R.drawable.ghc;
            case "HTG": return R.drawable.hgt;
        }
        return R.drawable.color_brown;
    }
}