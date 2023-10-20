package com.example.du_an_mau.Adapter;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_mau.Daos.Sach_Dao;
import com.example.du_an_mau.Daos.loaiSach_Dao;
import com.example.du_an_mau.R;
import com.example.du_an_mau.model.Sach;

import java.util.ArrayList;
public class sach_Adapter extends RecyclerView.Adapter<sach_Adapter.ViewHolder>{

    private Context context;
    private ArrayList<Sach> list;
    private Sach_Dao sach_dao;
    private loaiSach_Dao  ls_dao;

    public sach_Adapter(Context context, ArrayList<Sach> list, Sach_Dao sach_dao) {
        this.context = context;
        this.list = list;
        this.ls_dao = new loaiSach_Dao(context);
        this.sach_dao = new Sach_Dao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_sach,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtMaSach.setText("ID: "+list.get(position).getMaSach());
        holder.txtLoaiSach.setText("Loại Sách: "+ ls_dao.getTenLoai(list.get(position).getMaLoai()));
        holder.txtTenSach.setText("Tên sách: "+list.get(position).getTenSach());
        holder.txtTacGia.setText("Tác giả: "+list.get(position).getTacGia());
        holder.txtGiaThue.setText("Giá thuê: "+String.valueOf(list.get(position).getGiaThue()));
        holder.txtNamXB.setText("Năm XB:" + String.valueOf(list.get(position).getNamXB()));
        holder.txtSuaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDiaLogUpdate(list.get(position));
//                list.get(position);
            }
        });
        holder.txtGiaThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogDelete(list.get(holder.getAdapterPosition()).getMaSach(),
                        list.get(holder.getAdapterPosition()).getTenSach());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaSach, txtTenSach, txtTacGia, txtGiaThue, txtXoaSach, txtSuaSach ,txtLoaiSach, txtNamXB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLoaiSach = itemView.findViewById(R.id.txtLoaiSach);
            txtMaSach= itemView.findViewById(R.id.txtMaSach);
            txtTenSach= itemView.findViewById(R.id.txtTenSach);
            txtTacGia= itemView.findViewById(R.id.txtTacGia);
            txtGiaThue= itemView.findViewById(R.id.txtGiaThue);
            txtSuaSach= itemView.findViewById(R.id.txtSuaSach);
            txtXoaSach= itemView.findViewById(R.id.txtXoaSach);
            txtNamXB = itemView.findViewById(R.id.txtNamXB);
        }
    }
    public void showDiaLogUpdate(Sach sach) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_add_sach,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText edtNamXB = view.findViewById(R.id.edtNamXB);
        EditText edtSuaTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtSuaTacGia = view.findViewById(R.id.edtTacGia);
        EditText edtSuaGiaThue = view.findViewById(R.id.edtGiaThue);
        Button btnSuaSach = view.findViewById(R.id.btnThemSach);
        Button btnHuySach = view.findViewById(R.id.btnHuySach);
        TextView txtTieuDe = view.findViewById(R.id.txtTieuDe);
        LinearLayout LayoutSach = view.findViewById(R.id.LayoutSach);

        edtSuaTenSach.setText(sach.getTenSach());
        edtSuaTacGia.setText(sach.getTacGia());
        edtSuaGiaThue.setText(String.valueOf(sach.getGiaThue()));
        txtTieuDe.setText("CHỈNH SỬA SÁCH");

        btnSuaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtSuaTenSach.getText().toString();
                String tacGia = edtSuaTacGia.getText().toString();
                String giaThue = edtSuaGiaThue.getText().toString();
                String namXB = edtNamXB.getText().toString();
                if (tenSach.isEmpty() || tacGia.length()==0 || tacGia.length() == 0 || namXB.isEmpty()) {
                    Toast.makeText(context,"Nhập thông tin đầy đủ!",Toast.LENGTH_LONG).show();
                } else if ((!giaThue.matches("\\d+")) ||(!namXB.matches("\\d+")) ){
                    Toast.makeText(context, "Giá sai định dạng!", Toast.LENGTH_SHORT).show();
                } else {
                    sach.setTenSach(tenSach);
                    sach.setTacGia(tacGia);
                    sach.setGiaThue(Integer.parseInt(giaThue));
                    sach.setNamXB(Integer.parseInt(namXB));
//                    Sach sachUpdate = new Sach(sach.getMaSach(),tenSach,tacGia,Integer.parseInt(giaThue),Integer.parseInt(tenSach));
                    boolean check = sach_dao.suaSach(sach);
                    if (check) {
                        Toast.makeText(context,"Sửa thành công",Toast.LENGTH_LONG).show();
                        loadDS();
                        alertDialog.dismiss();

                    }else {
                        Toast.makeText(context,"Sửa thất bại",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        btnHuySach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
    public void showDiaLogDelete(int maSach, String tenSach) {
        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setIcon(R.drawable.warning);
        builder.setMessage("Bạn có muốn xóa sách \""+tenSach+"\"không?");

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = sach_dao.delete(maSach);
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
    public void loadDS() {
        list.clear();
        list = sach_dao.getDSSach();
        notifyDataSetChanged();
    }
}

