package com.example.boycott_food.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.boycott_food.R;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView brandNameTextView;
    private TextView productNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Find TextViews by their IDs
        brandNameTextView = findViewById(R.id.brand_name_text_view);
        productNameTextView = findViewById(R.id.product_name_text_view);

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Get the brand name and product name from the intent
        String brandName = intent.getStringExtra("brandName");
        String productName = intent.getStringExtra("productName");

        // Set the text of TextViews with the received data
        if (brandName != null) {
            brandNameTextView.setText(brandName);
        }
        if (productName != null) {
            productNameTextView.setText(productName);
        }
    }
}
