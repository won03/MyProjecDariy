package com.example.mydairyapp.object;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class HttpVolleyConn extends AppCompatActivity implements Response.ErrorListener, Response.Listener<String> {
    String ip = "192.168.0.105:8090";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Map<String, String> params = new HashMap<String, String>();

    public void request(String url, Response.Listener<String> listener) {
        //통신 요청
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        url = "http://" + ip + "/projecLWJ/" + url; // insert, delete, login , ...
        Log.d("won", "url: " + url);
        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                listener, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
        );
        stringRequest.add(myReq);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.d("won", "onErrorResponse: " + volleyError);
    }

    @Override
    public void onResponse(String s) {

    }
}
