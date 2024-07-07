package com.example.doanfashionapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.doanfashionapp.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchHistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> searchHistory;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "SearchHistoryPrefs";
    private String PREF_KEY;

    public interface OnHistoryItemClickListener {
        void onHistoryItemClick(String query);
    }

    private OnHistoryItemClickListener mListener;
    public SearchHistoryAdapter(Context context, ArrayList<String> searchHistory, OnHistoryItemClickListener listener, String username) {
        this.context = context;
        this.searchHistory = searchHistory;
        this.mListener = listener;
        this.PREF_KEY = "SearchHistory_" + username;
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return searchHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return searchHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_history, parent, false);
        }

        TextView tvHistoryItem = convertView.findViewById(R.id.tvHistoryItem);
        ImageButton btnDeleteHistory = convertView.findViewById(R.id.btnDeleteHistory);

        String item = searchHistory.get(position);
        tvHistoryItem.setText(item);

        btnDeleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeHistoryItem(item);
                searchHistory.remove(position);
                notifyDataSetChanged();
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy từ khóa từ mục được chọn
                String query = searchHistory.get(position);
                // Gửi từ khóa về Fragment thông qua Interface
                mListener.onHistoryItemClick(query);
            }
        });

        return convertView;
    }

    private void removeHistoryItem(String item) {
        Set<String> searchHistorySet = sharedPreferences.getStringSet(PREF_KEY, new HashSet<>());
        searchHistorySet.remove(item);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(PREF_KEY, searchHistorySet);
        editor.apply();
    }
}
