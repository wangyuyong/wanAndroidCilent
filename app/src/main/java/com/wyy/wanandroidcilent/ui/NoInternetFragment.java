package com.wyy.wanandroidcilent.ui;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.utils.StateUtil;

//进入app时，没有网络则加载此碎片
public class NoInternetFragment extends Fragment {

    Button tryAgainButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_internet,container,false);

        //注册重新加载按钮的监听器
        tryAgainButton = (Button)view.findViewById(R.id.btn_try_again);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StateUtil.isNetworkConnected(getActivity())){
                    replaceFragment(new ArticleListFragment());
                }else{
                    Toast.makeText(getActivity(),"网络不良，请检查您的网络设置",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    //切换碎片
    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.home_page,fragment);
        transaction.commit();
    }
}
