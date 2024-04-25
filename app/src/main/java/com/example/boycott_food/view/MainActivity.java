package com.example.boycott_food.view;

// Importing necessary packages
import android.content.Intent;
import android.os.Bundle;
import com.example.boycott_food.R;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.widget.TextView;
import android.widget.Toast;

// MainActivity class declaration
public class MainActivity extends AppCompatActivity {

    // Declaration of TextView variable to display product information
    private TextView product_info;

    // onCreate method called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the content view to activity_main layout

        // Initializing the button for launching barcode scanner
        findViewById(R.id.btn_scan).setOnClickListener(v -> {
            // Creating IntentIntegrator object for integrating barcode scanning functionality
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES); // Setting desired barcode formats to scan
            integrator.setPrompt("Scan a barcode"); // Setting prompt message for the scanner
            integrator.setCameraId(0);  // Using the default rear camera
            integrator.setBeepEnabled(true); // Disabling beep sound
            integrator.initiateScan(); // Initiating barcode scanning
        });
    }

    // onActivityResult method called when an activity you launched exits
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data); // Parsing the result from barcode scanner
        if (result != null) {
            String barcode = result.getContents(); // Getting the scanned barcode
            if (barcode != null) {
                OpenFoodFactsAPI api = new OpenFoodFactsAPI(); // Creating an instance of OpenFoodFactsAPI
                api.getProductInformation(this, barcode, new OpenFoodFactsAPI.OnProductInfoListener() {
                    @Override
                    public void onSuccess(String brandName, String productName, String imageUrl) {
                        // Creating an Intent to launch the ProductDetailsActivity and passing product data
                        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("brandName", brandName);
                        intent.putExtra("productName", productName);
                        intent.putExtra("imageUrl", imageUrl);
                        startActivity(intent); // Starting the ProductDetailsActivity
                    }

                    @Override
                    public void onFailure(String message) {
                        // Handling failure in retrieving product information
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
                    }
                });
            }
        }
    }

}
