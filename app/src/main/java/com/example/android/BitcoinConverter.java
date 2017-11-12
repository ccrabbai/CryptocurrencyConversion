package com.example.android.bitcoinconversion;

/**
 * Created by onyekachukwu on 11/1/2017.
 */

public class BitcoinConverter {
    // Drawable resource ID of the country
    private int mImageResourceId;

    //Symbol of the country's currency
    private String mBaseCurrencyShortName;

    //Short name of the base currency
    private String mBaseCurrencyName;

    //rate per bitcoin
    private double mBitRate;

    //rate per eth
    private double mEthRate;

    //Public constructor
    public BitcoinConverter(String countryCurrencySymbol, String baseCurrnecyShortName, int imageResourceId,  double bitRate, double ethRate) {
        mBaseCurrencyShortName = countryCurrencySymbol;
        mBaseCurrencyName = baseCurrnecyShortName;
        mBitRate = bitRate;
        mEthRate = ethRate;
        mImageResourceId = imageResourceId;
    }

    public double getBitRate() {
        return mBitRate;
    }

    public double getEthRate() {
        return mEthRate;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public String getBaseCurrencyShortName() { return mBaseCurrencyShortName;}

    public String getBaseCurrencyName() { return mBaseCurrencyName; }
}
