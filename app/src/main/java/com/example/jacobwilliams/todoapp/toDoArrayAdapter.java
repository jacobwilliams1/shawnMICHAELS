package com.example.jacobwilliams.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.example.jacobwilliams.todoapp.R.layout.to_do_item;

/**
 * Created by jacobwilliams on 10/25/16.
 */

public class toDoArrayAdapter extends ArrayAdapter<toDoItem> {
    private int resource;

    private ArrayList<toDoItem> items;

    private LayoutInflater inflater;

    private SimpleDateFormat formatter;


    public toDoArrayAdapter(Context context, int resource, ArrayList<toDoItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.items = objects;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        formatter = new SimpleDateFormat("MM/dd/yyyy");

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        toDoItem todoitem = items.get(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View customView = inflater.inflate(R.layout.to_do_item, parent, false);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.to_do_item, parent, false);
        }
        TextView toDoCategory = (TextView) convertView.findViewById(R.id.toDoCategory);
        TextView toDoTitle = (TextView) convertView.findViewById(R.id.toDoTitle);
        TextView toDoText = (TextView) convertView.findViewById(R.id.toDoText);
        TextView toDoMonth = (TextView) convertView.findViewById(R.id.toDoDueDateMonth);
        TextView toDoDay = (TextView) convertView.findViewById(R.id.toDoDueDateDay);
        TextView toDoTime = (TextView) convertView.findViewById(R.id.toDoDueDateTime);
        TextView toDoDate = (TextView) convertView.findViewById(R.id.due_date);

        toDoCategory.setText(todoitem.getCategory());
        toDoTitle.setText(todoitem.getTitle());
        toDoText.setText(todoitem.getText());
        toDoMonth.setText(todoitem.getMonth());
        toDoDay.setText(todoitem.getDay());
        toDoTime.setText(todoitem.getTime());
        toDoDate.setText(formatter.format(todoitem.getDate()));
        return convertView;

    }

    public void updateAdapter(ArrayList<toDoItem> notes) {
        this.items = notes;
        super.notifyDataSetChanged();
    }
}
