package com.example.boycott_food.view;
import com.example.boycott_food.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;




import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a click listener for the button

        // Initialize DatabaseHelper instance
        databaseHelper = new DatabaseHelper(this);


        findViewById(R.id.btn_scan).setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Scan a barcode");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.initiateScan();
        });

        Log.d("onCreate", "***********************End");


        // Add brand names to the database

        addBrand("Saida");
        addBrand("7days");
        addBrand("7up");
        addBrand("activia");
        addBrand("ades");
        addBrand("affogata");
        addBrand("alpro");
        addBrand("alvalle");
        addBrand("always");
        addBrand("aquarius");
        addBrand("arwa");
        addBrand("astra");
        addBrand("avena");
        addBrand("bakers");
        addBrand("bueno");
        addBrand("caramel");
        addBrand("coca-cola");
        addBrand("cocacola");
        addBrand("danette");
        addBrand("danone");
        addBrand("delicious");
        addBrand("doritos");
        addBrand("délice");
        addBrand("président");
        addBrand("nescafé");
        addBrand("maxwell house");
        addBrand("sprite");
        addBrand("pepsi");
        addBrand("barbican");
        addBrand("red bull");
        addBrand("nestle");
        addBrand("rani");
        addBrand("vimto");
        addBrand("tang");
        addBrand("lipton");
        addBrand("aquafina");
        addBrand("shani");
        addBrand("tropicana");
        addBrand("bounty");
        addBrand("fanta");
        addBrand("schweppes");
        addBrand("chokella");
        addBrand("coffee mate");
        addBrand("dairy milk");
        addBrand("danino");
        addBrand("danio");
        addBrand("dr pepper");
        addBrand("hawai");
        addBrand("kinder");
        addBrand("kit kat");
        addBrand("lu");
        addBrand("lay's");
        addBrand("lion");
        addBrand("m&m's");
        addBrand("mars");
        addBrand("maltesers");
        addBrand("marlboro");
        addBrand("milka");
        addBrand("milky way");
        addBrand("nesquik");
        addBrand("nutella");
        addBrand("oreo");
        addBrand("prince");
        addBrand("quality sreet");
        addBrand("raffaello");
        addBrand("ricore");
        addBrand("skittles");
        addBrand("smarties");
        addBrand("snickers");
        addBrand("snack a jacks");
        addBrand("starbucks");
        addBrand("tuc");
        addBrand("twix");
        addBrand("warda");
        addBrand("pringles");
        addBrand("sicam");
        addBrand("lotus");
        addBrand("saida");
        addBrand("said");
        addBrand("ferrero rocher");
        addBrand("punch");
        addBrand("apla");
        addBrand("shark");
        addBrand("melliti");
        addBrand("crestalin");
        addBrand("safia");
        addBrand("marwa");
        // Add more brand names as needed



    }

    private void addBrand(String brandName) {
        // Call the addBrand method of DatabaseHelper
        databaseHelper.addBrand(brandName);
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

                        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                        boolean boycotted = databaseHelper.isBrandBoycotted(brandName);
                        brandName = brandName.toLowerCase();

                        Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("brandName", brandName);
                        intent.putExtra("productName", productName);

                        // Pass boycotted status to ProductDetailsActivity
                        intent.putExtra("boycotted", boycotted);

                        startActivity(intent); // Start the activity

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
