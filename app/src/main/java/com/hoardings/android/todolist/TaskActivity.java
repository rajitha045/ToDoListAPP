package com.hoardings.android.todolist;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TaskActivity extends AppCompatActivity {
    private ViewGroup actionBarLayout;
    private List<Task> tasks;
    private TaskUtil taskUtil;
    private PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
        actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar,
                null);
        taskUtil =new TaskUtil(new FeedReaderDbHelper(getApplicationContext()));
        actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar, null);
        TextView actionBarName = (TextView)actionBarLayout.findViewById(R.id.action_bar_name);
        actionBarName.setText("Task");
        tasks = taskUtil.getTasks();
        ImageView imageView = (ImageView) actionBarLayout.findViewById(R.id.add_new);
        imageView.setVisibility(View.INVISIBLE);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(actionBarLayout);
        int position = Integer.parseInt(getIntent().getStringExtra("position"));
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerAdapter = new CustomPagerAdapter(this,tasks);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(position);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showUpdateView() {
        final Dialog commentDialog = new Dialog(this);
        commentDialog.setContentView(R.layout.add_task);
        commentDialog.show();

        Button okBtn = (Button) commentDialog.findViewById(R.id.ok);
        okBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String title = ((TextView) commentDialog.findViewById(R.id.new_title)).getText().toString();
                String details = ((TextView) commentDialog.findViewById(R.id.new_details)).getText().toString();
                taskUtil.addTask(new Task(title, details));
                pagerAdapter.notifyDataSetChanged();
                commentDialog.dismiss();
            }
        });
        Button cancelBtn = (Button) commentDialog.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                commentDialog.dismiss();
            }
        });
    }
}
