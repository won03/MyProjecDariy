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
import android.widget.EditText;

import com.example.mydairyapp.object.AddUrl;
import com.example.mydairyapp.object.HttpVolleyConn;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateActivity extends HttpVolleyConn implements View.OnClickListener {
    EditText updateTitle, updateContent;
    Intent intent;
    Button updateBtn;
    String posid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        intent=getIntent();
        updateBtn = findViewById(R.id.update_input);
        updateTitle = findViewById(R.id.update_title);
        updateContent = findViewById(R.id.update_content);
        updateTitle.setText(intent.getStringExtra("title"));
        updateContent.setText(intent.getStringExtra("content"));
        posid = intent.getStringExtra("ids");

        updateBtn.setOnClickListener(this);
    }

    private void getUpdate() {
        params.clear();
        params.put("idx", DetailActivity.posid);
        params.put("content", updateContent.getText().toString());
        params.put("title", updateTitle.getText().toString());
        request(AddUrl.update, this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.update_input){
            String title=updateTitle.getText().toString();
            if (title.equals("")){
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
                alt_bld.setMessage("제목은 받드시 적어야됨").setCancelable(
                        false).setPositiveButton("응",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                return;

                            }
                        });
            }else {
                getUpdate();
            }
        }
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
                alt_bld.setMessage("수정성공").setCancelable(
                        false).setPositiveButton("응",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                                finish();
                            }
                        });
                AlertDialog alert = alt_bld.create();
                alert.show();
            } else if (name.equals("F")) {
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
                alt_bld.setMessage("수정실패").setCancelable(
                        false).setPositiveButton("응",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            return;


                            }
                        });
                AlertDialog alert = alt_bld.create();
                alert.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("won", "id:" + s);
    }

}
