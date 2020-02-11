package com.example.masproposal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.masproposal.database.Database;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Database mDatabase;
    private static String name;
    private static String href,movie_page_url;
    private static String url;
    Random r = new Random();
    ImageView poster,imdb_logo,poster_bg;
    CheckBox checkBox;
    TextView movie_name_text,rate_text,duration_text,summary_text,add_movie,info1,info2,hint;
    LinearLayout add_or_later;
    EditText editText;
    Document document;
    Elements movie_url,movie;
    String movie_name,rate,duration,poster_url,summary,user_summary;
    String watched= "FALSE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase=new Database(this);
        def_inter_elements();



        add_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add_movie.getText().toString()=="Add This Movie"){
                    progressDialogAdding();
                    LayoutInflater inflater= getLayoutInflater();
                    View message_view=inflater.inflate(R.layout.successful_dialog,null);

                    TextView message=message_view.findViewById(R.id.successful_text);
                    message.setText(movie_name+" added successfully");
                    final AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                    alert.setView(message_view);
                    final AlertDialog dialog=alert.create();
                    dialog.show();

                    editText.setText("");
                    add_movie.setText("See Movie");
                    hint.setText("(click the title to see)");
                    editText.setVisibility(View.VISIBLE);


                }
                else {
                    name = editText.getText().toString();
                    progressDialogLoading();
                    name = name.replace(" ", "+");
                    new getMovie().execute();
                    checkBox.setText("Did you watched this movie ?");
                    add_movie.setText("Add This Movie");
                    hint.setText("(click the title to add movie)");

                    setVisibility();

                }




            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(watched=="FALSE"){
                    watched="TRUE";
                }
                else{
                    watched="FALSE";
                }


            }
        });



    }
    public void progressDialogLoading(){
        final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this,ProgressDialog.THEME_HOLO_DARK);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Movie is loading");
        progressDialog.setCancelable(false);//iptal edilmesini engelliyoruz
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressDialog.cancel();

            }
        }).start();
    }
    public void progressDialogAdding(){
        final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this,ProgressDialog.THEME_HOLO_DARK);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Adding "+movie_name);
        progressDialog.setMessage("Please wait a little");
        progressDialog.setCancelable(false);//iptal edilmesini engelliyoruz
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                mDatabase.addMovie(movie_name,rate,duration,summary,poster_url,movie_page_url,watched);

                progressDialog.cancel();

            }
        }).start();
    }



    public class getMovie extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                url="https://www.imdb.com/find?q="+name+"&s=tt&ttype=ft&ref_=fn_ft";
                document=Jsoup.connect(url).get();
                movie_url=document.getElementsByClass("primary_photo").select("a");
                href=movie_url.attr("href");
                movie_page_url="https://www.imdb.com"+href;
                document=Jsoup.connect(movie_page_url).get();
                movie=document.getElementsByClass("title_bar_wrapper");
                movie_name=movie.select("h1").text();



                movie=document.getElementsByClass("ratingValue");
                rate=movie.select("span").text();

                movie=document.getElementsByClass("poster");
                poster_url=movie.select("img").attr("src");

                summary=document.getElementsByClass("summary_text").text();
                movie=document.getElementsByClass("subtext");
                duration=movie.select("time").text();









                String[] arrOfurl = poster_url.replace(".","~").split("~");
                String new_url="";

                int c=arrOfurl.length-2;
                for (int i=0;i<arrOfurl.length;i++){
                    if(arrOfurl[i]!=arrOfurl[arrOfurl.length-2]){
                        System.out.println(arrOfurl[i]);
                        new_url+=arrOfurl[i];
                        if(c!=0){
                            new_url+=".";
                            c--;
                        }
                    }

                }
                poster_url=new_url;







            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.i("kaynak",""+movie_name);
            Log.i("kaynak",""+rate);
            Log.i("kaynak",""+poster_url);
            Log.i("kaynak",""+href);
            Log.i("kaynak",""+movie_url);
            Log.i("kaynak",""+url);


            Picasso.get().load(poster_url).resize(372,550).into(poster);
            Picasso.get().load(poster_url).into(poster_bg);
            movie_name_text.setText(""+movie_name);
            rate_text.setText(""+rate);
            duration_text.setText(""+duration);
            summary_text.setText(""+summary);




        }
    }
    public void def_inter_elements(){


        poster_bg=findViewById(R.id.poster_bg);
        poster=findViewById(R.id.poster);
        movie_name_text=findViewById(R.id.movie_name);
        rate_text=findViewById(R.id.rate);
        duration_text=findViewById(R.id.duration);
        summary_text=findViewById(R.id.summary);
        editText=findViewById(R.id.edit_text);
        add_movie=findViewById(R.id.edit_text_title);
        checkBox=findViewById(R.id.checkbox);
        hint=findViewById(R.id.hint);

    }
    public void setVisibility()  {

        poster.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.VISIBLE);
        editText.setVisibility(View.INVISIBLE);
        poster_bg.setVisibility(View.VISIBLE);
        poster_bg.setAlpha((float) 0.2);


    }
    /*
    public void setVisibilityInfo(){
        info1.setVisibility(View.GONE);
        info2.setVisibility(View.GONE);
        imdb_logo.setVisibility(View.GONE);
    }

     */
    public void movie_of_the_day(){
        Date date=new Date();
        Timer timer = new Timer();

        timer.schedule(new TimerTask(){
            public void run(){
                Random rand = new Random();

// Obtain a number between [0 - 49].
                int n = rand.nextInt(10);
                if (n==0){
                    n=1;
                }
                url="https://www.imdb.com/search/title/?groups=top_1000&sort=user_rating,desc&count=100&start="+n+"01&ref_=adv_nxt";
                try {
                    document=Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                movie_url=document.getElementsByClass("primary_photo").select("a");
                href=movie_url.attr("href");
                movie_page_url="https://www.imdb.com"+href;
                try {
                    document=Jsoup.connect(movie_page_url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                movie=document.getElementsByClass("title_bar_wrapper");
                movie_name=movie.select("h1").text();



                movie=document.getElementsByClass("ratingValue");
                rate=movie.select("span").text();

                movie=document.getElementsByClass("poster");
                poster_url=movie.select("img").attr("src");

                summary=document.getElementsByClass("summary_text").text();
                movie=document.getElementsByClass("subtext");
                duration=movie.select("time").text();

            }
        },date, 24*60*60*1000);

    }



}


