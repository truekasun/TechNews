package me.kasun.technews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by kasun on 2016-05-23.
 */
public class News {
    public String title;
    public String description;
    public String imageUrl;
    public String newsUrl;
    public String label;

    static ArrayList headlines = new ArrayList();
    static ArrayList links = new ArrayList();

    public static ArrayList<News> getNewsFromFile(){
      final ArrayList<News> newsList = new ArrayList<>();
        try {
            URL url = new URL("http://news.softpedia.com/newsRSS/Global-0.xml");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(getInputStream(url), "UTF_8");
            boolean insideItem = false;

            int eventType = xpp.getEventType();

            News tmpNewsItem = new News();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    switch (xpp.getName()){
                        case "item":
                            insideItem = true;
                            break;
                        case "title":
                            tmpNewsItem.title = xpp.nextText();
                            break;
                        case "link":
                            tmpNewsItem.newsUrl = xpp.nextText();
                            break;
                        case "description":
                            tmpNewsItem.description = xpp.nextText();
                            break;
                        case "media:thumbnail":
                            tmpNewsItem.imageUrl = xpp.getAttributeValue(null,"url");
                            break;
                    }

                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    newsList.add(tmpNewsItem);
                    tmpNewsItem = new News();
                    insideItem = false;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    public static InputStream getInputStream(URL url) {
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            return url.openConnection().getInputStream();

        } catch (IOException e) {
            return null;
        }

    }
}
