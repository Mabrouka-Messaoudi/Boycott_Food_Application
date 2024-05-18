package com.example.boycott_food.view;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;


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

        ImageButton historyButton = findViewById(R.id.history_button);
        ImageButton scanButton = findViewById(R.id.scan_button);
        setupNavigationBarClicks(historyButton,scanButton);



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



    private void setupNavigationBarClicks(ImageButton historyButton,ImageButton scanButton) {
        Log.i("onCreate", "***********start");
        historyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrandListActivity.this, HistoryActivity.class);
                startActivity(intent);
                Log.i("onCreate", "***********************button");


            }
        });
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for handling scan button click (launch MainActivity or other)
                Intent intent = new Intent(BrandListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}

