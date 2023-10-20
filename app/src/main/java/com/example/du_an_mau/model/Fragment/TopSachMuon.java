package com.example.du_an_mau.model.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.du_an_mau.Adapter.top_Adapter;
import com.example.du_an_mau.Daos.phieuMuon_Dao;
import com.example.du_an_mau.R;
import com.example.du_an_mau.model.top10;

import java.util.ArrayList;


public class TopSachMuon extends Fragment {


    RecyclerView rcvTop10;
    ArrayList<top10> list = new ArrayList<>();
    phieuMuon_Dao daoPhieuMuon;
    top_Adapter adapterTop10;

    public TopSachMuon() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_sach_muon, container, false);
        rcvTop10 = view.findViewById(R.id.rcvTop10);
        daoPhieuMuon = new phieuMuon_Dao(getContext());
        list = daoPhieuMuon.getTop();
        adapterTop10 = new top_Adapter(getContext(), list);
        //
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvTop10.setLayoutManager(linearLayoutManager);
        rcvTop10.setAdapter(adapterTop10);
        adapterTop10.notifyDataSetChanged();
        return view;
    }
}