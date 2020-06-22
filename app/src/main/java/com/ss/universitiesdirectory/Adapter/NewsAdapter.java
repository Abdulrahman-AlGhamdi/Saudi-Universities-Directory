package com.ss.universitiesdirectory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ss.universitiesdirectory.Model.NewsModel;
import com.ss.universitiesdirectory.R;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NewsModel> newsModels;

    public NewsAdapter(Context context, ArrayList<NewsModel> newsModels) {
        this.context = context;
        this.newsModels = newsModels;
    }

    @Override
    public int getCount() {
        return newsModels.size();
    }

    @Override
    public Object getItem(int position) {
        return newsModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.news_raw_holder, parent,false);

        final NewsModel model = (NewsModel) this.getItem(position);
        TextView Title = convertView.findViewById(R.id.NewsTitle);
        TextView Date = convertView.findViewById(R.id.NewsDate);

        if(model.getTitle() != null){
            Title.setText(model.getTitle());
        }

        if(model.getDate() != null){
            if(model.getDate().contains("Jan")) {
                String ModifiedDate = model.getDate()
                        .replace(model.getDate().substring(0,5), "")
                        .replace(model.getDate().substring(16), "")
                        .replace(" ", "/")
                        .replace("Jan", "1");
                Date.setText(ModifiedDate);
            }else if(model.getDate().contains("Feb")) {
                String ModifiedDate = model.getDate()
                        .replace(model.getDate().substring(0,5), "")
                        .replace(model.getDate().substring(16), "")
                        .replace(" ", "/")
                        .replace("Feb", "2");
                Date.setText(ModifiedDate);
            }else if(model.getDate().contains("Mar")) {
                String ModifiedDate = model.getDate()
                        .replace(model.getDate().substring(0,5), "")
                        .replace(model.getDate().substring(16), "")
                        .replace(" ", "/")
                        .replace("Mar", "3");
                Date.setText(ModifiedDate);
            }else if(model.getDate().contains("Apr")) {
                String ModifiedDate = model.getDate()
                        .replace(model.getDate().substring(0,5), "")
                        .replace(model.getDate().substring(16), "")
                        .replace(" ", "/")
                        .replace("Apr", "4");
                Date.setText(ModifiedDate);
            }else if(model.getDate().contains("May")) {
                String ModifiedDate = model.getDate()
                        .replace(model.getDate().substring(0,5), "")
                        .replace(model.getDate().substring(16), "")
                        .replace(" ", "/")
                        .replace("May", "5");
                Date.setText(ModifiedDate);
            }else if(model.getDate().contains("Jun")) {
                String ModifiedDate = model.getDate()
                        .replace(model.getDate().substring(0,5), "")
                        .replace(model.getDate().substring(16), "")
                        .replace(" ", "/")
                        .replace("Jun", "6");
                Date.setText(ModifiedDate);
            }else if(model.getDate().contains("Jul")) {
                String ModifiedDate = model.getDate()
                        .replace(model.getDate().substring(0,5), "")
                        .replace(model.getDate().substring(16), "")
                        .replace(" ", "/")
                        .replace("Jul", "7");
                Date.setText(ModifiedDate);
            }else if(model.getDate().contains("Aug")) {
                String ModifiedDate = model.getDate()
                        .replace(model.getDate().substring(0,5), "")
                        .replace(model.getDate().substring(16), "")
                        .replace(" ", "/")
                        .replace("Aug", "8");
                Date.setText(ModifiedDate);
            }else if(model.getDate().contains("Sept")) {
                String ModifiedDate = model.getDate()
                        .replace(model.getDate().substring(0,5), "")
                        .replace(model.getDate().substring(16), "")
                        .replace(" ", "/")
                        .replace("Sept", "9");
                Date.setText(ModifiedDate);
            }else if(model.getDate().contains("Oct")) {
                String ModifiedDate = model.getDate()
                        .replace(model.getDate().substring(0,5), "")
                        .replace(model.getDate().substring(16), "")
                        .replace(" ", "/")
                        .replace("Oct", "10");
                Date.setText(ModifiedDate);
            }else if(model.getDate().contains("Nov")) {
                String ModifiedDate = model.getDate()
                        .replace(model.getDate().substring(0,5), "")
                        .replace(model.getDate().substring(16), "")
                        .replace(" ", "/")
                        .replace("Nov", "11");
                Date.setText(ModifiedDate);
            }else if(model.getDate().contains("Dec")) {
                String ModifiedDate = model.getDate()
                        .replace(model.getDate().substring(0,5), "")
                        .replace(model.getDate().substring(16), "")
                        .replace(" ", "/")
                        .replace("Dec", "12");
                Date.setText(ModifiedDate);
            }else
                Date.setText(model.getDate());
        }

        return convertView;
    }
}