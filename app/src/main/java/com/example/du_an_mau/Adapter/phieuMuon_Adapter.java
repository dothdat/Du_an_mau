package com.example.du_an_mau.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_mau.Daos.Sach_Dao;
import com.example.du_an_mau.Daos.Thanhvien_Dao;
import com.example.du_an_mau.Daos.phieuMuon_Dao;
import com.example.du_an_mau.R;
import com.example.du_an_mau.model.Fragment.PhieuMuon;
import com.example.du_an_mau.model.Sach;
import com.example.du_an_mau.model.thanhVien;

import java.util.ArrayList;

public class phieuMuon_Adapter extends RecyclerView.Adapter<phieuMuon_Adapter.ViewHolder>{
    private final Context context;
    private final ArrayList<PhieuMuon> list;
    phieuMuon_Dao pmdao;
//    PhieuMuon phieuMuon;

    Sach_Dao sachdao;
    Thanhvien_Dao tvdao;
    Spinner spnThanhVien, spnSach;
    EditText txtNgayThue, txtTienThue;
    ImageButton btnNgayThue;
    TextView lblMaPM;
    CheckBox chkTrangThai;
    int indexS, indexTV;
    PhieuMuon index;


    public phieuMuon_Adapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        pmdao = new phieuMuon_Dao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieu_muon, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.lblMaPM.setText(String.valueOf(list.get(position).getMaPhieuMuon()));
        holder.lblTenTV.setText(list.get(position).getTenTV());
        holder.lblTenSach.setText(list.get(position).getTenSach());
        holder.lblGiaThue.setText(list.get(position).getTienThue() + " VNĐ");
        holder.lblNgayThue.setText(list.get(position).getNgayMuon());
        if (list.get(position).getTrangThai() == 0) {
            holder.lblTrangThai.setText("Đã trả sách");
            holder.lblTrangThai.setTextColor(Color.argb(100,3,169,244));
        } else {
            holder.lblTrangThai.setText("Chưa trả sách");
            holder.lblTrangThai.setTextColor(Color.argb(100,244,3,3));
        }
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = list.get(position);
                openDialog_del();
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = list.get(position);
                openDialog_update();
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblMaPM, lblTenTV,lblTenSach, lblGiaThue, lblNgayThue,lblTrangThai;
        ImageButton btnXoa, btnUpdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblMaPM = itemView.findViewById(R.id.lblMaPhieu);
            lblTenTV = itemView.findViewById(R.id.lblTenTV);
            lblTenSach = itemView.findViewById(R.id.lblTenSach);
            lblGiaThue = itemView.findViewById(R.id.lblGiaThue);
            lblNgayThue = itemView.findViewById(R.id.lblNgayThue);
            lblTrangThai = itemView.findViewById(R.id.lblTraSach);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
    public void openDialog_del(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Warning");
        builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (pmdao.delete(index.getMaPhieuMuon())) {
                    list.clear();
                    list.addAll(pmdao.selectAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "XÓa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
    public void openDialog_update(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_update_phieumuon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        lblMaPM = view.findViewById(R.id.lblMaPM);
        txtNgayThue = view.findViewById(R.id.txtNgayThue_Up);
        txtTienThue = view.findViewById(R.id.txtTienThue_Up);
        spnThanhVien = view.findViewById(R.id.spnThanhVien_Up);
        chkTrangThai = view.findViewById(R.id.chkTrangThai);
        spnSach = view.findViewById(R.id.spnSach_Up);

        sachdao = new Sach_Dao(context);
        tvdao = new Thanhvien_Dao(context);

        ArrayList<Sach> listS = new ArrayList<>();
        listS = sachdao.getDSSach();
        ArrayList<String> sachArr = new ArrayList<>();
        ArrayList<String> tienThueArr = new ArrayList<>();
        for (Sach x: listS) {
            sachArr.add(x.getTenSach());
            tienThueArr.add(String.valueOf(x.getGiaThue()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, sachArr);
        spnSach.setAdapter(adapter);
        spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexS = sachdao.getMaS(sachArr.get(position).toString());
                txtTienThue.setText(String.valueOf(sachdao.getTienThue(sachArr.get(position).toString())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //
        ArrayList<thanhVien> listTV = new ArrayList<>();
        listTV = tvdao.getAllND();
        ArrayList<String> thanhVienArr = new ArrayList<>();
        for (thanhVien x: listTV) {
            thanhVienArr.add(x.getTenTv());
        }
        ArrayAdapter<String> adaptertv = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, thanhVienArr);
        spnThanhVien.setAdapter(adaptertv);

        for (int i = 0; i < thanhVienArr.size(); i++) {
            if (thanhVienArr.get(i).equals(tvdao.getTenTV(index.getMaTV()))) {
                spnThanhVien.setSelection(i);
            }
        }
        txtNgayThue.setText(pmdao.getNgayThue(String.valueOf(index.getMaPhieuMuon())));
        if (index.getTrangThai() == 0) {
            chkTrangThai.setChecked(true);
        } else {
            chkTrangThai.setChecked(false);
        }
        lblMaPM.setText("Mã phiếu: " + index.getMaPhieuMuon());
        view.findViewById(R.id.btnUpdate_PM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tienThue = txtTienThue.getText().toString();
                index.setMaSach(indexS);
                index.setTienThue(Integer.valueOf(tienThue));
                if (chkTrangThai.isChecked()) {
                    index.setTrangThai(0);
                } else {
                    index.setTrangThai(1);
                }
                if (thanhVienArr.isEmpty() || sachArr.isEmpty()) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(pmdao.update(index)) {
                        list.clear();
                        list.addAll(pmdao.selectAll());
                        dialog.dismiss();
                        notifyDataSetChanged();
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        view.findViewById(R.id.btnHuy_PM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    }

