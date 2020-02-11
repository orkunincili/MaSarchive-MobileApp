package com.example.masproposal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.masproposal.adapters.ImageAdapter;
import com.example.masproposal.database.Database;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {
    private static final String TAG = "ListDataActivity";

    Database mDatabase;
    Cursor data;
    static int mos;
    String movie_name,rate,duration,summary,poster_url,movie_page_url,watched,notes;
    private GridView mGridView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mGridView =  findViewById(R.id.gridView);
        mDatabase = new Database(this);
        Intent intent=getIntent();
        mos=intent.getIntExtra("mos",1);

        populateListView(mos);
    }

    private void populateListView(int mos) {


        if(mos==0) {
            data = mDatabase.getData();
        }
        else{

            data=mDatabase.getDatatv();
        }

        ArrayList<String> listData = new ArrayList<>();

        while(data.moveToNext()){
            //get the value from the database in column 5
            //then add it to the ArrayList
            listData.add(data.getString(5));


        }
        //create the list adapter and set the adapter
        ImageAdapter adapter=new ImageAdapter(getApplicationContext(),listData);

        mGridView.setAdapter(adapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name=adapterView.getItemAtPosition(i).toString();


                data = mDatabase.getItemID(name);

                int itemID = -1;
                while(data.moveToNext()){

                    itemID = data.getInt(0);
                    movie_name = data.getString(1);
                    rate = data.getString(2);
                    duration = data.getString(3);
                    summary = data.getString(4);
                    poster_url = data.getString(5);
                    movie_page_url=data.getString(6);
                    watched=data.getString(7);
                    notes=data.getString(8);



                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);

                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditMovieActivity.class);

                    editScreenIntent.putExtra("name",movie_name);
                    editScreenIntent.putExtra("rate",rate);
                    editScreenIntent.putExtra("duration",duration);
                    editScreenIntent.putExtra("summary",summary);
                    editScreenIntent.putExtra("poster_url",poster_url);
                    editScreenIntent.putExtra("movie_page_url",movie_page_url);
                    editScreenIntent.putExtra("watched",watched);
                    editScreenIntent.putExtra("notes",notes);

                    startActivity(editScreenIntent);

                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });

    }

    public void createIntent(Class where){
        Intent editScreenIntent = new Intent(ListDataActivity.this, where);

        editScreenIntent.putExtra("name",movie_name);
        editScreenIntent.putExtra("rate",rate);
        editScreenIntent.putExtra("duration",duration);
        editScreenIntent.putExtra("summary",summary);
        editScreenIntent.putExtra("poster_url",poster_url);
        editScreenIntent.putExtra("movie_page_url",movie_page_url);
        editScreenIntent.putExtra("watched",watched);

        startActivity(editScreenIntent);

    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


}
