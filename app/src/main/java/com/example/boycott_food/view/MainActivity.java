package com.example.boycott_food.view;
import android.content.Intent;
import android.os.Bundle;
import com.example.boycott_food.R;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    private TextView product_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bouton pour lancer le scanner de codes-barres
        findViewById(R.id.btn_scan).setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Scan a barcode");
            integrator.setCameraId(0);  // Utiliser la caméra arrière par défaut
            integrator.setBeepEnabled(true);//beep sound
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
                        // Afficher les informations récupérées dans un TextView
                        runOnUiThread(() -> {
                            TextView productInfoTextView = findViewById(R.id.product_info);
                            productInfoTextView.setText("Marque : " + brandName + "\nNom du produit : " + productName);
                            productInfoTextView.setVisibility(View.VISIBLE); // Rendre le TextView visible
                        });
                    }

                    @Override
                    public void onFailure(String message) {
                        // Gérez l'échec de la récupération des informations du produit
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
                    }
                });
            }
        }
    }}






