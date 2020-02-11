package com.example.masproposal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.masproposal.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddTvAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> listImg;
    ArrayList<String> listName;
    ArrayList<String> listUrl;


    public AddTvAdapter(Context context, ArrayList<String> listImg, ArrayList<String> listName, ArrayList<String> listUrl) {
        this.context = context;
        this.listImg = listImg;
        this.listName = listName;
        this.listUrl = listUrl;
    }

    @Override
    public int getCount() {
        return listUrl.size();
    }

    @Override
    public Object getItem(int i) {
        return listUrl.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.tv_list,viewGroup,false);
        TextView tv_name=view.findViewById(R.id.tv_name);
        ImageView poster=view.findViewById(R.id.tv_poster);
        tv_name.setText(""+listName.get(i));
        Picasso.get().load(listImg.get(i)).resize(372,550).into(poster);
        return view;
    }
}
