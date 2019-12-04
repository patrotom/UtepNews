package edu.utep.cs.cs4330.utepnews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author Tomas Patro
 * @version 1.0
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder>
        implements Filterable {
    private List<Post> posts;
    private CustomFilter filter;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView postHeadingView;
        private TextView postBodyView;
        private TextView postDateView;

        MyViewHolder(View view) {
            super(view);
            this.postHeadingView = view.findViewById(R.id.postHeadingView);
            this.postBodyView = view.findViewById(R.id.postBodyView);
            this.postDateView = view.findViewById(R.id.postDateView);
        }
    }

    PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    List<Post> getPosts() {
        return posts;
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
        holder.postDateView.setText(posts.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null)
            filter = new CustomFilter(posts,this);
        return filter;
    }
}
