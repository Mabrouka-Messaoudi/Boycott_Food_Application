package com.example.boycott_food.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.boycott_food.R;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private ListView historyListView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyListView = findViewById(R.id.history_list_view);
        sharedPreferences = getSharedPreferences("scanned_products", MODE_PRIVATE);

        // Cast allEntries to the expected type (Map<String, String>)
        Map<String, String> allEntries = (Map<String, String>) sharedPreferences.getAll();

        List<Product> scannedProducts = loadScannedProducts(allEntries); // Pass allEntries directly

        // ... rest of the code to create and set adapter ...
    }

    private List<Product> loadScannedProducts(Map<String, String> allEntries) {
        List<Product> products = new ArrayList<>();

        for (Map.Entry<String, String> entry : allEntries.entrySet()) {
            if (entry.getKey().equals("productName")) {
                String productName = entry.getValue();
                String brandName = sharedPreferences.getString("brandName", "");
                boolean boycottStatus = sharedPreferences.getBoolean("boycottStatus", false); // Get boycott status
                products.add(new Product(productName, brandName, boycottStatus));
            }
        }

        return products;
    }


}

