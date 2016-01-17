package com.grand.simpletodo.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.grand.simpletodo.fragments.EditItemDialog;
import com.grand.simpletodo.R;
import com.grand.simpletodo.adapters.TodoAdapter;
import com.grand.simpletodo.modules.TodoItem;

import java.util.List;


public class MainActivity extends AppCompatActivity implements EditItemDialog.OnSaveItemListener {
    List<TodoItem> items;
    ArrayAdapter<TodoItem> itemsAdapter;
    ListView lvItems;
    private int REQUEST_CODE_EDIT_ITEM = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new TodoAdapter(this, R.layout.todo_list_item, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TodoItem item = items.get(position);
                item.delete();
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEditDialog(position, items.get(position));
            }
        });
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

    public void onAddItem(View view){
        showEditDialog(-1, null);
    }


    private void readItems(){
        items = new Select().from(TodoItem.class).execute();
    }

    private void showEditDialog(int position, TodoItem todoItem) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editItemDialog = EditItemDialog.newInstance(position, todoItem);
        editItemDialog.show(fm, "edit_item");
    }

    @Override
    public void save(int position, TodoItem todoItem) {
        if(position == -1){
            items.add(todoItem);
            itemsAdapter.notifyDataSetChanged();
            todoItem.save();
        }else{
            TodoItem item = items.get(position);
            item.setDueDate(todoItem.getDueDate());
            item.setPriority(todoItem.getPriority());
            item.setName(todoItem.getName());
            item.setTodoNote(todoItem.getTodoNote());
            itemsAdapter.notifyDataSetChanged();
            item.save();
        }
    }
}
