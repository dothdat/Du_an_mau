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
import com.example.du_an_mau.Adapter.thanhvienAdapter;
import com.example.du_an_mau.Daos.Thanhvien_Dao;
import com.example.du_an_mau.Daos.loaiSach_Dao;
import com.example.du_an_mau.R;
import com.example.du_an_mau.model.loaiSach;
import com.example.du_an_mau.model.thanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class QL_Thanhvien extends Fragment {
    private RecyclerView rcvthanhVien;
//    ImageFilterView imgMenu;
    private FloatingActionButton btnADD;
    private Thanhvien_Dao tvDao;

    public QL_Thanhvien() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_q_l__thanhvien, container, false);

        // ánh xạ
        rcvthanhVien=view.findViewById(R.id.lvThanhVien);
        btnADD=(FloatingActionButton) view.findViewById(R.id.fltAdd);
        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogAdd();
            }
        });

        tvDao= new Thanhvien_Dao(getContext());
        loatData();

        return view;
    }
    private  void loatData() {
        ArrayList<thanhVien> list = tvDao.getAllND();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvthanhVien.setLayoutManager(linearLayoutManager);
        thanhvienAdapter thanhvienAdapter = new thanhvienAdapter(getContext(),list,tvDao);
        rcvthanhVien.setAdapter(thanhvienAdapter);
    }
    private  void showDiaLogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_thanh_vien,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //đưa dữ liệu có sẵn lên edt
        EditText edtTenTV = view.findViewById(R.id.edtTenTV);
        EditText edtSdtTV = view.findViewById(R.id.edtSdtTV);
        EditText edtDiaChi = view.findViewById(R.id.edtDiaChi);
        Button btnThemTV = view.findViewById(R.id.btnThemSV);
        Button btnHuyTV = view.findViewById(R.id.btnHuyTV);

        btnThemTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentv = edtTenTV.getText().toString();
                String sdt = edtSdtTV.getText().toString();
                String diaChi = edtDiaChi.getText().toString();

                if (tentv.length() == 0 || sdt.length() == 0 || diaChi.isEmpty()){
                    Toast.makeText(getContext(),"Nhập thông tin đầy đủ!",Toast.LENGTH_LONG).show();
                }else{
                    boolean check = tvDao.insert(tentv,sdt,diaChi);
                    if (check){
                        Toast.makeText(getContext(),"Thêm thành viên thành công",Toast.LENGTH_LONG).show();
                        loatData();

                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(getContext(),"Thêm thành viên thất bại",Toast.LENGTH_LONG).show();

                    }

                }

            }
        });
        btnHuyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }
}