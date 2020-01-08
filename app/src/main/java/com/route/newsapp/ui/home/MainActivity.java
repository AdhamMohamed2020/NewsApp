package com.route.newsapp.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.route.NewsApp.R;
import com.route.newsapp.NewsAdapter;
import com.route.newsapp.model.NewsResponse.ArticlesItem;
import com.route.newsapp.model.sourcesResponse.SourcesItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected TabLayout tablayout;
    protected RecyclerView recyclerView;
    protected ProgressBar progressBar;
    protected View noData;
    NewsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    HomeViewModel homeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeViewModel = ViewModelProviders.of(this)
                .get(HomeViewModel.class);
        initView();
        initRecyclerView();
        subscribeToLiveData();
        homeViewModel.getNewsSources();
    }

    public void subscribeToLiveData(){
        homeViewModel.sourcesList.observe(this, new Observer<List<SourcesItem>>() {
            @Override
            public void onChanged(List<SourcesItem> sourcesItems) {
                initTabLayout(sourcesItems);
            }
        });
        homeViewModel.newsList.observe(this, new Observer<List<ArticlesItem>>() {
            @Override
            public void onChanged(List<ArticlesItem> articlesItems) {
                adapter.changeData(articlesItems);
            }
        });
        homeViewModel.message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        homeViewModel.showProgressBar.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldShow) {
                if (shouldShow)
                    progressBar.setVisibility(View.VISIBLE);
                else progressBar.setVisibility(View.GONE);
            }
        });
    }
    public void initRecyclerView(){
        adapter =new NewsAdapter(null);
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initTabLayout(List<SourcesItem> sourcesItems) {
        for(SourcesItem source: sourcesItems){
           TabLayout.Tab tab =  tablayout.newTab();
           tab.setText(source.getName());
           tab.setTag(source);
           tablayout.addTab(tab);
        }
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SourcesItem sourcesItem = ((SourcesItem) tab.getTag());
                getNewsBySourceId(sourcesItem.getId());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                SourcesItem sourcesItem = ((SourcesItem) tab.getTag());
                getNewsBySourceId(sourcesItem.getId());

            }
        });
        tablayout.getTabAt(0).select();
    }

    public void getNewsBySourceId(String sourceID){
        homeViewModel.getNewsBySourceId(sourceID);
    }
    private void initView() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        noData =  findViewById(R.id.no_data_view);
    }
}
