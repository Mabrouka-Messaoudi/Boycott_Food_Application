package com.example.boycott_food.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.boycott_food.R;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private ListView listViewProducts;
    private ArrayList<Product> productList; // Assuming Product is a custom class representing product details

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listViewProducts = findViewById(R.id.listViewProducts);
        productList = loadProductList(); // Load your product list from a database or other source

        ProductAdapter adapter = new ProductAdapter(HistoryActivity.this, productList);
        listViewProducts.setAdapter(adapter);
    }

    // Custom adapter for the product list
    private class ProductAdapter extends ArrayAdapter<Product> {
        public ProductAdapter(Context context, ArrayList<Product> products) {
            super(context, 0, products);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = LayoutInflater.from(getContext()).inflate(R.layout.history_item, parent, false);
            }

            Product product = getItem(position);
            if (product != null) {
                TextView textViewBrand = itemView.findViewById(R.id.textViewBrand);
                TextView textViewProductName = itemView.findViewById(R.id.textViewProductName);
                TextView textViewBoycott = itemView.findViewById(R.id.textViewBoycott);

                textViewBrand.setText(product.getBrand());
                textViewProductName.setText(product.getProductName());
                textViewBoycott.setText(product.isBoycotted() ? "Boycotté" : "Non Boycotté");
            }

            return itemView;
        }
    }

    // Example Product class (replace with your actual implementation)
    private static class Product {
        private String brand;
        private String productName;
        private boolean boycotted;

        public Product(String brand, String productName, boolean boycotted) {
            this.brand = brand;
            this.productName = productName;
            this.boycotted = boycotted;
        }

        public String getBrand() {
            return brand;
        }

        public String getProductName() {
            return productName;
        }

        public boolean isBoycotted() {
            return boycotted;
        }
    }

    // Method to load your product list (replace with your actual implementation)
    private ArrayList<Product> loadProductList() {
        ArrayList<Product> products = new ArrayList<>();
        // Load products from your data source and add them to the list
        return products;
    }
}

