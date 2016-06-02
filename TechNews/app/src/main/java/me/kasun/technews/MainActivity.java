package me.kasun.technews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHelper techNewsDb;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private ArrayList<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_technews_logo_white);

        setContentView(R.layout.activity_main);
        setNews(this);
        Toast.makeText(this, "Swipe down to get the latest news!", Toast.LENGTH_SHORT).show();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.google_blue, R.color.google_yellow, R.color.google_green);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                }
            });
        }
        mListView = (ListView) findViewById(R.id.news_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News selectedNews = newsList.get(position);
                Intent detailIntent = new Intent(getApplicationContext(), NewsDetailActivity.class);
                detailIntent.putExtra("title", selectedNews.title);
                detailIntent.putExtra("url", selectedNews.newsUrl);
                startActivity(detailIntent);
            }
        });

    }

    private void refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        News.syncNewsFromWeb(getApplicationContext());
                        setNews(getApplicationContext());
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void setNews(Context context) {
        techNewsDb = new DBHelper(context);
        newsList = techNewsDb.getNews();
        mListView = (ListView) findViewById(R.id.news_list_view);
        NewsAdapter adapter = new NewsAdapter(getApplicationContext(), newsList);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
