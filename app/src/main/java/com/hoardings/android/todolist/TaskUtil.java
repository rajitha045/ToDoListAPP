package com.hoardings.android.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskUtil {
    private List<Task> taskList;
    private FeedReaderDbHelper mDBHelper;

    public TaskUtil(FeedReaderDbHelper mDBHelper) {
        this.mDBHelper = mDBHelper;
    }

    public void addTask(Task task) {
        saveData(task);
        taskList.add(0,task);
    }

    public List<Task> getTasks() {
        if(taskList == null){
            taskList = getTaskList();
        }
        return taskList;
    }

    private List<Task> getTaskList() {
        taskList =new ArrayList<>();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String[] projection = {
                FeedReaderDbHelper.FeedEntry._ID,
                FeedReaderDbHelper.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderDbHelper.FeedEntry.COLUMN_NAME_DETAILS
        };

        String sortOrder =
                FeedReaderDbHelper.FeedEntry._ID + " DESC";

        Cursor c = db.query(
                FeedReaderDbHelper.FeedEntry.TABLE_NAME,                     // The table to query
                projection,                                                    // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c != null && c.moveToFirst()) {
            System.out.println(c.getColumnCount());
            System.out.println(c.getCount());

            do {
                Task task = new Task(
                        c.getString(c.getColumnIndexOrThrow(FeedReaderDbHelper.FeedEntry.COLUMN_NAME_TITLE)),
                        c.getString(c.getColumnIndexOrThrow(FeedReaderDbHelper.FeedEntry.COLUMN_NAME_DETAILS)));
                this.taskList.add(task);
            } while (c.moveToNext());
            c.close();
        }
        return taskList;

    }

    private void saveData(Task task) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderDbHelper.FeedEntry.COLUMN_NAME_TITLE, task.getTitle());
        values.put(FeedReaderDbHelper.FeedEntry.COLUMN_NAME_DETAILS, task.getDetails());
        long newRowId = db.insert(FeedReaderDbHelper.FeedEntry.TABLE_NAME, null, values);
    }
}
