package com.example.boycott_food.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.boycott_food.R;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Récupérer les données du produit depuis l'Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String brandName = extras.getString("brandName");
            String productName = extras.getString("productName");
            String imageUrl = extras.getString("imageUrl"); // URL de l'image du produit

            // Afficher les données du produit dans les vues appropriées
            TextView brandNameTextView = findViewById(R.id.brand_name_text_view);
            TextView productNameTextView = findViewById(R.id.product_name_text_view);
            brandNameTextView.setText("Brand : " + brandName);
            productNameTextView.setText("Product Name: " + productName);

            // Charger l'image du produit depuis l'URL avec Picasso
            ImageView productImageView = findViewById(R.id.product_image);
            Picasso.get().load(imageUrl).into(productImageView);
        }
    }

}
