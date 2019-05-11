package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.utils.StateUtil;

public class SearchActivity extends BaseActivity {

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

        //返回上一活动
        backIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //搜索
        searchIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开搜索结果活动
                if(StateUtil.isNetworkConnected(SearchActivity.this)) {
                    String searchText = searchEt.getText().toString();
                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("searchText", searchText);
                    startActivity(intent);
                }else {
                    Toast.makeText(SearchActivity.this,"网络连接不良，请检查网络设置",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
