package com.example.du_an_mau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.du_an_mau.Daos.Thuthu_Dao;


public class Dang_nhap extends AppCompatActivity {
    EditText txtTaiKhoan, txtMatKhau;
    Button btnDangNhap;
    CheckBox chkLuuMatKhau;
    TextView txtSignUp;
    private Thuthu_Dao thuthu_dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        btnDangNhap = findViewById(R.id.btnlogin);
        txtTaiKhoan = findViewById(R.id.edtUser);
        txtMatKhau = findViewById(R.id.edtPass);
        chkLuuMatKhau = findViewById(R.id.chkLuuMatKhau);

        thuthu_dao = new Thuthu_Dao(this);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=txtTaiKhoan.getText().toString();
                String pass=txtMatKhau.getText().toString();

                boolean check=thuthu_dao.CheckLogin(user,pass);
                if(check){
                    Toast.makeText(Dang_nhap.this,"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Dang_nhap.this,MainActivity.class));
                }else {
                    Toast.makeText(Dang_nhap.this,"Đăng nhập thất bại. Vui lòng kiểm tra lại",Toast.LENGTH_LONG).show();
                }


            }
        });


    }
}