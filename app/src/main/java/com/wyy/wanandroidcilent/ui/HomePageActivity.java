package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.utils.StateUtil;

//app主页，完成登录后进入此界面
public class HomePageActivity extends BaseActivity {

    FrameLayout homePageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        homePageLayout = (FrameLayout)findViewById(R.id.home_page);

        if(StateUtil.isNetworkConnected(this)){
            addFragmentToLayout(R.id.home_page,new ArticleListFragment());
        }else {
            addFragmentToLayout(R.id.home_page,new NoInternetFragment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }
}
