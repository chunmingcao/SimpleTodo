package com.grand.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    public static String PARAM_ITEM_INDEX = "PARAM_ITEM_INDEX";
    public static String PARAM_ITEM_VALUE = "PARAM_ITEM_VALUE";
    private EditText editItem;
    private int itemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editItem = (EditText) findViewById(R.id.editItem);
        editItem.setText(getIntent().getStringExtra(PARAM_ITEM_VALUE));
        itemIndex = getIntent().getIntExtra(PARAM_ITEM_INDEX, 0);
    }

    public void saveItem(View view){
        Intent result = new Intent();
        result.putExtra(PARAM_ITEM_INDEX, itemIndex);
        result.putExtra(PARAM_ITEM_VALUE, editItem.getText().toString());
        setResult(RESULT_OK, result);
        finish();
    }

}
