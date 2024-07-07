package com.example.doanfashionapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doanfashionapp.R;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {
    private List<String> colors;
    private int selectedPosition = -1;

    public ColorAdapter(List<String> colors) {
        this.colors = colors;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        String color = colors.get(position);
        holder.txtColor.setText(color);
        holder.txtColor.setBackgroundResource(position == selectedPosition ? R.drawable.selected_color_background : R.drawable.color_background);
        holder.txtColor.setOnClickListener(new View.OnClickListener() {
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
        return colors.size();
    }

    public String getSelectedColor() {
        if (selectedPosition != -1) {
            return colors.get(selectedPosition);
        }
        return null;
    }

    public static class ColorViewHolder extends RecyclerView.ViewHolder {
        TextView txtColor;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            txtColor = itemView.findViewById(R.id.txtColor);
        }
    }
}
