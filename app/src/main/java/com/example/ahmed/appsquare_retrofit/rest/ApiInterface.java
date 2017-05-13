package com.example.ahmed.appsquare_retrofit.rest;

import com.example.ahmed.appsquare_retrofit.model.RepoResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ahmed on 5/10/2017.
 */

public interface ApiInterface {


    @GET("users/square/repos")
    Call<List<RepoResult>> getRepos(@Query("page") int pageIndex,@Query("per_page") int perPage);


}
