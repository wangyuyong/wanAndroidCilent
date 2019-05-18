package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.utils.StateUtil;

public class SearchActivity extends BaseActivity {

    Toolbar toolbar;
    EditText searchEt;
    ActionBar actionBar;
    ImageButton searchIbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = (Toolbar)findViewById(R.id.tb_search);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        searchEt = (EditText)findViewById(R.id.et_search);

        //返回上一活动
        /*backIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        //搜索
/*        searchIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StateUtil.isFastClicked()){
                    return;
                }
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
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (StateUtil.isFastClicked()){
            return false;
        }
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.search:
                search();
                break;
            default:
        }
        return true;
    }

    private void search(){
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
}
