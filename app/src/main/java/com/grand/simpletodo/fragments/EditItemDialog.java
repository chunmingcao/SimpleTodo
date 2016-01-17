package com.grand.simpletodo.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.grand.simpletodo.R;
import com.grand.simpletodo.modules.TodoItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditItemDialog extends DialogFragment {
    public interface OnSaveItemListener{
        public void save(int position, TodoItem todoItem);
    }
    public static String PARAM_ITEM_INDEX = "PARAM_ITEM_INDEX";
    public static String PARAM_ITEM_NAME = "PARAM_ITEM_NAME";
    public static String PARAM_ITEM_PRIORITY = "PARAM_ITEM_PRIORITY";
    public static String PARAM_ITEM_DUEDATE = "PARAM_ITEM_DUEDATE";
    public static String PARAM_ITEM_NOTE = "PARAM_ITEM_NOTE";

    private EditText editItem;
    private int itemIndex;

    public EditItemDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditItemDialog newInstance(int pos, TodoItem todoItem) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putInt(PARAM_ITEM_INDEX, pos);

        if(todoItem != null) {
            args.putString(PARAM_ITEM_NAME, todoItem.getName());
            if(todoItem.getPriority() != null) {
                args.putString(PARAM_ITEM_PRIORITY, todoItem.getPriority().toString());
            }
            if(todoItem.getDueDate() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String date = formatter.format(todoItem.getDueDate());
                args.putString(PARAM_ITEM_DUEDATE, date);
            }
            args.putString(PARAM_ITEM_NOTE, todoItem.getTodoNote());
        }

        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_edit_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle("Edit Item");

        editItem = (EditText) view.findViewById(R.id.editItem);
        editItem.setText(getArguments().getString(PARAM_ITEM_NAME));
        itemIndex = getArguments().getInt(PARAM_ITEM_INDEX, -1);
        final Spinner priority = (Spinner) view.findViewById(R.id.priority);

        ArrayAdapter<TodoItem.Priority> spnnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, TodoItem.Priority.values());
        priority.setAdapter(spnnerAdapter);
        if(getArguments().getString(PARAM_ITEM_PRIORITY) != null) {
            int selectedPos = spnnerAdapter.getPosition(TodoItem.Priority.valueOf(getArguments().getString(PARAM_ITEM_PRIORITY)));
            priority.setSelection(selectedPos);
        }

        final TextView todoNote = (EditText) view.findViewById(R.id.note);
        todoNote.setText(getArguments().getString(PARAM_ITEM_NOTE));

        final EditText dueDateTxt = (EditText)view.findViewById(R.id.duedate);
        if(getArguments().getString(PARAM_ITEM_DUEDATE) != null) {
            dueDateTxt.setText(getArguments().getString(PARAM_ITEM_DUEDATE));
        }else{
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            dueDateTxt.setText(formatter.format(new Date()));
        }

        dueDateTxt.setInputType(InputType.TYPE_NULL);
        dueDateTxt.setFocusable(false);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        dueDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dtDlg = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dueDateTxt.setText(String.format("%02d/%02d/%02d", monthOfYear+1, dayOfMonth, year));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dtDlg.show();
            }
        });

        Button saveBtn = (Button)view.findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSaveItemListener onSaveItemListener = (OnSaveItemListener)getActivity();
                TodoItem todoItem = new TodoItem(editItem.getText().toString());
                if(!priority.getSelectedItem().toString().equals("")){
                    todoItem.setPriority(TodoItem.Priority.valueOf(priority.getSelectedItem().toString()));
                }
                todoItem.setTodoNote(todoNote.getText().toString());

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date date = formatter.parse(dueDateTxt.getText().toString());
                    todoItem.setDueDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                onSaveItemListener.save(itemIndex, todoItem);
                dismiss();
            }
        });

        Button cancelBtn = (Button)view.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
    }
}
