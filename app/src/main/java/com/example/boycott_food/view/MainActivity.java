// MainActivity.java
package com.example.boycott_food.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.boycott_food.R;

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
                        // Start the ProductDetailsActivity and pass product data
                        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("brandName", brandName);
                        intent.putExtra("productName", productName);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
                    }
                });
            }
        }
    }
}
