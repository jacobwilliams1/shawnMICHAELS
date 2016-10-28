package com.example.jacobwilliams.todoapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.example.jacobwilliams.todoapp.R.layout.to_do_item;

public class MainActivity extends AppCompatActivity {
    private toDoArrayAdapter toDoArrayAdapter;
    public ListView toDoList;
    String filename = "ToDoListFile";
    Gson gson = new Gson();
    ArrayList<toDoItem> toDoItemArrayList = new ArrayList<>();
    List<toDoItem> noteLists = new ArrayList<>();
    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gson = new Gson();
        setupNotes();

        toDoList = (ListView) findViewById(R.id.listView);
        toDoArrayAdapter = new toDoArrayAdapter(this, to_do_item, toDoItemArrayList);
        toDoList.setAdapter(toDoArrayAdapter);
        iv = (ImageView) findViewById(R.id.imageView);


        toDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent doStuff = new Intent(MainActivity.this, toDoDetailActivity.class);
                toDoItem toDo = toDoItemArrayList.get(position);

                doStuff.putExtra("Title", toDo.getTitle());
                doStuff.putExtra("Text", toDo.getText());
                doStuff.putExtra("Month", toDo.getMonth());
                doStuff.putExtra("Day", toDo.getDay());
                doStuff.putExtra("Time", toDo.getTime());
                doStuff.putExtra("Category", toDo.getCategory());
                doStuff.putExtra("Index", position);
                startActivityForResult(doStuff, 1);
                toDoArrayAdapter.remove(toDoItemArrayList.get(position));
                toDoArrayAdapter.notifyDataSetChanged();
                writeTodo();
            }
        });


        toDoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view,
                                           final int position,
                                           long id) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Delete");
                alertBuilder.setMessage("You sure?");
                alertBuilder.setNegativeButton("Cancel", null);
                alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toDoItem toDo = toDoItemArrayList.get(position);
                        toDoItemArrayList.remove(position);
                        toDoArrayAdapter.updateAdapter(toDoItemArrayList);
                        writeTodo();

                    }
                });
                alertBuilder.create().show();
                return true;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(MainActivity.this, toDoDetailActivity.class);
                startActivityForResult(i, 1);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            int index = data.getIntExtra("Index", -1);
            toDoItem todoitem = new toDoItem(
                    data.getStringExtra("Time"),
                    data.getStringExtra("Day"),
                    data.getStringExtra("Month"),
                    data.getStringExtra("Text"),
                    new Date(),
                    data.getStringExtra("Title"),
                    data.getStringExtra("Category"));
            if (index < 0 || index > toDoItemArrayList.size() - 1) {
                toDoItemArrayList.add(todoitem);
                Collections.sort(toDoItemArrayList);
                toDoArrayAdapter.updateAdapter(toDoItemArrayList);
            }
            writeTodo();
        }
    }

    private void setupNotes() {
        toDoItemArrayList = new ArrayList<>();


        File filesDir = this.getFilesDir();
        File todoFile = new File(filesDir + File.separator + filename);
        if (todoFile.exists()) {
            readTodo(todoFile);

            for (toDoItem todo : noteLists) {
                Log.d("ToDo Read from file: ", todo.getTitle() + " " + todo.getText());
                toDoItemArrayList.add(todo);
            }
        } else {
            writeTodo();
        }
    }

    private void readTodo(File todoFile) {
        FileInputStream inputStream = null;
        String todosText = "";
        try {
            inputStream = openFileInput(todoFile.getName());
            byte[] input = new byte[inputStream.available()];
            while (inputStream.read(input) != -1) {
            }
            todosText = new String(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            toDoItem[] todoList = gson.fromJson(todosText, toDoItem[].class);
            noteLists = Arrays.asList(todoList);
        }
    }

    private void writeTodo() {
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            String json = gson.toJson(toDoItemArrayList);
            byte[] bytes = json.getBytes();
            outputStream.write(bytes);

            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception ignored) {
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
