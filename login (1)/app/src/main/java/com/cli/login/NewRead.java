package com.cli.login;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewRead extends AppCompatActivity {

    private ImageView imageView;
    private TextView headtext;
    private TextView descriptions;

    String title, description, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_read);

        imageView = findViewById(R.id.readimage);
        headtext = findViewById(R.id.readtitle);
        descriptions = findViewById(R.id.readdesc);

        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        description = getIntent().getStringExtra("description");

        headtext.setText(title);
        descriptions.setText(description);
        Picasso.get().load(url).into(imageView);

    }
}