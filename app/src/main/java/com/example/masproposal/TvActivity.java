package com.example.masproposal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.masproposal.adapters.AddTvAdapter;
import com.example.masproposal.database.Database;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TvActivity extends AppCompatActivity {
    Database mDatabase;
    private static String name;
    private static String href,tv_page_url;
    private static String url;
    Random r = new Random();
    ImageView poster;
    CheckBox checkBox;
    TextView tv_name_text,rate_text,duration_text,summary_text,season_text;
    ListView listView;

    EditText editText;
    Button add_tv,tv_button,save,button_gec;
    Document document;
    Elements tv_url,movie,divs,tv_names;
    Element div,number,tv;
    String tv_name,rate,duration,poster_url,summary,episode_poster,season,rand_ep_poster;
    String watched= "FALSE";
    String fav= "FALSE";

    String for_img,for_name;
    String for_tv_url="https://www.imdb.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        mDatabase=new Database(this);
        def_inter_elements();

        add_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=editText.getText().toString();
                name=name.replace(" ","+");
                getMovie(name);



            }
        });



    }
    public void getMovie(String name) {

        ArrayList<String> listDataImg = new ArrayList<>();
        ArrayList<String> listDataName = new ArrayList<>();
        ArrayList<String> listDataUrl = new ArrayList<>();


        try {

                url="https://www.imdb.com/find?q="+name+"&s=tt&ttype=tv&ref_=fn_tv";

                document= Jsoup.connect(url).get();
                tv_url = document.getElementsByClass("primary_photo").select("a");
                tv_names=document.getElementsByClass("result_text");
             for(int i=0;i<tv_url.size()/(tv_url.size()/3);i++) {
                 for_tv_url="https://www.imdb.com";
                 for_img = tv_url.get(i).select("img").attr("src");
                 for_name = tv_names.get(i).text();
                 for_tv_url+=tv_url.get(i).attr("href");


                 String[] arrOfurl = for_img.replace(".", "~").split("~");
                 String new_url = "";
                 int c = arrOfurl.length - 2;
                 for (int url = 0; url < arrOfurl.length; url++) {
                     if (arrOfurl[url] != arrOfurl[arrOfurl.length - 2]) {
                         System.out.println(arrOfurl[url]);
                         new_url += arrOfurl[url];
                         if (c != 0) {
                             new_url += ".";
                             c--;
                         }
                     }
                 }
                 for_img=new_url;
                 listDataImg.add(for_img);
                 listDataName.add(for_name);
                 listDataUrl.add(for_tv_url);
                 Log.i("tv4", "" + for_img);
                 Log.i("tv4", "" + for_name);
                 Log.i("tv4", "" + for_tv_url);

             }
            AddTvAdapter adapter=new AddTvAdapter(getApplicationContext(),listDataImg,listDataName,listDataUrl);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                    final ProgressDialog progressDialog=new ProgressDialog(TvActivity.this,ProgressDialog.THEME_HOLO_DARK);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("Please wait a little");
                    progressDialog.setCancelable(false);//iptal edilmesini engelliyoruz
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url=adapterView.getItemAtPosition(i).toString();
                            try {
                                document= Jsoup.connect(url).get();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            movie=document.getElementsByClass("title_bar_wrapper");
                            tv_name=movie.select("h1").text();


                            movie=document.getElementsByClass("ratingValue");
                            rate=movie.select("span").text();

                            movie=document.getElementsByClass("poster");
                            poster_url=movie.select("img").attr("src");


                            summary=document.getElementsByClass("summary_text").text();
                            movie=document.getElementsByClass("subtext");
                            duration=movie.select("time").text();
                            div=document.getElementsByClass("seasons-and-year-nav").select("div").get(3);
                            season=div.child(0).text();
                            episode_poster=div.child(Integer.parseInt(season)-(Integer.parseInt(season)-1)).attr("href");
                            try {
                                document=Jsoup.connect("https://www.imdb.com"+episode_poster).get();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            rand_ep_poster=document.getElementsByClass("image").select("img").get(0).attr("src");


                            String[] arrOfurl = rand_ep_poster.replace(".", "~").split("~");
                            String new_url = "";


                            int c = arrOfurl.length - 2;
                            for (int x = 0; x < arrOfurl.length; x++) {
                                if (arrOfurl[x] != arrOfurl[arrOfurl.length - 2]) {

                                    new_url += arrOfurl[x];
                                    if (c != 0) {
                                        new_url += ".";
                                        c--;
                                    }
                                }

                            }
                            rand_ep_poster=new_url;
                            Log.i("show",""+tv_name);
                            Log.i("show",""+rate);
                            Log.i("show",""+duration);
                            Log.i("show",""+season);
                            Log.i("show",""+summary);
                            Log.i("show",""+poster_url);
                            Log.i("show",""+url);
                            Log.i("show",""+rand_ep_poster);
                            Intent editScreenIntent = new Intent(TvActivity.this, TvSaveActivity.class);
                            editScreenIntent.putExtra("name",tv_name);
                            editScreenIntent.putExtra("rate",rate);
                            editScreenIntent.putExtra("duration",duration);
                            editScreenIntent.putExtra("summary",summary);
                            editScreenIntent.putExtra("poster_url",poster_url);
                            editScreenIntent.putExtra("tv_page_url",url);
                            editScreenIntent.putExtra("season",season);
                            editScreenIntent.putExtra("rand_ep_poster",rand_ep_poster);
                            startActivity(editScreenIntent);


                            progressDialog.cancel();

                        }
                    }).start();
                }});
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    public void def_inter_elements(){
        listView=findViewById(R.id.add_tv_list);
        add_tv=findViewById(R.id.add);
        editText=findViewById(R.id.edit_text);

    }


}
