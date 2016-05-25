package me.kasun.technews;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView =(ListView) findViewById(R.id.news_list_view);

        final ArrayList<News> newsList = News.getNewsFromFile();
        NewsAdapter adapter = new NewsAdapter(this,newsList);
        mListView.setAdapter(adapter);

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            final ArrayList<News> newsList = News.getNewsFromFile();
                            NewsAdapter adapter = new NewsAdapter(getApplicationContext(),newsList);
                            mListView.setAdapter(adapter);

                            mSwipeRefreshLayout.setRefreshing(false);
                        }},2000);
                }

            });
        }

        final Context context = this;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position,long id){
                News selectedNews = newsList.get(position);
                Intent detailIntent = new Intent(context,NewsDetailActivity.class);
                detailIntent.putExtra("title",selectedNews.title);
                detailIntent.putExtra("url",selectedNews.newsUrl);
                startActivity(detailIntent);
            }
        });

    }
}
