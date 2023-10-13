package com.example.du_an_mau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_mau.Daos.loaiSach_Dao;
import com.example.du_an_mau.R;
import com.example.du_an_mau.model.loaiSach;

import java.util.ArrayList;

public class loaiSach_Adapter extends RecyclerView.Adapter<loaiSach_Adapter.viewholder> {
    private Context context;
    private ArrayList<loaiSach> list;
    private loaiSach_Dao loaiSach_dao;

    public loaiSach_Adapter(Context context, ArrayList<loaiSach> list, loaiSach_Dao loaiSach_dao) {
        this.context = context;
        this.list = list;
        this.loaiSach_dao = loaiSach_dao;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_loaisach,null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.txtIdTheLoai.setText("ID: "+list.get(position).getMaLoai());
        holder.txtTheloai.setText(list.get(position).getTenLoai());
        holder.txtChinhSuaLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogUpdate(list.get(holder.getAdapterPosition()));
            }
        });
        holder.txtXoaLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogDelete(list.get(holder.getAdapterPosition()).getMaLoai(),
                        list.get(holder.getAdapterPosition()).getTenLoai());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{
        TextView txtIdTheLoai, txtTheloai, txtChinhSuaLoaiSach, txtXoaLoaiSach, txt;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtIdTheLoai= itemView.findViewById(R.id.txtIdTheLoai);
            txtTheloai= itemView.findViewById(R.id.txtTheLoai);
            txtChinhSuaLoaiSach= itemView.findViewById(R.id.txtChinhSuaTheLoai);
            txtXoaLoaiSach = itemView.findViewById(R.id.txtXoaTheLoai);
        }
    }
    public void showDiaLogDelete(int maLoai, String tenLoai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setIcon(R.drawable.warning);
        builder.setMessage("Bạn có muốn xóa thể loại \""+tenLoai+"\"không?");

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = loaiSach_dao.delete(maLoai);
                if(check){
                    Toast.makeText(context,"Xóa thành công",Toast.LENGTH_LONG).show();
                    loadDS();
                }else {
                    Toast.makeText(context,"Xóa thất bại",Toast.LENGTH_LONG).show();

                }
            }
        });
        builder.setNegativeButton("Hủy",null);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }
    public void showDiaLogUpdate(loaiSach loaiSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater  layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_update_loai_sach,null);
        builder.setView(view);

        AlertDialog alertDialog =builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        EditText edtChinhSuaLoaiSach = view.findViewById(R.id.edtChinhSuaLoaiSach);
        Button btnSuaLoaiSach = view.findViewById(R.id.btnSuaLoaiSach);
        Button btnHuyLoaiSach = view.findViewById(R.id.btnHuyLoaiSach);

        edtChinhSuaLoaiSach.setText(loaiSach.getTenLoai());

        btnSuaLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtChinhSuaLoaiSach.getText().toString();
                if (tenLoai.equals("")) {
                    Toast.makeText(context, "Vui lòng nhập thể loại sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                loaiSach loaiSachUpdate = new loaiSach(loaiSach.getMaLoai(),tenLoai);

                boolean check = loaiSach_dao.suaLoaiSach(loaiSachUpdate);
                if(check) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadDS();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuyLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
    public void loadDS() {
        list.clear();
        list = loaiSach_dao.getDSLoaiSach();
        notifyDataSetChanged();
    }
}
