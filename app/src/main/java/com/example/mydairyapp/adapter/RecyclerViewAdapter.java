package com.example.mydairyapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

;

import com.example.mydairyapp.DetailActivity;
import com.example.mydairyapp.MainActivity;
import com.example.mydairyapp.R;
import com.example.mydairyapp.object.RecyclerContent;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public Activity activity;
    public ArrayList<RecyclerContent> dataArr;

    public RecyclerViewAdapter(Activity activity, ArrayList<RecyclerContent> dataArr, int pos) {
        this.pos=pos;
        this.activity = activity;
        this.dataArr = dataArr;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView content,date,title;

        public ViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.title_item_tv);
            date = itemView.findViewById(R.id.content_item_tv);
            title= itemView.findViewById(R.id.date_tv);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            v.getContext().startActivity(intent);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
        int pos;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        pos=position;
        viewHolder.content.setText(dataArr.get(position).content);
        viewHolder.date.setText(dataArr.get(position).date);
        viewHolder.title.setText(dataArr.get(position).title);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Toast.makeText(context, position + "asdf", Toast.LENGTH_SHORT).show();
                String title=dataArr.get(position).content;
                String content=dataArr.get(position).date;
                 String id=dataArr.get(position).idx;
                Intent intent = new Intent(v.getContext(), DetailActivity.class);

                intent.putExtra("title",title);
                intent.putExtra("content",content);
                intent.putExtra("idx",id);

                v.getContext().startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), DetailActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataArr.size();
    }

    private void removeItemView(int position) {
        dataArr.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataArr.size());

    }

}