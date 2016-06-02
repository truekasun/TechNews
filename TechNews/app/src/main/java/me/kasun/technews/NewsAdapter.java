package me.kasun.technews;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by kasun on 2016-05-23.
 */
public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<News> mDataSource;

    public NewsAdapter(Context context, ArrayList<News> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

        View rowView = mInflater.inflate(R.layout.list_item_news, parent, false);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.news_list_title);
        TextView subtitleTextView = (TextView) rowView.findViewById(R.id.rnews_list_subtitle);
        ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.news_list_thumbnail);
        TextView timeTextView = (TextView) rowView.findViewById(R.id.news_time);

        News newsItem = (News) getItem(position);
        long startDate = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(newsItem.newsDate);

            startDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String time = (String)DateUtils.getRelativeDateTimeString(mContext,startDate,DateUtils.MINUTE_IN_MILLIS,DateUtils.WEEK_IN_MILLIS,0);
        time = time.substring(0, time.indexOf(','));;

        titleTextView.setText(newsItem.title);
        subtitleTextView.setText(newsItem.description);
        timeTextView.setText(time);

        Picasso.with(mContext).load(newsItem.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);

        return rowView;
    }
}
