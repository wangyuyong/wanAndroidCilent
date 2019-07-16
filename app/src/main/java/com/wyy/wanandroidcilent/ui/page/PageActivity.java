package com.wyy.wanandroidcilent.ui.page;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wyy.wanandroidcilent.Const;
import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.adapter.GuideAdapter;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.ui.AboutFragment;
import com.wyy.wanandroidcilent.ui.homepage.HomePageFragment;
import com.wyy.wanandroidcilent.ui.login.LoginActivity;
import com.wyy.wanandroidcilent.ui.projectlist.ProjectFragment;
import com.wyy.wanandroidcilent.ui.search.SearchActivity;
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//app主页，完成登录后进入此界面
public class PageActivity extends BaseActivity {

    private List<Fragment> fragments;
    private FragmentPagerAdapter adapter;

    @BindView(R.id.tb_home_page)
    Toolbar homePageTb;
    @BindView(R.id.nav_slide)
    NavigationView slideNav;
    @BindView(R.id.dl_slide_menu)
    DrawerLayout slideMenuDl;
    @BindView(R.id.vp_guide)
    ViewPager guideVp;
    @BindView(R.id.bnv_guide)
    BottomNavigationView guideBnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        ButterKnife.bind(this);

        setSupportActionBar(homePageTb);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //设置滑动菜单
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        init();

        slideNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_logon:
                        //退出登录
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
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //打开滑动菜单
                slideMenuDl.openDrawer(GravityCompat.START);
                break;
            case R.id.search:
                Intent intent = new Intent(this, SearchActivity.class);
                //进入搜索界面
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    //退出登录
    private void logon() {
        //从本地中删除用户信息
        SharedPreferencesUtil.deleteWithSharePreference(this, Const.CONST_USER,
                Const.CONST_USER_NAME);
        SharedPreferencesUtil.deleteWithSharePreference(this, Const.CONST_USER,
                Const.CONST_PASSWORD);

        //返回登录界面
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //初始化Activity
    private void init(){
        //添加viewPager的数据源
        fragments = new ArrayList<>();
        fragments.add(new HomePageFragment());
        fragments.add(new ProjectFragment());
        fragments.add(new AboutFragment());
        //添加适配器
        adapter = new GuideAdapter(getSupportFragmentManager(),fragments);
        guideVp.setAdapter(adapter);
        //设置缓存
        guideVp.setOffscreenPageLimit(2);
        //添加监听
        guideVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                guideBnv.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        guideBnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.bnv_home_page:
                        guideVp.setCurrentItem(0);
                        break;
                    case R.id.bnv_project:
                        guideVp.setCurrentItem(1);
                        break;
                    case R.id.bnv_about:
                        guideVp.setCurrentItem(2);
                        break;
                    default:
                }
                return true;
            }
        });
    }

}
