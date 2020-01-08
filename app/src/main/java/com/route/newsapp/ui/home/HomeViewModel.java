package com.route.newsapp.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.route.newsapp.Constants;
import com.route.newsapp.api.ApiManager;
import com.route.newsapp.model.NewsResponse.ArticlesItem;
import com.route.newsapp.model.NewsResponse.NewsResponse;
import com.route.newsapp.model.sourcesResponse.SourcesItem;
import com.route.newsapp.reposotries.SourcesRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    MutableLiveData<List<SourcesItem>> sourcesList ;
    MutableLiveData<List<ArticlesItem>> newsList =new MutableLiveData<>();
    MutableLiveData<String> message =new MutableLiveData<>();
    MutableLiveData<Boolean> showProgressBar =new MutableLiveData<>();
    SourcesRepository repository =new SourcesRepository();
    public HomeViewModel(){
        sourcesList = repository.sources;
    }
    public void getNewsSources(){
        showProgressBar.setValue(true);
        repository.getSources();
    }

    public void getNewsBySourceId(String sourceID) {
        showProgressBar.setValue(true);
        ApiManager.getApis()
                .getNewsBySourceId(Constants.API_KEY,sourceID)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call,
                                           Response<NewsResponse> response) {
                        showProgressBar.setValue(false);
                        if("ok".equals(response.body().status)){
                            newsList.setValue(response.body().articles);
                        }else {
                            message.setValue(response.body().message);
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        showProgressBar.setValue(false);
                        message.setValue(t.getLocalizedMessage());
                    }
                });
    }
}
