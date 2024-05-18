package com.example.boycott_food.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.boycott_food.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HistoryActivity extends AppCompatActivity {

    private static final String PREF_KEY_HISTORY = "scanned_products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ImageButton scanButton = findViewById(R.id.scan_button);
        ImageButton listButton = findViewById(R.id.list_button);
        setupNavigationBarClicks(scanButton,listButton);
        // Retrieve scanned product data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_KEY_HISTORY, MODE_PRIVATE);
        String history = sharedPreferences.getString(PREF_KEY_HISTORY, "");

        // Parse history string into individual product entries
        List<ProductData> productList = new ArrayList<>();
        if (!history.isEmpty()) {
            // Regex pattern to extract brand, product, and boycott status
            Pattern pattern = Pattern.compile("(?:Brand: )?(.*?),(?: Product: )?(.*?),(?: Boycotted: )?(.*?)(?:\n|$)");
            Matcher matcher = pattern.matcher(history);

            while (matcher.find()) {
                String brand = matcher.group(1);
                String product = matcher.group(2);
                boolean boycotted = "true".equalsIgnoreCase(matcher.group(3));

                productList.add(new ProductData(brand, product, boycotted));
            }
        }

        // Set up ListView
        ListView historyListView = findViewById(R.id.history_list_view);
        ProductListAdapter adapter = new ProductListAdapter(this, productList);
        historyListView.setAdapter(adapter);
    }
    private void setupNavigationBarClicks(ImageButton scanButton,ImageButton listButton) {
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for handling scan button click (launch MainActivity or other)
                Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        listButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, BrandListActivity.class);
                startActivity(intent);
                Log.i("onCreate", "***********************button");


            }
        });

    }
    // ProductData class to hold parsed product information
    private static class ProductData {
        String brand;
        String product;
        boolean boycotted;

        public ProductData(String brand, String product, boolean boycotted) {
            this.brand = brand;
            this.product = product;
            this.boycotted = boycotted;
        }
    }

    // ProductListAdapter class to customize item display in ListView
    private class ProductListAdapter extends ArrayAdapter<ProductData> {

        public ProductListAdapter(Context context, List<ProductData> products) {
            super(context, R.layout.history_item, products);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductData product = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_item, parent, false);
            }

            TextView brandTextView = convertView.findViewById(R.id.textViewBrand);
            TextView productTextView = convertView.findViewById(R.id.textViewProductName);
            TextView boycottTextView = convertView.findViewById(R.id.textViewBoycott);

            brandTextView.setText(product.brand);
            productTextView.setText(product.product);
            boycottTextView.setText(product.boycotted ? "Boycotted" : "Not boycotted");

            return convertView;
        }
    }
}
