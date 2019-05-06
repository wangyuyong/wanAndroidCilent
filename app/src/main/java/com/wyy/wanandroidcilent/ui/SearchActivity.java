package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.wyy.wanandroidcilent.R;

public class SearchActivity extends AppCompatActivity {

    ImageButton backIbtn;
    EditText searchEt;
    ImageButton searchIbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();                               //隐藏原始标题栏

        backIbtn = (ImageButton)findViewById(R.id.ibtn_back);
        searchEt = (EditText)findViewById(R.id.et_search);
        searchIbtn = (ImageButton)findViewById(R.id.ibtn_search);

        backIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEt.getText().toString();
                Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                intent.putExtra("searchText",searchText);
                startActivity(intent);
            }
        });
    }
}
