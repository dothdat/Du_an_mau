package com.example.du_an_mau.Adapter;


import android.app.Activity;
import android.app.AlertDialog;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_mau.Daos.Thanhvien_Dao;
import com.example.du_an_mau.Daos.loaiSach_Dao;
import com.example.du_an_mau.R;
import com.example.du_an_mau.model.loaiSach;
import com.example.du_an_mau.model.thanhVien;

import java.util.ArrayList;

public class thanhvienAdapter extends RecyclerView.Adapter<thanhvienAdapter.viewholder> {
    private Context context;
    private ArrayList<thanhVien> list;
    private Thanhvien_Dao tvdao;

    public thanhvienAdapter(Context context, ArrayList<thanhVien> list, Thanhvien_Dao tvdao) {
        this.context = context;
        this.list = list;
        this.tvdao = tvdao;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_qlthanhvien,null);
        return new thanhvienAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.txtMaTV.setText("Mã thành viên: "+list.get(position).getMaTV());
        holder.txtTenTV.setText("Tên thành viên: "+list.get(position).getTenTv());
        holder.txtSdtTV.setText("Số điện thoại: "+list.get(position).getSdt());
        holder.txtDiaChi.setText("Địa chỉ: "+list.get(position).getDiaChi());

        // sửa thành viên
        holder.txtChinhSuaThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogUpdate(list.get(holder.getAdapterPosition()));
            }
        });
        // xóa thành viên
        holder.txtXoaThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogDelete(list.get(holder.getAdapterPosition()).getMaTV(),
                        list.get(holder.getAdapterPosition()).getTenTv());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        TextView txtTenTV, txtSdtTV, txtMaTV, txtDiaChi;
        TextView txtChinhSuaThanhVien, txtXoaThanhVien;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            txtTenTV = itemView.findViewById(R.id.txtTenTV);
            txtSdtTV = itemView.findViewById(R.id.txtSdtTV);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChiTV);

            txtChinhSuaThanhVien = itemView.findViewById(R.id.txtChinhSuaThanhVien);
            txtXoaThanhVien = itemView.findViewById(R.id.txtXoaThanhVien);

        }
    }

    private void showDiaLogUpdate(thanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dialog_update,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //đưa dữ liệu có sẵn lên edt

        EditText edtTenTV = view.findViewById(R.id.edtTenTV);
        EditText edtSdtTV = view.findViewById(R.id.edtSdtTV);
        EditText edtDiaChi = view.findViewById(R.id.edtDiaChi);
        Button btnSuaTV = view.findViewById(R.id.btnSuaTV);
        Button btnHuyTV = view.findViewById(R.id.btnHuyTV);

        edtTenTV.setText(thanhVien.getTenTv());
        edtSdtTV.setText(thanhVien.getSdt());
        edtDiaChi.setText(thanhVien.getDiaChi());

        btnHuyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnSuaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentv = edtTenTV.getText().toString();
                String sdt = edtSdtTV.getText().toString();
                String diaChi = edtDiaChi.getText().toString();

                if (tentv.length() == 0 || sdt.length() == 0 || diaChi.isEmpty()){
                    Toast.makeText(context,"Nhập thông tin đầy đủ!",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    thanhVien thanhVienChinhSua = new thanhVien(thanhVien.getMaTV(),tentv,sdt,diaChi);
                    boolean check = tvdao.update(thanhVienChinhSua);
                    if (check){
                        Toast.makeText(context,"Chỉnh sửa thành công",Toast.LENGTH_LONG).show();
                        loadDS();
                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(context,"Chỉnh sửa thất bại",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public void showDiaLogDelete(int matv, String tentv) {
        AlertDialog.Builder builder  = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setIcon(R.drawable.baseline_warning_24);
        builder.setMessage("Bạn có muốn xóa thành viên \""+ tentv+"\" không?");

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = tvdao.delete(matv);
                if (check){
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
    public void loadDS(){
        list.clear();
        list = tvdao.getAllND();
        notifyDataSetChanged();
    }
}
