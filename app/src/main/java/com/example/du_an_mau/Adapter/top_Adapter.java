package com.example.du_an_mau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_mau.R;
import com.example.du_an_mau.model.top10;

import java.util.ArrayList;

public class top_Adapter extends RecyclerView.Adapter<top_Adapter.ViewHolder>{
    private final Context context;
    private final ArrayList<top10> list;



    public top_Adapter(Context context, ArrayList<top10> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_top10, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.lblTenSach.setText(list.get(position).getTenSach());
        holder.lblSoLuong.setText(String.valueOf(list.get(position).getSoLuong()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblTenSach, lblSoLuong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTenSach = itemView.findViewById(R.id.lblTenSach);
            lblSoLuong = itemView.findViewById(R.id.lblSoLuong);
        }
    }
}
