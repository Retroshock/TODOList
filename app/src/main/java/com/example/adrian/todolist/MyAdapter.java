package com.example.adrian.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 15.05.2017.
 */

public class MyAdapter extends ArrayAdapter {

    private int sessionId;
    private List<String> strings;

    public MyAdapter(@NonNull Context context, @NonNull List<String> strings, int sessionId) {
        super(context, 0, strings);
        this.strings = strings;
        this.sessionId = sessionId;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String info = (String) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_element, parent, false);
        }



        TextView todoElementText = (TextView) convertView.findViewById(R.id.todoElementText);
        todoElementText.setText(info);

        ImageView deleteBtn = (ImageView)convertView.findViewById(R.id.deleteBtn);
        deleteBtn.setTag(position);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strings.remove(position);
                SQLiteDatabase db = DatabaseObject.getDb();
                String[] strArgs = {String.valueOf(sessionId), info};
                db.delete("todos", "id_user = ? and info = ?", strArgs);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
