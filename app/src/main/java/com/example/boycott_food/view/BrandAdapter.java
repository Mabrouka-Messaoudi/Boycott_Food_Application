package com.example.boycott_food.view;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.boycott_food.R;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    private List<String> brandList;

    public BrandAdapter(List<String> brandList) {
        this.brandList = brandList;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        String brandName = brandList.get(position);
        holder.brandNameTextView.setText(brandName);
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    static class BrandViewHolder extends RecyclerView.ViewHolder {
        TextView brandNameTextView;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            brandNameTextView = itemView.findViewById(R.id.brand_name_text_view);
        }
    }
}

