package com.cli.login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPi {

    @GET
    Call<list> getAllNews(@Url String url);

    Call<list> getCategoryNews(@Url String url);

}
