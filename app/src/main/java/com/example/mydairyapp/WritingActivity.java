package com.example.mydairyapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mydairyapp.object.AddUrl;
import com.example.mydairyapp.object.HttpVolleyConn;

import org.json.JSONException;
import org.json.JSONObject;

public class WritingActivity extends HttpVolleyConn implements View.OnClickListener {
    private EditText titleEt, conEt;
    private Button insertBtn;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        titleEt = findViewById(R.id.title_et);
        conEt = findViewById(R.id.content_et);
        insertBtn = findViewById(R.id.input_btn);
        insertBtn.setOnClickListener(this);

    }

    private void getWrite() {
        params.clear();
        params.put("id", LoginActivity.idps);
        params.put("title",titleEt.getText().toString());
        params.put("content", conEt.getText().toString());
        request(AddUrl.write, this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.input_btn) {
            String title = titleEt.getText().toString();
            if (title.equals("")) {
                Toast.makeText(this,"제목은 입력하세요!",Toast.LENGTH_SHORT).show();
            } else {
                getWrite();
            }
        }
    }

    @Override
    public void onResponse(String s) {
        super.onResponse(s);
        try {
            JSONObject obj = new JSONObject(s);
            String name = obj.getString("result");
            String success="S";
            if (name.equals(success)) {
                startActivity(new Intent(WritingActivity.this, MainActivity.class));
            finish();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("won", "id:" + s);
    }

}
