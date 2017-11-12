package com.example.android.bitcoinconversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    //Variables that will hold the values sent zith the intent
    Double bitRate;
    Double ethRate;
    String baseCurrencyShortName;
    String baseCurrencyName;

    Double convertedBitAmount;
    Double convertedEthAmount;

    TextView currency_real_symbol_bit;
    TextView currency_equivalent_bit;
    TextView currency_real_symbol_eth;
    TextView currency_equivalent_eth;
    TextView which_currency;
    TextView perBitcoin;
    TextView perEth;
    EditText enterBit_Eth;
    Button convertAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        currency_real_symbol_bit = (TextView) findViewById(R.id.currrency_real_symbol_bit);
        currency_real_symbol_eth = (TextView) findViewById(R.id.currrency_real_symbol_eth);
        currency_equivalent_bit = (TextView) findViewById(R.id.currrency_equivalent_bit);
        currency_equivalent_eth = (TextView) findViewById(R.id.currrency_equivalent_eth);
        which_currency = (TextView) findViewById(R.id.which_currency);
        perBitcoin = (TextView) findViewById(R.id.per_bitcoin);
        perEth = (TextView) findViewById(R.id.per_eth);
        enterBit_Eth = (EditText) findViewById(R.id.enterBit_Eth);
        convertAmount = (Button) findViewById(R.id.convertAmount);

        Intent intent = getIntent();
        //Get the data from the intent
        bitRate = intent.getDoubleExtra(IntentConstant.BIT_RATE, 0);
        ethRate = intent.getDoubleExtra(IntentConstant.ETH_RATE, 0);
        baseCurrencyShortName = intent.getStringExtra(IntentConstant.BASE_CURRENCY_SHORT_NAME);
        baseCurrencyName = intent.getStringExtra(IntentConstant.BASE_CURRENCY_NAME);

        currency_real_symbol_bit.setText(currencySymbol(baseCurrencyShortName)+" ");
        currency_real_symbol_eth.setText(currencySymbol(baseCurrencyShortName)+" ");
        currency_equivalent_bit.setText(bitRate.toString());
        currency_equivalent_eth.setText(ethRate.toString());
        which_currency.setText("Conversion to " + baseCurrencyName);

        convertAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //Get the amount of bitcoin/eth entered by the user
                    double amountEntered = Double.parseDouble(enterBit_Eth.getText().toString().trim());
                    convertedBitAmount = amountEntered*bitRate;
                    convertedEthAmount = amountEntered*ethRate;
                    double aa = amountEntered;

                    currency_equivalent_bit.setText(convertedBitAmount.toString());
                    currency_equivalent_eth.setText(convertedEthAmount.toString());

                    perEth.setText(String.format("%.1f", amountEntered));
                    perBitcoin.setText(String.format("%.1f", amountEntered));

                }catch (NumberFormatException e){
                    Toast.makeText(DetailsActivity.this, "Invalid entry", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Returns the base currency's currency symbol
    private String currencySymbol(String symbol){
        switch (symbol){
            case "NGN": return "₦";
            case "ZAR": return "SAR";
            case "USD": return "$";
            case "KES": return "KSh";
            case "AFN": return "AFN";
            case "UGX": return "USh";
            case "JPY": return "JP¥";
            case "GBP": return "UK£";
            case "AUD": return "AU$";
            case "CAD": return "CA$";
            case "CHF": return "CHF";
            case "CNY": return "CN¥";
            case "HUF": return "Ft";
            case "KMF": return "KMF";
            case "NZD": return "NZ$";
            case "MYR": return "RM";
            case "BND": return "B$";
            case "RON": return "ROL";
            case "RUB": return "RUруб";
            case "INR": return "IN₨";
            case "GHC": return "₵";
            case "HTG": return "G";
        }
        return symbol;
    }
}