package edu.utep.cs.cs4330.utepnews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private List<Post> posts;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView postHeadingView;
        public TextView postBodyView;
        public TextView postDateView;

        public MyViewHolder(View view) {
            super(view);
            this.postHeadingView = view.findViewById(R.id.postHeadingView);
            this.postBodyView = view.findViewById(R.id.postBodyView);
            this.postDateView = view.findViewById(R.id.postDateView);
        }
    }

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posts_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.postHeadingView.setText(posts.get(position).heading);
        holder.postBodyView.setText(posts.get(position).description);

        Date date = new Date((long)posts.get(position).added*1000);
        DateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        holder.postDateView.setText(f.format(date));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
