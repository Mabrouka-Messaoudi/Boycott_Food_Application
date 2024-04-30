package com.example.boycott_food.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.boycott_food.R;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference brandsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://boycott-app-d0c36-default-rtdb.europe-west1.firebasedatabase.app/");
        brandsRef = databaseReference.child("brands"); // Initialize brandsRef here

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
                        // Query the database to check if the brand is boycotted
                        checkBrandBoycottStatus(brandName);
                    }

                    @Override
                    public void onFailure(String message) {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
                    }
                });
            }
        }
    }

    private void checkBrandBoycottStatus(String brandName) {
        brandsRef.child(brandName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Brand exists in the database (boycotted)
                    startProductDetailsActivity(brandName, true);
                }// Pass boycotted status as true
                else {
                    // Brand doesn't exist in the database (not boycotted)
                    startProductDetailsActivity(brandName, false); // Pass boycotted status as false
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error handling
                Log.e("Firebase", "Error querying database: " + databaseError.getMessage());
                // You can display an error message to the user here if needed
            }
        });
    }

    private void startProductDetailsActivity(String brandName, boolean isBoycotted) {
        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
        intent.putExtra("brandName", brandName);
        intent.putExtra("isBoycotted", isBoycotted); // Pass the boycotted status
        startActivity(intent);
    }
}
