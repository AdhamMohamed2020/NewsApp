package com.route.newsapp.reposotries;

import androidx.lifecycle.MutableLiveData;

import com.route.newsapp.Constants;
import com.route.newsapp.api.ApiManager;
import com.route.newsapp.database.MyDataBase;
import com.route.newsapp.model.sourcesResponse.SourcesItem;
import com.route.newsapp.model.sourcesResponse.SourcesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourcesRepository {
    public MutableLiveData<List<SourcesItem>> sources
            =new MutableLiveData<>();

    public void getSources(){
        ApiManager.getApis()
                .getNewsSource(Constants.API_KEY)
                .enqueue(new Callback<SourcesResponse>() {
                    @Override
                    public void onResponse(Call<SourcesResponse> call, Response<SourcesResponse> response) {
                        // save data in cache ex. room
                        cacheSources(response.body().getSources());
                        sources.setValue(response.body().getSources());
                    }

                    @Override
                    public void onFailure(Call<SourcesResponse> call, Throwable t) {
                        getSourcesFromCache();
                    }
                });
    }

    private void getSourcesFromCache() {
        GetSourcesThread th=new GetSourcesThread();
        th.start();
    }

    private void cacheSources(final List<SourcesItem> sources) {

        Thread th = new Thread(){
            @Override
            public void run() {
                super.run();
                MyDataBase.getInstance().
                        sourcesDao().
                        addAllSources(sources);
            }
        };
        th.start();
    }

    public class GetSourcesThread extends Thread{

        @Override
        public void run() {
            super.run();
            List<SourcesItem>sourcesList =
                    MyDataBase.getInstance()
                            .sourcesDao()
                            .getAllSources();
            sources.postValue(sourcesList);
        }
    }
}
