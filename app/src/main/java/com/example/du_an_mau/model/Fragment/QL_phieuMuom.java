package com.example.du_an_mau.model.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.du_an_mau.Adapter.phieuMuon_Adapter;
import com.example.du_an_mau.Daos.Sach_Dao;
import com.example.du_an_mau.Daos.Thanhvien_Dao;
import com.example.du_an_mau.Daos.phieuMuon_Dao;
import com.example.du_an_mau.R;
import com.example.du_an_mau.model.Sach;
import com.example.du_an_mau.model.thanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;


public class QL_phieuMuom extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton flt_btn_Them;
    ArrayList<PhieuMuon> list = new ArrayList<>();
    phieuMuon_Dao pmdao;
    SearchView searchView;
    phieuMuon_Adapter pmadapter;
    Sach_Dao sachdao;
    Thanhvien_Dao tvdao;
    Spinner spnThanhVien, spnSach;
    EditText txtNgayThue, txtTienThue;
    ImageButton btnNgayThue;

    int indexS, indexTV;



    public QL_phieuMuom() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q_l_phieu_muom, container, false);
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.rcvPhieuMuon);
        flt_btn_Them = view.findViewById(R.id.flt_btn_Them);
        flt_btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog_Them();
            }
        });

        //
        pmdao = new phieuMuon_Dao(getContext());
        list = pmdao.selectAll();
        //
        pmadapter = new phieuMuon_Adapter(getContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(pmadapter);
        pmadapter.notifyDataSetChanged();
        return view;
    }
    public void OpenDialog_Them() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.layout_them_phieumuon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        txtNgayThue = view.findViewById(R.id.txtNgayThue);
        txtTienThue = view.findViewById(R.id.txtTienThue);
        spnThanhVien = view.findViewById(R.id.spnThanhVien);
        btnNgayThue = view.findViewById(R.id.btnNgayThue);
        spnSach = view.findViewById(R.id.spnSach);

        sachdao = new Sach_Dao(getContext());
        tvdao = new Thanhvien_Dao(getContext());

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        ArrayList<Sach> listS = new ArrayList<>();
        listS = sachdao.getDSSach();
        ArrayList<String> sachArr = new ArrayList<>();
        ArrayList<String> tienThueArr = new ArrayList<>();
        for (Sach x: listS) {
            sachArr.add(x.getTenSach());
            tienThueArr.add(String.valueOf(x.getGiaThue()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, sachArr);
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
        ArrayAdapter<String> adaptertv = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, thanhVienArr);
        spnThanhVien.setAdapter(adaptertv);
        spnThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indexTV = tvdao.getMaTV(thanhVienArr.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnNgayThue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog getDay = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtNgayThue.setText(String.format("%d/%d/%d", dayOfMonth, month+1, year));
                    }
                }, year, month, day);
                getDay.show();
            }
        });
        txtNgayThue.setText(String.format("%d/%d/%d", day, month + 1, year));
        view.findViewById(R.id.btnThem_PM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngayThue = txtNgayThue.getText().toString();
                String tienThue = txtTienThue.getText().toString();
                if (thanhVienArr.isEmpty() || sachArr.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(pmdao.insert(new PhieuMuon("TT01", indexTV, indexS, Integer.valueOf(tienThue), ngayThue, 1))) {
                        list.clear();
                        list.addAll(pmdao.selectAll());
                        dialog.dismiss();
                        pmadapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
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