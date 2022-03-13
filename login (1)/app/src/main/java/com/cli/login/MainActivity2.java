package com.cli.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.squareup.picasso.Picasso;

public class MainActivity2 extends AppCompatActivity {

    String title, image, content, url;

    private TextView headline;
    private ImageView imageView;
    private   TextView discontent;
    private Button DisplayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        title = getIntent().getStringExtra("title");
        image = getIntent().getStringExtra("image");
        content = getIntent().getStringExtra("content");
        url = getIntent().getStringExtra("url");

        headline = findViewById(R.id.headline);
        discontent = findViewById(R.id.headcontent);
        imageView = findViewById(R.id.newsimage);
        DisplayBtn = findViewById(R.id.readbtn);


        headline.setText(title);
        discontent.setText(content);
        Picasso.get().load(image).into(imageView);

        DisplayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               callMe();
            }
        });

    }

    private void callMe() {
        Intent intent = new Intent(this, webView.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }
}