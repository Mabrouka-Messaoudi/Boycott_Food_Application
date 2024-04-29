package com.example.boycott_food.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.boycott_food.R;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView brandNameTextView;
    private TextView productNameTextView;
    private TextView boycottStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Find TextViews by their IDs
        brandNameTextView = findViewById(R.id.brand_name_text_view);
        productNameTextView = findViewById(R.id.product_name_text_view);
        boycottStatusTextView = findViewById(R.id.boycott_status_text_view);

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Get the brand name and product name from the intent
        String brandName = intent.getStringExtra("brandName");
        String productName = intent.getStringExtra("productName");

        // Set the text of TextViews with the received data
        brandNameTextView.setText(brandName);
        productNameTextView.setText(productName);

        // Get the boycotted status from the intent
        boolean isBoycotted = intent.getBooleanExtra("isBoycotted", false);

        // Display the boycotted status
        displayBoycottStatus(isBoycotted);
    }

    private void displayBoycottStatus(boolean boycotted) {
        if (boycotted) {
            // Brand is boycotted
            boycottStatusTextView.setText("Boycotted");
        } else {
            // Brand is not boycotted
            boycottStatusTextView.setText("Not boycotted");
        }
    }
}
