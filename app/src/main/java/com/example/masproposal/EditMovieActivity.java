package com.example.masproposal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.masproposal.database.Database;
import com.squareup.picasso.Picasso;

public class EditMovieActivity extends AppCompatActivity {
    private static final String TAG = "Edit";
    TextView movie_name_text,rate_text,duration_text,summary_text,user_note_text;
    EditText user_note;
    TextView movie_summary,my_summary;
    ImageView poster,trash,poster_bg;
    FrameLayout movie_summary_frame;
    Button save_note;


    private String movie_name,rate,duration,summary,poster_url,movie_page_url,notes;
    private static String note;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        final Database mDatabase=new Database(this);
        def_elements();
        get_data_from_activity();


        final ChangeFragment changeFragment = new ChangeFragment(EditMovieActivity.this);
        if(savedInstanceState==null){
            Fragment fragment=new MovieSummary();
            Bundle bundle = new Bundle();
            bundle.putString("summary",summary);
            fragment.setArguments(bundle);
            changeFragment.changeFragment(fragment);
        }
        movie_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                movie_summary.setTextColor(Color.parseColor("#940a37"));
                my_summary.setTextColor(Color.parseColor("#ffffff"));


                Intent intent=getIntent();
                summary=intent.getStringExtra("summary");
                Fragment fragment=new MovieSummary();
                Bundle bundle = new Bundle();
                bundle.putString("summary",summary);
                fragment.setArguments(bundle);
                changeFragment.changeFragment(fragment);
                save_note.setVisibility(View.INVISIBLE);
                user_note.setVisibility(View.INVISIBLE);

            }
        });
        my_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                my_summary.setTextColor(Color.parseColor("#940a37"));
                movie_summary.setTextColor(Color.parseColor("#ffffff"));


                Color.parseColor("#940a37");
                Intent intent=getIntent();
                notes=intent.getStringExtra("notes");
                user_note.setText(notes);
                Fragment fragment=new MySummary();
                Bundle bundle = new Bundle();
                bundle.putString("notes",notes);
                fragment.setArguments(bundle);
                changeFragment.changeFragment(fragment);
                save_note.setVisibility(View.VISIBLE);
                user_note.setVisibility(View.VISIBLE);

            }
        });



        rate_text.setText(rate);
        duration_text.setText(duration);
        Picasso.get().load(poster_url).into(poster);
        Picasso.get().load(poster_url).into(poster_bg);
        save_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor data;
                note = user_note.getText().toString();
                mDatabase.addNote(movie_name,note);
                data = mDatabase.getSummary(movie_name);
                notes=data.getString(0);
                Fragment fragment=new MySummary();
                Bundle bundle = new Bundle();
                bundle.putString("notes",note);
                fragment.setArguments(bundle);
                changeFragment.changeFragment(fragment);
                finish();
                startActivity(getIntent());



            }
        });




    }

    public void def_elements(){
        movie_name_text=findViewById(R.id.movie_name);
        rate_text=findViewById(R.id.rate);
        duration_text=findViewById(R.id.duration);
        summary_text=findViewById(R.id.summary);
        poster=findViewById(R.id.poster);
        trash=findViewById(R.id.delete);
        user_note=findViewById(R.id.user_note);
        save_note=findViewById(R.id.save_note);
        poster_bg=findViewById(R.id.poster_bg);
        movie_summary=findViewById(R.id.movie_summary);
        my_summary=findViewById(R.id.my_summary);
        user_note_text=findViewById(R.id.user_note_text);



    }
    public void get_data_from_activity(){
        Intent intent=getIntent();
        id = intent.getIntExtra("id",-1);
        movie_name = intent.getStringExtra("name");
        rate=intent.getStringExtra("rate");
        duration=intent.getStringExtra("duration");
        summary=intent.getStringExtra("summary");
        poster_url=intent.getStringExtra("poster_url");
        movie_page_url=intent.getStringExtra("movie_poster_url");
        notes=intent.getStringExtra("notes");
        movie_name_text.setText(movie_name);
    }

    public void createIntent(Class where){
        Intent intent = new Intent(EditMovieActivity.this, where);
        startActivity(intent);

    }
}
