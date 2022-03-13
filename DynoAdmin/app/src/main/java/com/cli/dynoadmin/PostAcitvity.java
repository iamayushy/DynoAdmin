package com.cli.dynoadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostAcitvity extends AppCompatActivity {

    private EditText adminTitle;
    private EditText adminDescription;
    private EditText adminNews;
    private EditText adminUrl;
    private Button adminButton;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_acitvity);

        adminTitle = findViewById(R.id.headlinetext);
        adminDescription = findViewById(R.id.description);
        adminNews = findViewById(R.id.completenews);
        adminUrl = findViewById(R.id.urltext);
        adminButton = findViewById(R.id.addpost);

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titletext = adminTitle.getText().toString();
                String desc = adminDescription.getText().toString();
                String news = adminNews.getText().toString();
                String url = adminUrl.getText().toString();
                Boolean isUrl = URLUtil.isValidUrl(url);


                if (!isUrl){
                    adminUrl.setError("Not A Valid Url");
                }


                if (isUrl && !titletext.isEmpty() && !desc.isEmpty() && !news.isEmpty()){

                    Post post = new Post(titletext, desc, news, url);
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("ADMIN");
                    reference.child(titletext).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            adminTitle.setText("");
                            adminDescription.setText("");
                            adminNews.setText("");
                            adminUrl.setText("");
                            startActivity(new Intent(PostAcitvity.this, MainActivity.class));
                        }

                    });


                }
                else{
                    Toast.makeText(PostAcitvity.this, "Field Can't Be Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}