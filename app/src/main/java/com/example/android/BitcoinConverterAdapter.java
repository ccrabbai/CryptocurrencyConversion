package com.example.android.bitcoinconversion;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class BitcoinConverterAdapter extends ArrayAdapter<BitcoinConverter> {

    //Public Constructor
    public BitcoinConverterAdapter(Activity context, ArrayList<BitcoinConverter> bitcoinConverters) {
        super(context, 0, bitcoinConverters);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_item, parent, false);
        }

        // Get the {@link BitcoinConverter} object located at this position in the list
        final BitcoinConverter currentBitcoinConverter = getItem(position);

        // Find the TextView in the activity_item.xml layout with the ID default_text_View
        TextView currencyTextView = (TextView) listItemView.findViewById(R.id.default_text_View);
        // Get the country currency symbol from the current BitcoinConverter object and
        // set this text on the symbol TextView
        currencyTextView.setText(currentBitcoinConverter.getBaseCurrencyName());

        // Find the ImageView in the activity_item.xml layout with the ID image
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.image);
        // Get the image resource ID from the current BitcoinConverter object and
        // set the image to iconView
        iconView.setImageResource(currentBitcoinConverter.getImageResourceId());

        // Return the whole list item layout (containing 1 TextView and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}