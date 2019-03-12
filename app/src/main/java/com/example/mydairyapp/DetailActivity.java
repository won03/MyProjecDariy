package com.example.mydairyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mydairyapp.object.AddUrl;
import com.example.mydairyapp.object.HttpVolleyConn;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends HttpVolleyConn implements View.OnClickListener {
    TextView titleDet, contentDet;
    Intent intent;
    Button deleteBtn, updateBtn;
    public  static  String  posid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        titleDet=findViewById(R.id.title_det);
        contentDet = findViewById(R.id.content_det);
        deleteBtn = findViewById(R.id.delete_btn);
        updateBtn = findViewById(R.id.update_btn);
        intent=getIntent();
        posid=intent.getStringExtra("idx");
        titleDet.setText(intent.getStringExtra("title"));
        contentDet.setText(intent.getStringExtra("content"));
        getDetail();
        deleteBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
    }
    private void getDetail() {
        params.clear();
        params.put("idx", posid);
        request(AddUrl.detail, this);
    }
    private void getDelete() {
        params.clear();
        params.put("idx", posid);
        request(AddUrl.delete, this);
    }
    /* ------------------------서버에서받을때------------------------------------*/
    @Override
    public void onResponse(String s) {
        super.onResponse(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            String name = jsonObject.getString("result");
            if (name.equals("S")) {
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
                alt_bld.setMessage("삭제?").setCancelable(
                        false).setPositiveButton("응",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(DetailActivity.this, MainActivity.class));
                                finish();
                            }
                        }).setNegativeButton("아니",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                return;
                            }
                        })

                ;
                AlertDialog alert = alt_bld.create();
                alert.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("won", "id:" + s);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.delete_btn){
            getDelete();
        }
        if (v.getId()==R.id.update_btn){
            startActivity(new Intent(DetailActivity.this,UpdateActivity.class));
            intent.putExtra("ids",posid);
                finish();
        }
    }
}
