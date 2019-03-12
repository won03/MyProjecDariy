package com.example.mydairyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.example.mydairyapp.adapter.RecyclerViewAdapter;
import com.example.mydairyapp.object.AddUrl;
import com.example.mydairyapp.object.BackPressFinishHandler;
import com.example.mydairyapp.object.HttpVolleyConn;
import com.example.mydairyapp.object.RecyclerContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends HttpVolleyConn {
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<RecyclerContent> dataArr = new ArrayList<>();
    private BackPressFinishHandler backPressFinishHandler;

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_View);
        setRecyclerView();
        getSelectList();
        backPressFinishHandler = new BackPressFinishHandler(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                addPage();

        }

        return true;
    }

    private void addPage() {
        startActivity(new Intent(MainActivity.this, WritingActivity.class));
        finish();
    }

    private void setRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerViewAdapter(this, dataArr, pos);
        recyclerView.setAdapter(recyclerAdapter);

    }

    /*-----------------------------------서버에 던저주는 메소드----------------------------------------------------------*/
    private void getSelectList() {
        params.clear();
        params.put("id", LoginActivity.idps);
        request(AddUrl.list, this);

    }

    /*----------------------------------------JSON을이용하여 서버에서 주는 것을 받아오는 메소드 ----------------------------------------------------*/
    @Override
    public void onResponse(String s) {
        super.onResponse(s);

        try {
            JSONArray jArr = new JSONArray(s);

            dataArr.clear();
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject jObj = (JSONObject) jArr.get(i);
                String idx = jObj.getString("idx");
                String title = jObj.getString("title");
                String content = jObj.getString("content");
                String date = jObj.getString("the_date");
                RecyclerContent recyclerContent = new RecyclerContent(idx, title, content, date);
                dataArr.add(recyclerContent);
                recyclerAdapter.notifyDataSetChanged();

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        onPause();

        Log.d("won", "id:" + s);

    }
    @Override
    protected void onResume() {
        super.onResume();
       getSelectList();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        backPressFinishHandler.onBackPressed();
    }
}
