package edu.utep.cs.cs4330.utepnews;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class CustomFilter extends Filter {
    private PostAdapter adapter;
    private List<Post> filterList;

    CustomFilter(List<Post> filterList, PostAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            List<Post> filteredPosts = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++)
                if (filterList.get(i).heading.toUpperCase().contains(constraint))
                    filteredPosts.add(filterList.get(i));

            results.count = filteredPosts.size();
            results.values = filteredPosts;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setPosts((List<Post>)results.values);
        adapter.notifyDataSetChanged();
    }
}
