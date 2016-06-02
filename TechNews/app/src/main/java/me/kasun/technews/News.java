package me.kasun.technews;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Created by kasun on 2016-05-23.
 */
public class News implements Comparable<News> {
    public Integer id;
    public String title;
    public String description;
    public String imageUrl;
    public String newsUrl;
    public String newsDate;


    public static void syncNewsFromWeb(Context context) {
        if (NetworkHelper.getConnectivityStatus(context)) {
            DBHelper techNewsDb = new DBHelper(context);
            try {
                URL url = new URL("http://news.softpedia.com/newsRSS/Global-0.xml");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();
                News tmpNewsItem = new News();
                int newNewsCount = 0;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        switch (xpp.getName()) {
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
                                tmpNewsItem.imageUrl = xpp.getAttributeValue(null, "url");
                                break;
                            case "pubDate":
                                tmpNewsItem.newsDate = xpp.nextText();
                                break;
                        }

                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        long result = techNewsDb.insertNews(tmpNewsItem.title, tmpNewsItem.description, tmpNewsItem.imageUrl, tmpNewsItem.newsUrl,tmpNewsItem.newsDate);
                        if (result > 0) {
                            newNewsCount++;
                        }
                        tmpNewsItem = new News();
                        insideItem = false;
                    }
                    eventType = xpp.next();
                }
                Toast.makeText(context, (newNewsCount > 0) ? newNewsCount + " News successfully updated!" : "You are already up to date!", Toast.LENGTH_SHORT).show();

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Network connection required!", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public int compareTo(News newsObj) {
        return newsDate.compareTo(newsObj.newsDate);
    }
}
