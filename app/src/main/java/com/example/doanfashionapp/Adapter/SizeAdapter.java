package com.example.doanfashionapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doanfashionapp.R;

import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.SizeViewHolder> {
    List<String> sizes;
    private int selectedPosition = -1;

    public SizeAdapter(List<String> sizes){
        this.sizes = sizes;
    }

    @NonNull
    @Override
    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_size, parent, false);
        return new SizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeViewHolder holder, int position) {
        String size = sizes.get(position);
        holder.txtSize.setText(size);
        holder.txtSize.setBackgroundResource(position == selectedPosition ? R.drawable.selected_size_background : R.drawable.size_background);
        holder.txtSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousSelectedPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sizes.size();
    }

    public String getSelectedSize(){
        if(selectedPosition != -1){
            return sizes.get(selectedPosition);
        }
        return null;
    }

    public static class SizeViewHolder extends RecyclerView.ViewHolder{
        TextView txtSize;

        public SizeViewHolder(@NonNull View view){
            super(view);
            txtSize = view.findViewById(R.id.txtSize);
        }
    }
}
