package com.cli.dynoadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ImageView postButton;
    private RecyclerView recyclerView;
    private  FireAdapter fireAdapter;
    private Button imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postButton = findViewById(R.id.postbutton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PostAcitvity.class));
            }
        });



        recyclerView = findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("ADMIN"), Post.class)
                .build();

        fireAdapter = new FireAdapter(options);
        recyclerView.setAdapter(fireAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        fireAdapter.startListening();

    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        fireAdapter.stopListening();
//    }
}