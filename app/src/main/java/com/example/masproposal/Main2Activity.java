package com.example.masproposal;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.masproposal.classes.RandomTv;
import com.example.masproposal.database.Database;
import com.jgabrielfreitas.core.BlurImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.BlurTransformation;


public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";
    Database mDatabase;
    TextView rand_name,
             rand_rate,
             rand_duration,
             rand_episodes,
             movies,
             tv_series,
             menu_add_movie,
             menu_add_tv,
             rand_summary;


    ImageView rand_poster,rand_poster_small;


    int clickTv=0;
    static int mos=0;

    LinearLayout add_tv_now,infos;

    ArrayList <String> dataRandomTv =new ArrayList<>();

    RandomTv randomTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*android.os.NetworkOnMainThreadException hatası için sonraki iki satır eklendi*/
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        def_random_tv_elements();
        def_menu();
        mDatabase = new Database(this);
        randomTv =new RandomTv();
        randomTv.get_random_series(dataRandomTv);
        rand_name.setText("" + dataRandomTv.get(0));
        rand_rate.setText("" + dataRandomTv.get(1));
        rand_duration.setText("" + dataRandomTv.get(2));
        rand_episodes.setText("" + dataRandomTv.get(3));
        Picasso.get().load(dataRandomTv.get(4)).into(rand_poster);
        rand_summary.setText(""+dataRandomTv.get(5));




        listViewMovie();
        listViewTv();
        rand_poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickTv==0){

                    Picasso.get().load(dataRandomTv.get(4)).transform(new BlurTransformation(Main2Activity.this, 20, 1)).into(rand_poster);
                    Picasso.get().load(dataRandomTv.get(4)).into(rand_poster_small);
                    rand_name.setVisibility(View.VISIBLE);
                    add_tv_now.setVisibility(View.VISIBLE);
                    infos.setVisibility(View.VISIBLE);
                    rand_poster_small.setVisibility(View.VISIBLE);
                    rand_summary.setVisibility(View.VISIBLE);
                    rand_poster.setAlpha((float) 0.2);
                    clickTv=1;

                }
                else{
                    clickTv=0;
                    Picasso.get().load(dataRandomTv.get(4)).into(rand_poster);
                    rand_name.setVisibility(View.GONE);
                    add_tv_now.setVisibility(View.GONE);
                    infos.setVisibility(View.GONE);
                    rand_poster.setAlpha((float) 1.0);
                    rand_poster_small.setVisibility(View.GONE);
                    rand_summary.setVisibility(View.GONE);

                }

            }
        });
        add_tv_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createIntent(TvSaveActivity.class);
            }
        });
        movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createIntent(ListDataActivity.class,mos);
            }
        });
        tv_series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mos = 1;
                createIntent(ListDataActivity.class,mos);

            }
        });
        menu_add_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createIntent(MainActivity.class);
            }
        });
        menu_add_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createIntent(TvActivity.class);

            }
        });

    }



    public void def_random_tv_elements(){

        rand_poster=findViewById(R.id.random_tv_poster);
        rand_poster.setScaleType(ImageView.ScaleType.FIT_XY);
        rand_name=findViewById(R.id.random_tv_name);
        rand_rate=findViewById(R.id.random_tv_rate);
        rand_duration=findViewById(R.id.random_tv_duration);
        rand_episodes=findViewById(R.id.episodes);
        add_tv_now=findViewById(R.id.add_tv_linear);
        movies=findViewById(R.id.movies);
        tv_series=findViewById(R.id.tv_series);
        infos=findViewById(R.id.linear);
        rand_poster_small=findViewById(R.id.random_tv_poster_small);
        rand_summary=findViewById(R.id.summary);

    }

    public void def_menu(){
        menu_add_movie=findViewById(R.id.add_movie_menu);
        menu_add_tv=findViewById(R.id.add_tv_menu);
    }




    private void listViewMovie() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mDatabase.getWatched("FALSE");
        ArrayList<String> listData = new ArrayList<>();

        while(data.moveToNext()){
            //get the value from the database in column 5
            //then add it to the ArrayList
            listData.add(data.getString(5));

        }





    }
    private void listViewTv() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mDatabase.getDatatv();
        ArrayList<String> listDataTv = new ArrayList<>();

        while(data.moveToNext()){
            //get the value from the database in column 5
            //then add it to the ArrayList
            listDataTv.add(data.getString(5));

        }


    }

    public void createIntent(Class where){

        Intent intent = new Intent(Main2Activity.this,where);
        startActivity(intent);
    }
    public void createIntent(Class where,int mos){
        Intent intent = new Intent(Main2Activity.this,where);
        intent.putExtra("mos",mos);
        startActivity(intent);

    }

}
