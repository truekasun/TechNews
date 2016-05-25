package me.kasun.technews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kasun on 2016-05-23.
 */
public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<News> mDataSource;

    public NewsAdapter(Context context, ArrayList<News> items){
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = mInflater.inflate(R.layout.list_item_news,parent,false);
        TextView titleTextView = (TextView)rowView.findViewById(R.id.news_list_title);
        TextView subtitleTextView = (TextView)rowView.findViewById(R.id.rnews_list_subtitle);
        ImageView thumbnailImageView = (ImageView)rowView.findViewById(R.id.news_list_thumbnail);

        News newsItem = (News)getItem(position);

        titleTextView.setText(newsItem.title);
        subtitleTextView.setText(newsItem.description);

        Picasso.with(mContext).load(newsItem.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);

        return rowView;
    }
}
