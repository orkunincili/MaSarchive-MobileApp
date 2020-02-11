package com.example.masproposal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.masproposal.database.Database;
import com.squareup.picasso.Picasso;

public class TvSaveActivity extends AppCompatActivity {
    private String name,rate,duration,summary,poster_url,tv_page_url,season,rand_ep_poster,user_summary;
    private TextView name_text,rate_text,duration_text,summary_text,season_text;
    private ImageView poster;
    private Button save_button;
    private String watched="FALSE";
    private String fav="FALSE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_save);
        final Database mDatabase=new Database(this);

        getDatawIntent();
        def_elements();
        Picasso.get().load(rand_ep_poster).into(poster);
        name_text.setText(""+name);
        rate_text.setText("Rate: "+rate);
        duration_text.setText("Duration: "+duration);
        season_text.setText("Seasons: "+season);
        summary_text.setText(""+summary);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.addTv(name,rate,duration,summary,poster_url,tv_page_url,season,watched,fav,rand_ep_poster,user_summary);
            }
        });





    }
    public void def_elements(){
        poster=findViewById(R.id.tv_poster);
        poster.setScaleType(ImageView.ScaleType.FIT_XY);
        name_text=findViewById(R.id.tv_name);
        rate_text=findViewById(R.id.rate);
        duration_text=findViewById(R.id.duration);
        season_text=findViewById(R.id.season);
        summary_text=findViewById(R.id.summary);
        save_button=findViewById(R.id.save);
    }
    public void getDatawIntent(){
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        rate=intent.getStringExtra("rate");
        duration=intent.getStringExtra("duration");
        summary=intent.getStringExtra("summary");
        poster_url=intent.getStringExtra("poster_url");
        tv_page_url=intent.getStringExtra("tv_page_url");
        season=intent.getStringExtra("season");
        rand_ep_poster=intent.getStringExtra("rand_ep_poster");

    }

}
