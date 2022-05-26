package com.example.phuctong.onthick;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    TextView txtSave;
    EditText edtTien,edtDv,edtGhiChu,edtNgay,edtThanhToan,edtMail;
    ImageView imgout,imgchon;
    private SQLiteDBHandler db;
    private static final int REQUEST_CODE_EDIT =100;

    DatabaseReference mData ;
    FrmDangNhap frmDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentout=new Intent(this,FrnHome.class);
        FiniID();
        //Khởi tạo sql
        db = new SQLiteDBHandler(getApplicationContext());

        imgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentout);

            }
        });
        getIntent();

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DV dv1= ThemDv();
                Toast.makeText(getApplicationContext(),"Record inserted successfully",Toast.LENGTH_LONG).show();
                AddDv(dv1);


                //Insert SQL
                db.addDV(dv1);
                Toast.makeText(MainActivity.this, "Thêm record thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, FrnHome.class));
            }
        });

        imgchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentGd=new Intent(MainActivity.this,MainActivity2.class);
                startActivityForResult(intentGd,REQUEST_CODE_EDIT);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT) {

            int idImage = data.getIntExtra("image", 0);
            String name = data.getStringExtra("name");

            imgchon.setImageResource(idImage);
            edtDv.setText(name);
        } else {
            Toast.makeText(this, "Sai du lieu", Toast.LENGTH_SHORT).show();
        }
    }

    public void FiniID(){
        txtSave=findViewById(R.id.Txt_Save);
        edtTien=findViewById(R.id.Edt_Tien);
        edtDv=(EditText) findViewById(R.id.Edt_Gd);
        edtGhiChu=findViewById(R.id.Edt_GhiChu);
        edtNgay=findViewById(R.id.Edt_Ngay);
        edtThanhToan=findViewById(R.id.Edt_ThanhToan);
        imgout=findViewById(R.id.Img_Out);
        imgchon=(ImageView) findViewById(R.id.ImgChon);
    }

    private DV ThemDv(){
        double tien=Double.parseDouble(edtTien.getText().toString());
        String dichvu=edtDv.getText().toString();
        String ghichu=edtGhiChu.getText().toString();
        String ngay=edtNgay.getText().toString();
        String thanhtoan=edtThanhToan.getText().toString();
        DV dv=new DV(tien,dichvu,ghichu,ngay,thanhtoan);
        return dv;
    }

    private void AddDv(DV dv){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DanhSach");
        String  pathObject= FirebaseAuth.getInstance().getCurrentUser().getUid();
        String id= myRef.push().getKey();
        dv.setId(id);
        myRef.child(pathObject).child(String.valueOf(dv.getId())).setValue(dv, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this, "Luu thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}