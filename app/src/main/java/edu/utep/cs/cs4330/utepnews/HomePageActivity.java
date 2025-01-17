package edu.utep.cs.cs4330.utepnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Tomas Patro
 * @version 1.0
 */
public class HomePageActivity extends AppCompatActivity {
    private DatabaseReference db;
    private List<Post> posts;

    private RecyclerView recyclerView;
    private PostAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setupDatabase();
        setupRecyclerView();

        ItemClickSupport.addTo(recyclerView)
            .setOnItemClickListener((RecyclerView recyclerView, int position, View v) -> {
                Intent i = new Intent(this, PostActivity.class);

                posts = recyclerAdapter.getPosts();

                i.putExtra("fileHash", posts.get(position).file_hash);
                i.putExtra("author", posts.get(position).author);
                i.putExtra("date", posts.get(position).getDate());
                startActivity(i);
            });

        setupSpinner();
        setupSearch();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupDatabase() {
        posts = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference("posts");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.child("added").exists() &&
                                ds.child("author").exists() &&
                                ds.child("description").exists() &&
                                ds.child("file_hash").exists() &&
                                ds.child("heading").exists()) {
                            Post post = ds.getValue(Post.class);
                            posts.add(post);

                            Collections.sort(posts);
                            Collections.reverse(posts);
                        }
                    }
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new PostAdapter(posts);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void logOut() {
        DialogInterface.OnClickListener dialogClickListener =
                (DialogInterface dialog, int which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    logOutYes();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Log out?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void logOutYes() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.remember_me_file_name), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("rememberMe", false);
        editor.apply();

        startActivity(new Intent(this, MainActivity.class));
    }

    private void setupSpinner() {
        Spinner spinner = findViewById(R.id.sortingSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sorting_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                posts = recyclerAdapter.getPosts();
                switch (i) {
                    case 0:
                        Collections.sort(posts);
                        Collections.reverse(posts);
                        break;
                    case 1:
                        Collections.sort(posts);
                        break;
                    default:
                        break;
                }
                recyclerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void setupSearch() {
        SearchView postSearchView = findViewById(R.id.postSearchView);

        postSearchView.setQueryHint("Search...");

        postSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                recyclerAdapter.getFilter().filter(query);
                return false;
            }
        });
    }
}
