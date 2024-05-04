package com.example.boycott_food.view;
import com.example.boycott_food.R;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView brandNameTextView;
    private TextView productNameTextView;
    private TextView boycottStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Initialize TextViews
        brandNameTextView = findViewById(R.id.brand_name_text_view);
        productNameTextView = findViewById(R.id.product_name_text_view);
        boycottStatusTextView = findViewById(R.id.boycott_status_text_view);

        // Get intent data
        Intent intent = getIntent();
        String brandName = intent.getStringExtra("brandName");
        String productName = intent.getStringExtra("productName");
        boolean isBoycotted = intent.getBooleanExtra("isBoycotted", false);

        // Set TextViews with data
        if (brandName != null) {
            brandNameTextView.setText(brandName);
        }
        if (productName != null) {
            productNameTextView.setText(productName);
        }

        // Set boycott status text and color
        if (isBoycotted) {
            boycottStatusTextView.setText("Boycotted");


        } else {
            boycottStatusTextView.setText("Not boycotted");

        }
    }
}