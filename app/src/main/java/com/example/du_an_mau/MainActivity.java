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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.du_an_mau.Daos.Thuthu_Dao;
import com.example.du_an_mau.model.Fragment.PhieuMuon;
import com.example.du_an_mau.model.Fragment.QL_Sach;
import com.example.du_an_mau.model.Fragment.QL_Thanhvien;
import com.example.du_an_mau.model.Fragment.QL_loaiSach;
import com.example.du_an_mau.model.Fragment.QL_phieuMuom;
import com.example.du_an_mau.model.Fragment.TopSachMuon;
import com.example.du_an_mau.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    NavigationView navigationView;
    Thuthu_Dao ttdao;
    String maTT, matKhauMoi;
    ArrayList<ThuThu> listTT = new ArrayList<>();

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
        ttdao = new Thuthu_Dao(this);
        listTT = ttdao.selectAll();
        for(ThuThu x : listTT){
            if(getIntent().getStringExtra("maTT").equals(x.getMaTT())) {

                if (x.getChucVu() == 0) {
                    navigationView.getMenu().findItem(R.id.nav_ThemThuThu).setVisible(true);
                } else {
                    navigationView.getMenu().findItem(R.id.nav_ThemThuThu).setVisible(false);
                }
            }
        }
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
                   OpenDialog_ThemTT();
                } else if (item.getItemId()==R.id.nav_DoanhThu) {
                    QL_Thanhvien tv = new QL_Thanhvien();
                    doifrg(tv);
                    toolbar.setTitle(item.getTitle());
                } else if (item.getItemId()==R.id.nav_TopMuon) {
                    TopSachMuon topSachMuon = new TopSachMuon();
                    doifrg(topSachMuon);
                    toolbar.setTitle(item.getTitle());
                } else if (item.getItemId()==R.id.nav_DoiMatKhau) {
                    OpenDialog_DoiMatKhau();
                }else if (item.getItemId()==R.id.nav_DangXuat) {
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
    public void OpenDialog_DoiMatKhau(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_doimatkhau, null);
        builder.setView(view);
        Dialog dialog = builder.create();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText txtMatKhauCu = view.findViewById(R.id.txtMatKhauCu);
        EditText txtMatKhauMoi = view.findViewById(R.id.txtMatKhauMoi);
        EditText txtMatKhauXacNhan = view.findViewById(R.id.txtMatKhauXacNhan);


        view.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 maTT = getIntent().getStringExtra("maTT");

                String matKhauCu = txtMatKhauCu.getText().toString().trim();
                 matKhauMoi = txtMatKhauMoi.getText().toString().trim();
                String matKhauXacNhan = txtMatKhauXacNhan.getText().toString().trim();

                if (matKhauCu.isEmpty() || matKhauMoi.isEmpty() || matKhauXacNhan.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (ttdao.checklogin( maTT, matKhauCu)) {
                        if (matKhauMoi.equals(matKhauXacNhan)) {
//                            Toast.makeText(MainActivity.this, "Còn gì đâu em hỡi", Toast.LENGTH_SHORT).show();
                            openDialog_XacNhan();
                        } else {
                            Toast.makeText(MainActivity.this, "Mật khẩu mới không khớp nhau", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void openDialog_XacNhan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.warning);
        builder.setTitle("Warning");
        builder.setMessage("Bạn có chắc chắn muốn đổi mật khẩu ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (ttdao.doiMatKhau( maTT, matKhauMoi)) {
                    ttdao.selectAll();
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
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
    public void OpenDialog_ThemTT() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.layout_themthuthu, null);
        builder.setView(view);
        Dialog dialog = builder.create();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText txtMaTT = view.findViewById(R.id.txtMaTT);
        EditText txtTenTT = view.findViewById(R.id.txtTenTT);
        EditText txtMatKhau = view.findViewById(R.id.txtMatKhau);
        EditText txtMatKhau_2 = view.findViewById(R.id.txtMatKhau_2);

        view.findViewById(R.id.btnThem_TT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maTT = txtMaTT.getText().toString();
                String tenTT = txtTenTT.getText().toString();
                String matKhau = txtMatKhau.getText().toString();
                String matKhau2 = txtMatKhau_2.getText().toString();

                if (maTT.isEmpty() || tenTT.isEmpty() || matKhau.isEmpty() || matKhau2.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (ttdao.checkMaTT(maTT)) {
                        Toast.makeText(MainActivity.this, "Mã thủ thư đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else if (!matKhau.equals(matKhau2)) {
                        Toast.makeText(MainActivity.this, "Mật khẩu không khớp nhau", Toast.LENGTH_SHORT).show();
                    } else if (ttdao.insert(new ThuThu(maTT, tenTT, matKhau, 1))) {
                        ttdao.selectAll();
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
