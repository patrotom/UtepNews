package edu.utep.cs.cs4330.utepnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        WebView webView = findViewById(R.id.webView);

        Intent i = getIntent();
        String fileName = i.getStringExtra("fileHash") + ".html";
        String author =  i.getStringExtra("author");
        String date = i.getStringExtra("date");
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("posts/" + fileName);

        pathReference.getDownloadUrl().addOnSuccessListener((Uri uri) -> {
            webView.loadUrl(uri.toString());
            TextView postAuthorView = findViewById(R.id.postAuthorView);
            TextView postDateView = findViewById(R.id.postDateView);
            postAuthorView.setText(Html.fromHtml("<b>Author: </b>" + author));
            postDateView.setText(date);
        }).addOnFailureListener((@NonNull Exception exception) ->
                Toast.makeText(this, "Unable to load content!",
                        Toast.LENGTH_LONG).show());
    }
}
