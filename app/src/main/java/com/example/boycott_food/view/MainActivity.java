package com.example.boycott_food.view;
import com.example.boycott_food.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference("brands");
        Log.d("onCreate", "*********************** FirebaseDatabase.getInstance()");


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


    private void startProductDetailsActivity(String brandName, String productName, boolean isBoycotted) {
        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
        intent.putExtra("brandName", brandName);
        intent.putExtra("productName", productName);
        intent.putExtra("isBoycotted", isBoycotted);
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
                        Log.i("onSuccess", "*********************** onSuccess" + brandName + "  " + productName);
                        checkBoycottStatus(brandName, productName);
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void checkBoycottStatus(String brandName, String productName) {
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("onDataChange", "*********************** onDataChange" + brandName + "  " + productName);
                // Log retrieved data
                Log.d("Firebase", "onDataChange : DataSnapshot: " + dataSnapshot.getValue());

                boolean boycotted = false;
                for (DataSnapshot brandSnapshot : dataSnapshot.getChildren()) {
                    String dbBrandName = brandSnapshot.getValue(String.class);
                    if (dbBrandName != null && dbBrandName.equals(brandName)) {
                        boycotted = true;
                        break;
                    }
                }
                startProductDetailsActivity(brandName, productName, boycotted);
                Log.i("startProductDetailsActivity", "*********************** startProductDetailsActivity" + brandName + "  " + productName + " --> "+ boycotted);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Log error message
                Log.e("Firebase", "Error querying database: " + databaseError.getMessage());
                // Display an error message to the user if needed
            }
        };
        mDatabase.addListenerForSingleValueEvent(postListener);
    }



}
