package com.mjc.smkguide.Adpter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mjc.smkguide.R;
import com.mjc.smkguide.task.member.mypage.MyTask;

import java.util.ArrayList;

public class MypagePaginationViewAdapter extends RecyclerView.Adapter<MypagePaginationViewAdapter.ViewHolder> {

    private ArrayList<String> itemList;
    private Activity context;
    private View root;
    private String keyword;
    public MypagePaginationViewAdapter(Activity context, ArrayList<String> itemList, View root, String keyword){
        this.context = context;
        this.itemList = itemList;
        this.root = root;
        this.keyword = keyword;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.comment_pagination_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = itemList.get(position);
        holder.textview.setText(item);
        if(item.equals("<"))
            holder.textview.setTag(String.valueOf((Integer.valueOf(itemList.get(position+1))-1)));
        else if(item.equals(">"))
            holder.textview.setTag(String.valueOf((Integer.valueOf(itemList.get(position-1))+1)));
        else
        holder.textview.setTag(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textview;

        public ViewHolder(View itemView) {
            super(itemView);

            textview = itemView.findViewById(R.id.tvPagenation);
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = keyword+"/pages/"+v.getTag().toString();
                    MyTask task = new MyTask(context,root,keyword);
                    Log.d("debugging","0");
                    task.execute(url);
                    Log.d("debugging","1");
                }
            });
        }
    }
}
