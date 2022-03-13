package com.cli.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CatAdapter.CategoryClick, View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView categorylist;
    LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    adapter newsadapter;
    CatAdapter catAdapter;
    ArrayList<articles> articlesArrayList;
    ArrayList<category> categoryArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String[] words = new String[]{"Current Affairs India", "Insurnce India", "Gaming", "World Bank", "Vaccination", "University", "Smartphones", "Laptops", "School", "Jobs", "Government"};
    Random random = new Random();
    int index;
    private ImageView logout;
    private TextView uservalue;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        categorylist = findViewById(R.id.categorylist);
        recyclerView = findViewById(R.id.recycle);
        articlesArrayList = new ArrayList<>();
        categoryArrayList = new ArrayList<>();
        uservalue = findViewById(R.id.iamuser);
        progressBar = findViewById(R.id.progress);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

//        uservalue.setText("Hello");
        newsadapter = new adapter(articlesArrayList, this);
        catAdapter = new CatAdapter(categoryArrayList, this::OnCategoryClick);


        LinearLayoutManager cate = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);


        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(newsadapter);
        categorylist.setLayoutManager(cate);
        categorylist.setAdapter(catAdapter);
        getCategories();

        if (isNetworkConnected()){

            getNews("All");
            Snackbar.make(logout, "Resfesh For More", Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar.make(logout, "No Network", Snackbar.LENGTH_LONG).show();
        }
        catAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkConnected()){
                index = random.nextInt(words.length);
                getNews(words[index]);
                newsadapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                }
                else{
                    Snackbar.make(logout, "No Internet", Snackbar.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }


            }

        });

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        String userid = firebaseUser.getUid();

        databaseReference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user userprofile = snapshot.getValue(user.class);

                if (userprofile != null){
                    String fullName = "Hey " + userprofile.fullName;

                    uservalue.setText(fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(uservalue, "Server Crashed", Snackbar.LENGTH_LONG).show();
            }
        });


    }

    public void getCategories() {
        categoryArrayList.add(new category("Movies"));
        categoryArrayList.add(new category("Crime"));
        categoryArrayList.add(new category("Corona"));
        categoryArrayList.add(new category("Science"));
        categoryArrayList.add(new category("Billionaires"));
        categoryArrayList.add(new category("Startup"));
        categoryArrayList.add(new category("World"));
        categoryArrayList.add(new category("Health"));
        categoryArrayList.add(new category("Technology"));
        categoryArrayList.add(new category("Finance"));
        categoryArrayList.add(new category("Politics"));
        categoryArrayList.add(new category("All"));

    }


    private void getNews(String types) {
        articlesArrayList.clear();
        String url = "https://newsapi.org/v2/top-headlines?country=in&category=general&apiKey=c1765de2161c46c49ccfa20000d92cf9";
        String categoryUrl = "https://newsapi.org/v2/everything?q=" + types + " india&language=en&apiKey=c1765de2161c46c49ccfa20000d92cf9";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPi retrofitAPi = retrofit.create(RetrofitAPi.class);

        Call<list> call;
        if (types.equals("All")) {
            call = retrofitAPi.getAllNews(url);
        } else if (types.equals("World")) {
            call = retrofitAPi.getAllNews("https://newsapi.org/v2/top-headlines?country=us&apiKey=c1765de2161c46c49ccfa20000d92cf9");
        } else {
            call = retrofitAPi.getAllNews(categoryUrl);
        }


        call.enqueue(new Callback<list>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<list> call, Response<list> response) {

                list list = response.body();
                progressBar.setVisibility(View.GONE);
                ArrayList<articles> articles = list.getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    articlesArrayList.add(new articles(articles.get(i).getTitle(), articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(), articles.get(i).getContent(), articles.get(i).getUrl()));

                }
                newsadapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<list> call, Throwable t) {

            }
        });
    }

    @Override
    public void OnCategoryClick(int position) {
        String pos = categoryArrayList.get(position).getCatext();
        getNews(pos);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logout) {
//            case R.id.logout:
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(this, LoginUser.class));
//                finish();
                startActivity(new Intent(this, UserPost.class));
                Snackbar.make(logout, "Feeling Sad", Snackbar.LENGTH_LONG).show();

        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}