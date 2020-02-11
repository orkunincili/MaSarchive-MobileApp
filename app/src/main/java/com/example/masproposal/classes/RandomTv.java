package com.example.masproposal.classes;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomTv {
    String url,random_tv,rand_tv_name,rand_tv_rate,
            rand_tv_duration,rand_tv_poster,episodes,
            episodes_url,rand_main_poster,summary;
    Document document;
    Elements tv_urls,random_tvs;

    public ArrayList<String> get_random_series(ArrayList<String> list)  {


        Random rand = new Random();
        int p = rand.nextInt(10);
        String base_imdb="https://www.imdb.com";
         url = "https://www.imdb.com/search/title/?title_type=tv_series&count=100&start=" + p + "01&ref_=adv_nxt";
        try {
            document= Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tv_urls = document.getElementsByClass("lister-item-header");
        random_tvs=tv_urls.select("a");

        random_tv=random_tvs.get(rand.nextInt(random_tvs.size())).attr("href");

        base_imdb+=random_tv;

        try {
            document= Jsoup.connect(base_imdb).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        rand_tv_name = document.getElementsByClass("title_wrapper").select("h1").text();
        rand_tv_rate = document.getElementsByClass("ratingValue").select("span").text();
        rand_tv_duration = document.getElementsByClass("subtext").select("time").text();
        summary=document.getElementsByClass("summary_text").text();
        rand_main_poster=document.getElementsByClass("poster").select("img").attr("src");
        episodes = document.getElementsByClass("bp_description").select("span").text();
        episodes_url=random_tv.replace("?ref_=adv_li_tt","episodes?ref_=tt_ov_epl");

        try {
            document=Jsoup.connect("https://www.imdb.com"+episodes_url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        rand_tv_poster=document.getElementsByClass("image").select("img").attr("src");
        Log.i("kaynak",rand_main_poster);
        String[] arrOfurl = rand_main_poster.replace(".", "~").split("~");
        String new_url = "";

        int c = (arrOfurl.length) - 2;
        for (int x = 0; x < arrOfurl.length; x++) {
            if (arrOfurl[x] != arrOfurl[arrOfurl.length - 2]) {

                new_url += arrOfurl[x];
                if (c != 0) {
                    new_url += ".";
                    c--;
                }
            }

        }
        String new_rand_tv_poster=new_url;
        list.add(rand_tv_name);
        list.add(rand_tv_rate);
        list.add(rand_tv_duration);
        list.add(episodes);
        list.add(new_rand_tv_poster);
        list.add(summary);

        return list;
    }
}
