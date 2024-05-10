package com.example.boycott_food.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boycott_food.R;

import java.util.List;

public class BrandListActivity extends AppCompatActivity {

    private RecyclerView brandRecyclerView;
    private BrandAdapter brandAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list);

        // Initialize RecyclerView
        brandRecyclerView = findViewById(R.id.brand_recycler_view);
        brandRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Get list of brand names from database
        List<String> brandList = databaseHelper.getAllBrands();

        // Initialize and set adapter
        brandAdapter = new BrandAdapter(brandList);
        brandRecyclerView.setAdapter(brandAdapter);
    }
}

