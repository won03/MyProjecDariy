package com.example.mydairyapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mydairyapp.object.AddUrl;
import com.example.mydairyapp.object.HttpVolleyConn;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends HttpVolleyConn implements View.OnClickListener {
    private TextView input_id, input_pw, fnd_id_pw;
    private Button login, register;
    private CheckBox auto_login;
    private SharedPreferences autoLogin;
    SharedPreferences.Editor editor;
    String loginId, loginPw;
    private  AlertDialog dialog;
    public static String idps;
    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        input_id=findViewById(R.id.main_id_input);
        input_pw=findViewById(R.id.main_pw_input);
        login=findViewById(R.id.main_login_btn);
        register=findViewById(R.id.main_reg_btn);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
        auto_login=findViewById(R.id.main_auto_login_check);

        autoLogin=getSharedPreferences("setting",0);
        editor=autoLogin.edit();
        if (autoLogin.getBoolean("autoLog",false)){
            input_id.setText(autoLogin.getString("Id",""));
            input_pw.setText(autoLogin.getString("Pwd",""));
            auto_login.setChecked(true);
        }
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    String id=input_id.getText().toString();
                    String pw=input_pw.getText().toString();
                    editor.putString("id",id);
                    editor.putString("pw",pw);
                    editor.putBoolean("Auto_Login_enabled",true);
                    editor.commit();
                }else {
                    editor.clear();
                    editor.commit();
                }
            }
        });

    }

    private void getLog(){
        idps=input_id.getText().toString();
        params.clear();
        params.put("id",idps);
        params.put("pw",input_pw.getText().toString());
        request(AddUrl.log,this);

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_login_btn) {
            String id=input_id.getText().toString();
            String pw=input_pw.getText().toString();
            if (id.equals("") || pw.equals("")) {
                Toast.makeText(getApplicationContext(), "빈칸 입력하세요", Toast.LENGTH_SHORT).show();
            }else {
                getLog();
            }
        }
        if (v.getId()==R.id.main_reg_btn){
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

        }
    }
    /* ------------------------서버에서받을때------------------------------------*/
    @Override
    public void onResponse(String s) {
        super.onResponse(s);
        try {
            JSONObject obj = new JSONObject(s);
            String name = obj.getString("result");
            String msg = "로그인실패";
            String success="S";
            String validate="N";
            String fail="F";
            if (name.equals(success)){
                Toast.makeText(this,idps+"님 환영합니다",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    onStop();
            }
            if (name.equals(fail)) {
                AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                dialog=builder.setMessage("비밀번호가 틀림")
                        .setPositiveButton("확인",null)
                        .create();
                dialog.show();
                return;
            }
            if (name.equals(validate)){
                AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                dialog=builder.setMessage("존재하지 않는아이디")
                        .setPositiveButton("확인",null)
                        .create();
                dialog.show();
                return;

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("won", "id:" + s);
    }
}
