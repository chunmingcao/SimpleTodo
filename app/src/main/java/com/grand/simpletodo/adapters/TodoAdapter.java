package com.grand.simpletodo.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.grand.simpletodo.R;
import com.grand.simpletodo.modules.TodoItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by caocm_000 on 1/16/2016.
 */
public class TodoAdapter extends ArrayAdapter<TodoItem> {
    private static class ViewHolder{
        TextView todoNameTxt;
        TextView todoPriorityTxt;
        TextView todoDueDate;
    }
    public TodoAdapter(Context context, int resource, List<TodoItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem todoItem = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.todoNameTxt = (TextView)convertView.findViewById(R.id.todo_item_name);
            viewHolder.todoPriorityTxt = (TextView)convertView.findViewById(R.id.todo_item_priority);
            viewHolder.todoDueDate = (TextView)convertView.findViewById(R.id.todo_item_duedate);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.todoNameTxt.setText(todoItem.getName());

        if(todoItem.getPriority() != null) {
            viewHolder.todoPriorityTxt.setText(todoItem.getPriority().toString());
            switch (todoItem.getPriority()){
                case HEIGH:
                    viewHolder.todoPriorityTxt.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
                    break;
                case MEDIUM:
                    viewHolder.todoPriorityTxt.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark));
                    break;
                case LOW:
                    viewHolder.todoPriorityTxt.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_dark));
                    break;
            }
        }
        if(todoItem.getDueDate()!=null) {
            Date today = new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String todayStr = dateFormat.format(today);
            try {
                today = dateFormat.parse(todayStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
            if (today.compareTo(todoItem.getDueDate()) == 0) {
                viewHolder.todoDueDate.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
                viewHolder.todoDueDate.setText("Today");
            }else if(today.compareTo(todoItem.getDueDate()) > 0){
                viewHolder.todoDueDate.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
                viewHolder.todoDueDate.setText(formatter.format(todoItem.getDueDate()));
            }else {
                long diffDays = (todoItem.getDueDate().getTime() - today.getTime())/(1000 * 60 * 60 * 24);
                Log.i("diffDays", String.valueOf(diffDays));
                if(diffDays == 1){
                    viewHolder.todoDueDate.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark));
                    viewHolder.todoDueDate.setText("Tomorrow");
                }else{
                    viewHolder.todoDueDate.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_dark));
                    viewHolder.todoDueDate.setText(formatter.format(todoItem.getDueDate()));
                }

            }
        }
        return convertView;
    }
}
