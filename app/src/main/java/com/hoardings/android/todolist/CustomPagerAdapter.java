package com.hoardings.android.todolist;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class CustomPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Task> movies;
    public CustomPagerAdapter(Context context, List<Task> movieList) {
        this.context =context;
        this.movies =movieList;

    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.task_details, collection, false);
        TextView titleTextView = (TextView) view.findViewById(R.id.details_title);
        TextView summary = (TextView) view.findViewById(R.id.details_summary);
        Task movie = movies.get(position);
        titleTextView.setText(movie.getTitle());
        summary.setText(movie.getDetails());
        view.setTag(movie);
        collection.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
}
