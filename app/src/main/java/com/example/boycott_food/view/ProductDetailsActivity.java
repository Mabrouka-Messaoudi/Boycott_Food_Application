package com.example.boycott_food.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.boycott_food.R;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView brandNameTextView;
    private TextView productNameTextView;
    private TextView boycottedStatusTextView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ImageButton scanButton = findViewById(R.id.scan_button);

        // Initialize TextViews
        brandNameTextView = findViewById(R.id.brand_name_text_view);
        productNameTextView = findViewById(R.id.product_name_text_view);
        boycottedStatusTextView = findViewById(R.id.boycott_status_text_view);

        sharedPreferences = getSharedPreferences("scanned_products", MODE_PRIVATE);

        setupNavigationBarClicks(scanButton);

        // Get intent data
        Intent intent = getIntent();
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
            boycottedStatusTextView.setText("This brand is boycotted.");
        } else {
            boycottedStatusTextView.setText("This brand is not boycotted.");
        }
    }

    private void setupNavigationBarClicks(ImageButton scanButton) {
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save scanned product
                String productName = productNameTextView.getText().toString();
                String brandName = brandNameTextView.getText().toString();
                saveScannedProduct(productName, brandName);

                // Launch MainActivity
                Intent intent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveScannedProduct(String productName, String brandName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("productName", productName);
        editor.putString("brandName", brandName);
        editor.apply();
    }
}
