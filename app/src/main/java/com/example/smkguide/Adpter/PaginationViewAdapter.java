package com.example.smkguide.Adpter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smkguide.R;
import com.example.smkguide.task.comment.CommentTask;

import java.util.ArrayList;

public class PaginationViewAdapter extends RecyclerView.Adapter<PaginationViewAdapter.ViewHolder> {

    private ArrayList<String> itemList;
    private Activity context;
    private View root;
    private CommentTask task;
    private String url;
    public PaginationViewAdapter(Activity context, ArrayList<String> itemList,View root,String url){
        this.context = context;
        this.itemList = itemList;
        this.root = root;
        this.url = url;
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
                    task = new CommentTask(context,root);
                    String urls[] = url.split("/");
                    task.execute("/"+urls[1]+"/"+v.getTag()+".json");
                    TextView commentPage = root.findViewById(R.id.tvCommentPage);
                    commentPage.setText(v.getTag().toString());
                }
            });
        }
    }
}
