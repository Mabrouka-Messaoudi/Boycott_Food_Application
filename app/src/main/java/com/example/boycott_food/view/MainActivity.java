package com.example.boycott_food.view;
import com.example.boycott_food.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        findViewById(R.id.btn_scan).setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Scan a barcode");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.initiateScan();
        });

        Log.d("onCreate", "***********************End");

    }


    private void startProductDetailsActivity(String brandName, String productName) {
        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
        intent.putExtra("brandName", brandName);
        intent.putExtra("productName", productName);

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String barcode = result.getContents();
            if (barcode != null) {
                OpenFoodFactsAPI api = new OpenFoodFactsAPI();
                api.getProductInformation(this, barcode, new OpenFoodFactsAPI.OnProductInfoListener() {
                    @Override
                   
                    public void onSuccess(String brandName, String productName) {
                        // Start ProductDetailsActivity with the product information as intent extras
                        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("brandName", brandName);
                        intent.putExtra("productName", productName);
                        startActivity(intent); // Add this line to start the activity
                    }


                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }





}
