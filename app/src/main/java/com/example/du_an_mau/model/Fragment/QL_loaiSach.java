package com.example.du_an_mau.model.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.du_an_mau.Adapter.loaiSach_Adapter;
import com.example.du_an_mau.Daos.loaiSach_Dao;
import com.example.du_an_mau.R;
import com.example.du_an_mau.model.loaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
public class QL_loaiSach extends Fragment {
    RecyclerView rcvloaiSach;
    FloatingActionButton btnADD;
    loaiSach_Dao lsDao;
    private loaiSach_Adapter adapter;
    ArrayList<loaiSach> list= new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_q_l_loai_sach, container, false);

        // Thiết kế giao diện (chính + giao diện)
        rcvloaiSach=view.findViewById(R.id.lvLoaiSach);
        btnADD=(FloatingActionButton) view.findViewById(R.id.flAddLS);
        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogThem();
            }
        });

        // data
        lsDao= new loaiSach_Dao(getContext());

        //adapter
        loadData();

        return view;
    }
    public void showDiaLogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_loai_sach,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Ánh xạ
        EditText edtTheLoaiSach = view.findViewById(R.id.edtTheLoaiSach);
        Button btnThemTheLoaiSach = view.findViewById(R.id.btnThemTheLoaiSach);
        Button btnHuyTheLoaiSach = view.findViewById(R.id.btnHuyTheLoaiSach);

        btnThemTheLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtTheLoaiSach.getText().toString();

                if (tenLoai.equals("")) {
                    Toast.makeText(getContext(), "Vui lòng nhập thể loại sách", Toast.LENGTH_SHORT).show();
                    return;

                }
                boolean check = lsDao.insert(tenLoai);

                if (check){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuyTheLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
    public void loadData() {
        list=lsDao.getDSLoaiSach();//lấy toàn bộ d liệu trong sql
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        rcvloaiSach.setLayoutManager(layoutManager);
        adapter=new loaiSach_Adapter(getContext(),list,lsDao);
        rcvloaiSach.setAdapter(adapter);
    }
}