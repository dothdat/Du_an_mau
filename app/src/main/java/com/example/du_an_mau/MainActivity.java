package com.example.du_an_mau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.du_an_mau.model.Fragment.PhieuMuon;
import com.example.du_an_mau.model.Fragment.QL_Sach;
import com.example.du_an_mau.model.Fragment.QL_Thanhvien;
import com.example.du_an_mau.model.Fragment.QL_loaiSach;
import com.example.du_an_mau.model.Fragment.QL_phieuMuom;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawlayout);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView=findViewById(R.id.navigation_view);
        frameLayout=findViewById(R.id.frame_layout);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lý phiếu mượn");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        QL_phieuMuom phieuMuon= new QL_phieuMuom();
        doifrg(phieuMuon);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.nav_PhieuMuon){
                    QL_phieuMuom phieuMuon= new QL_phieuMuom();
                    doifrg(phieuMuon);
                    toolbar.setTitle(item.getTitle());
                }else if(item.getItemId()==R.id.nav_LoaiSach){
                    QL_loaiSach gt = new QL_loaiSach();
                    doifrg(gt);
                    toolbar.setTitle(item.getTitle());
                } else if (item.getItemId()==R.id.nav_Sach) {
                    QL_Sach s = new QL_Sach();
                    doifrg(s);
                    toolbar.setTitle(item.getTitle());
                } else if (item.getItemId()==R.id.nav_ThanhVien) {
                    QL_Thanhvien tv = new QL_Thanhvien();
                    doifrg(tv);
                    toolbar.setTitle(item.getTitle());
                } else if (item.getItemId()==R.id.nav_ThemThuThu) {
                    QL_Thanhvien tv = new QL_Thanhvien();
                    doifrg(tv);
                    toolbar.setTitle(item.getTitle());
                } else if (item.getItemId()==R.id.nav_DoanhThu) {
                    QL_Thanhvien tv = new QL_Thanhvien();
                    doifrg(tv);
                    toolbar.setTitle(item.getTitle());
                } else if (item.getItemId()==R.id.nav_TopMuon) {
                    QL_Thanhvien tv = new QL_Thanhvien();
                    doifrg(tv);
                    toolbar.setTitle(item.getTitle());
                } else if (item.getItemId()==R.id.nav_DoiMatKhau) {
                    QL_Thanhvien tv = new QL_Thanhvien();
                    doifrg(tv);
                    toolbar.setTitle(item.getTitle());
                }else if (item.getItemId()==R.id.nav_DangXuat){
//                    Intent intent= new Intent(MainActivity.this,Dang_nhap.class);
//                    startActivity(intent);
                    OpenDialog_DangXuat();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });


    }
    public void doifrg(Fragment fragment){
        FragmentManager manager= getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_layout,fragment).commit();
    }
    public void OpenDialog_DangXuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("WARNING");
        builder.setMessage("Bạn có muốn đăng xuất không ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, Dang_nhap.class);
                startActivity(intent);
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

}
