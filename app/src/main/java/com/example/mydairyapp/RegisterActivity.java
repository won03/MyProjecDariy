package com.example.mydairyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mydairyapp.object.AddUrl;
import com.example.mydairyapp.object.HttpVolleyConn;

import org.json.JSONException;
import org.json.JSONObject;
/*
회원가입 페이지이며 HttpVolleyConn의 클래스를 상속박아 Volley를 이용하여 서버에 던지고 받아 회원가입을 구현하는 로직
 */
public class RegisterActivity extends HttpVolleyConn implements View.OnClickListener {
    private EditText regiId, regiPass, regiPassCheck, regiName;
    private Button regiBtn,checkBtn;
    private  AlertDialog dialog;
    private boolean validate = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi);
        regiId = findViewById(R.id.regi_id);
        regiPass = findViewById(R.id.regi_pass);
        regiPassCheck = findViewById(R.id.regi_pass_check);
        checkBtn=findViewById(R.id.checkcheck);
        regiBtn = findViewById(R.id.regi_btn);
        regiName = findViewById(R.id.regi_name);
        regiBtn.setOnClickListener(this);
        checkBtn.setOnClickListener(this);



    }

    private void getId(){

        params.clear();
        params.put("id", regiId.getText().toString());
        params.put("pw", regiPass.getText().toString());
        params.put("name", regiName.getText().toString());
        request(AddUrl.regi,this);
    }

    private void getCheck(){
        params.clear();
        params.put("id",regiId.getText().toString());
        request("regiCheck",this);
    }
    /*-------------------------------------------------------------클릭 이벤트--------------------------------------------------------------------------*/
    @Override
    public void onClick(View v) {
        /*회원가입 완료 클릭시*/
        if (v.getId()==R.id.regi_btn){
            String id=regiId.getText().toString();
            String pass=regiPass.getText().toString();
            String checkPass=regiPassCheck.getText().toString();
            String name=regiName.getText().toString();
            if (id.equals("")||pass.equals("")||checkPass.equals("")||name.equals("")){
                Toast.makeText(getApplicationContext(), "빈칸 입력하세요", Toast.LENGTH_SHORT).show();
            }
            else if (!validate){
                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                dialog=builder.setMessage("중복확인 부터 하기")
                        .setPositiveButton("확인",null)
                        .create();
                dialog.show();


            }
            else if(pass.equals(checkPass)){
                getId();
            }else {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }

        /*------------------------------------------------------------중복확인 킄릭시--------------------------------------------------------------------------*/
        if (v.getId()==R.id.checkcheck){
            String id=regiId.getText().toString();

            if (id.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                dialog = builder.setMessage("아이디는 빈 칸일 수없습니다.")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
                return;
            }else {
                getCheck();
                return;

            }
        }

    }
    /*---------------------------------------------------------------------------클릭이벤트 끝-----------------------------------------------------------------------------------------------*/

    /*------------------------------------------------------------JSON으로 서버 응답을 받는 메소드-----------------------------------------------------------------*/
    @Override
    public void onResponse(String s) {
        super.onResponse(s);

        try {
            JSONObject obj = new JSONObject(s);
            String name = obj.getString("result");
            String sucess="S";
            String error="D";
            String no="F";
            String yes="Y";
            if (name.equals(no)){
                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                dialog=builder.setMessage("존재하는 아이디")
                        .setPositiveButton("확인",null)
                        .create();
                dialog.show();


            }
            else if (name.equals(yes)){
                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
//                builder.setMessage("사용 가능한 아이디 입니다!").setCancelable(false).setPositiveButton("확인",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                validate=true;
//                                return;
//                            }
//                        }
//                );
                dialog=builder.setMessage("사용 가능한 아이디 입니다!")
                        .setPositiveButton("확인",null)
                        .create();
                dialog.show();

                validate=true;


            }
              else if (name.equals(error)) {
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
                alt_bld.setMessage("에러!").setCancelable(
                        false).setPositiveButton("확인.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                AlertDialog alert = alt_bld.create();
                alert.show();
            }  else if (name.equals(sucess))  {
                Toast.makeText(this,"회원가입성공",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("won", "id:" + s);
    }
    /*--------------------------------------------------------------------------------respons부분 끝----------------------------------------------------------*/

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog !=null){
            dialog.dismiss();
            dialog=null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
