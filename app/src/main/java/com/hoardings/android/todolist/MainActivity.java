package com.hoardings.android.todolist;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TodoListAdapter mAdapter;
    private ViewGroup actionBarLayout;
    private TaskUtil taskUtil;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskUtil = new TaskUtil(new FeedReaderDbHelper(getApplicationContext()));
        actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar, null);
        tasks = taskUtil.getTasks();
        ImageView imageView = (ImageView) actionBarLayout.findViewById(R.id.add_new);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateView();
            }
        });
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(actionBarLayout);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mAdapter = new TodoListAdapter(tasks);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerItemDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        // prepareMovieData();
        recyclerView.addOnItemTouchListener(new RecyclerViewListener(getApplicationContext(), recyclerView, new RecyclerViewListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Task movie = tasks.get(position);
                Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                intent.putExtra("position", String.valueOf(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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
                mAdapter.notifyDataSetChanged();
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
