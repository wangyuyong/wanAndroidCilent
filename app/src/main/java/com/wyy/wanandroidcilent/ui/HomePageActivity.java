package com.wyy.wanandroidcilent.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;
import com.wyy.wanandroidcilent.utils.StateUtil;

//app主页，完成登录后进入此界面
public class HomePageActivity extends BaseActivity {

    FrameLayout homePageLayout;
    DrawerLayout slideMenu;
    NavigationView menuNav;
    TextView userNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        slideMenu = (DrawerLayout)findViewById(R.id.dl_slide_menu);
        homePageLayout = (FrameLayout)findViewById(R.id.home_page);
        menuNav = (NavigationView)findViewById(R.id.nav_slide);
        userNameTv = (TextView)slideMenu.findViewById(R.id.tv_user_name);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("username");                                //从上一活动中获取用户名

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.menu);

        if(StateUtil.isNetworkConnected(this)){
            addFragmentToLayout(R.id.home_page,new ArticleListFragment());
        }else {
            addFragmentToLayout(R.id.home_page,new NoInternetFragment());
        }

        menuNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_logon:
                        logon();
                        break;
                    default:
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                slideMenu.openDrawer(GravityCompat.START);
                break;
            case R.id.search:
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    private void logon(){
        SharedPreferencesUtil.deleteWithSharePreference(this,SharedPreferencesUtil.USER,
                "username");
        SharedPreferencesUtil.deleteWithSharePreference(this,SharedPreferencesUtil.USER,
                "password");                                                                   //从本地中删除用户信息

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();                                                                                   //返回登录界面
    }
}
