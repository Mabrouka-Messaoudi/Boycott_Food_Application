package com.example.boycott_food.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.boycott_food.R;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView brandNameTextView;
    private TextView productNameTextView;
    private TextView boycottedStatusTextView;

    private static final String PREF_KEY_HISTORY = "scanned_products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ImageButton scanButton = findViewById(R.id.scan_button);
        ImageButton historyButton = findViewById(R.id.history_button);

        ImageButton listButton = findViewById(R.id.list_button);

        // Initialize TextViews
        brandNameTextView = findViewById(R.id.brand_name_text_view);
        productNameTextView = findViewById(R.id.product_name_text_view);
        boycottedStatusTextView = findViewById(R.id.boycott_status_text_view);


        setupNavigationBarClicks(scanButton,historyButton ,listButton);





        // Get intent data (optional)
        Intent intent = getIntent();
        if (intent != null) {
            String brandName = intent.getStringExtra("brandName");
            String productName = intent.getStringExtra("productName");
            boolean boycotted = intent.getBooleanExtra("boycotted", false);

            // Set TextViews with data
            if (brandName != null) {
                brandNameTextView.setText(brandName);
            }
            if (productName != null) {
                productNameTextView.setText(productName);
            }

            // Set boycotted status
            if (boycotted) {
                boycottedStatusTextView.setText("This brand is boycotted");
            } else {
                boycottedStatusTextView.setText("This brand is not boycotted");

            }
            Log.i("onActivityResult", "***********************end produit");

            // Save scanned product data (optional)
            saveScannedProduct(brandName, productName, boycotted);
        }
    }


    private void setupNavigationBarClicks(ImageButton scanButton,ImageButton historyButton,ImageButton listButton) {

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for handling scan button click (launch MainActivity or other)
                Intent intent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, HistoryActivity.class);
                startActivity(intent);
                Log.i("onCreate", "***********************button");


            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, BrandListActivity.class);
                startActivity(intent);
                Log.i("onCreate", "***********************button");


            }
        });

    }

    private void saveScannedProduct(String brandName, String productName, boolean boycotted) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_KEY_HISTORY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Append scanned product data to existing history (modify as needed)
        String existingHistory = sharedPreferences.getString(PREF_KEY_HISTORY, "");
        String newProductData = "\nBrand: " + brandName + ", Product: " + productName + ", Boycotted: " + boycotted;
        editor.putString(PREF_KEY_HISTORY, existingHistory + newProductData);
        editor.apply(); // Use apply() for asynchronous saving
    }

}