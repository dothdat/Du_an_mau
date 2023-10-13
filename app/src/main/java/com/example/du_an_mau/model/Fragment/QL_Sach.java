package com.example.du_an_mau.model.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.du_an_mau.Adapter.sach_Adapter;
import com.example.du_an_mau.Daos.Sach_Dao;
import com.example.du_an_mau.Daos.loaiSach_Dao;
import com.example.du_an_mau.R;
import com.example.du_an_mau.model.Sach;
import com.example.du_an_mau.model.loaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QL_Sach extends Fragment {
    private RecyclerView rcvSach;
    private FloatingActionButton btnADDSach;
    private Sach_Dao sach_dao;
    private sach_Adapter sach_adapter1;
    private loaiSach_Dao lsdao;
    int index;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q_l__sach, container, false);
        lsdao = new loaiSach_Dao(getContext());
        rcvSach = view.findViewById(R.id.lvSach);
        btnADDSach=(FloatingActionButton) view.findViewById(R.id.flAddS);
        btnADDSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogAdd();

            }
        });

        //thiết kế giao diện (chính + giao diện item)

        //data
        sach_dao = new Sach_Dao(getContext());

        //adapter
        loadData();

        return view;

    }
    private void loadData() {
        ArrayList<Sach> list = sach_dao.getDSSach();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvSach.setLayoutManager(linearLayoutManager);
        sach_adapter1 = new sach_Adapter(getContext(),list,sach_dao);
        rcvSach.setAdapter(sach_adapter1);

    }
    private void showDiaLogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_sach,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        EditText edtLoaiSach = view.findViewById(R.id.edtLoaiSach);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);
        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtTacGia = view.findViewById(R.id.edtTacGia);
        EditText edtGiaThue = view.findViewById(R.id.edtGiaThue);
        Button btnThemSach = view.findViewById(R.id.btnThemSach);
        Button btnHuySach = view.findViewById(R.id.btnHuySach);
        ArrayList<loaiSach> listLS = new ArrayList<>();
        listLS = lsdao.getDSLoaiSach();
        ArrayList<String> loaiSachArr = new ArrayList<>();
        for (loaiSach x: listLS) {
            loaiSachArr.add(x.getTenLoai());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, loaiSachArr);
        spnLoaiSach.setAdapter(adapter);
        spnLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index = lsdao.getMaLoai(loaiSachArr.get(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String loaiSach = edtLoaiSach.getText().toString();
                String tenSach = edtTenSach.getText().toString();
                String tacGia = edtTacGia.getText().toString();
                String giaThue = edtGiaThue.getText().toString();

                if (tenSach.isEmpty() || tacGia.length()==0 || tacGia.length() == 0) {
                    Toast.makeText(getContext(),"Nhập thông tin đầy đủ!",Toast.LENGTH_LONG).show();
                } else if (!giaThue.matches("\\d+")){
                    Toast.makeText(getContext(), "Giá sai định dạng!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = sach_dao.insert(tenSach,tacGia, Integer.parseInt(giaThue),index);
                    if (check) {
                        Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                        loadData();
                        alertDialog.dismiss();

                    }else {
                        Toast.makeText(getContext(),"Thêm thất bại",Toast.LENGTH_LONG).show();
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
}