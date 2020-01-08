package com.route.newsapp.api;

import com.route.newsapp.model.NewsResponse.NewsResponse;
import com.route.newsapp.model.sourcesResponse.SourcesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WebServices {

    @GET("sources")
    Call<SourcesResponse> getNewsSource(@Query("apiKey") String apiKey);

    @GET("everything")
    Call<NewsResponse> getNewsBySourceId(@Query("apiKey") String apiKey,
                                         @Query("sources") String sources
    );

 }
