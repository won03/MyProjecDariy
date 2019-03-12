package com.example.mydairyapp.object;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class BackPressFinishHandler {
    private long backPressTime=0;
    private Toast toast;
    private Activity activity;
    public BackPressFinishHandler (Activity context){
        this.activity=context;
    }
    public void onBackPressed(){
        if (System.currentTimeMillis()>backPressTime+2000){
            backPressTime=System.currentTimeMillis();
            showGuid();
            onBackPressed();
            return;
        }
        if (System.currentTimeMillis()<=backPressTime+2000){
            AlertDialog.Builder alt_bld = new AlertDialog.Builder(activity);
            alt_bld.setMessage("종료하시겠습니까?").setCancelable(
                    false).setPositiveButton("응",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            activity.moveTaskToBack(true);
                            activity.finish();
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(0);
                            toast.cancel();

                        }
                    }).setNegativeButton("아니", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            AlertDialog alert = alt_bld.create();
            alert.show();
        }
        }

    public void showGuid(){
        toast=Toast.makeText(activity,"한번더 누르시면 종료됩니다. ",Toast.LENGTH_SHORT);
        toast.show();

    }
}
