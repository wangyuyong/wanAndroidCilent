package com.wyy.wanandroidcilent.ui.registe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wyy.wanandroidcilent.Const;
import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.utils.StateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisteActivity extends BaseActivity implements RegisteContract.RegisteView{

    private RegistePresent present;

    @BindView(R.id.btn_confirm)
    Button registeBtn;
    @BindView(R.id.btn_cancel)
    Button cancelBtn;
    @BindView(R.id.ed__registe_password)
    EditText passwordEdt;
    @BindView(R.id.ed__registe_repassword)
    EditText repasswordEdt;
    @BindView(R.id.ed__registe_username)
    EditText userNameEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        ButterKnife.bind(this);

        //present绑定view
        present = new RegistePresent();
        present.bindView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.unBind();
    }

    //获得用户名
    @Override
    public String getUserName() {
        return userNameEdt.getText().toString();
    }

    //获得密码
    @Override
    public String getPassword() {
        return passwordEdt.getText().toString();
    }

    //获得确认密码
    @Override
    public String getRepassword() {
        return repasswordEdt.getText().toString();
    }

    //注册成功
    @Override
    public void registeSuccess() {
        Toast.makeText(this, Const.CONST_SUCCESS,Toast.LENGTH_LONG).show();
        finish();
    }

    //注册失败
    @Override
    public void registeError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    //取消
    @Override
    public void cancel() {
        finish();
    }

    @OnClick({R.id.btn_confirm,R.id.btn_cancel})
    public void onViewClick(View v){
        switch (v.getId()){
            case R.id.btn_confirm:
                if (StateUtil.isFastClicked()){
                    return;
                }
                present.registe();
                break;
            case R.id.btn_cancel:
                if (StateUtil.isFastClicked()){
                    return;
                }
                cancel();
                break;
            default:
        }
    }
}
