package com.example.adrian.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private Database dbHelper = DatabaseObject.getDbHelper();
    private SQLiteDatabase db = DatabaseObject.getDb();
    private ArrayList<String> infos;
    private MyAdapter adapter;
    private ListView listView;
    private Button addBtn;
    private TextView infoEntry;
    private int sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);
        sessionId = getIntent().getIntExtra(Extras.SESSION_ID, 0);
        setUserName();
        setActionListeners();
        getUserToDos();


    }

    private void setActionListeners() {
        addBtn = (Button) findViewById(R.id.addTodoItemBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodoItem();

                //getUserToDos();
            }
        });
    }

    private void getUserToDos() {
        Cursor cursor = db.rawQuery("SELECT * FROM todos WHERE id_user = " + sessionId, null);
        infos = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                String info;
                info = cursor.getString(1);
                infos.add(info);
            }while (cursor.moveToNext());
        }
        cursor.close();
        adapter = new MyAdapter(this, infos,sessionId);
        listView = (ListView) findViewById(R.id.listOfTodos);
        listView.setAdapter(adapter);

    }

    public void setUserName (){
        TextView username = (TextView) findViewById(R.id.usernameInList);

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE id = " + sessionId, null);
        if (cursor.moveToFirst()){
            username.setText(cursor.getString(1));
        } else {
            Toast.makeText(getApplicationContext(),"No username found at the id ", Toast.LENGTH_SHORT);
        }
        cursor.close();

    }

    public void addTodoItem (){
        infoEntry = (TextView) findViewById(R.id.infoEntry);
        if (infoEntry.getText().toString().equals(""))
            Toast.makeText(this, "Activity cannot be empty", Toast.LENGTH_SHORT).show();
        else {
            String[] strArgs = {String.valueOf(sessionId), infoEntry.getText().toString()};
            Cursor cursor = db.rawQuery("SELECT * FROM todos WHERE id_user = ? and info = ?", strArgs);
            if (cursor.moveToFirst()){
                Toast.makeText(this, "Cannot add the same todo item twice!", Toast.LENGTH_SHORT).show();
            } else {
                adapter.add(infoEntry.getText().toString());
                db.execSQL("INSERT INTO todos (id_user, info) VALUES ( ?, ? )", strArgs);
                Toast.makeText(TodoActivity.this, "Added Item", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }
    }

}
