package com.wyy.wanandroidcilent.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.net.HttpCallBack;
import com.wyy.wanandroidcilent.utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisteActivity extends BaseActivity implements View.OnClickListener {

    EditText userNameEd;
    EditText passwordEd;
    EditText repasswordEd;
    Button confirmBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        userNameEd = (EditText)findViewById(R.id.ed__registe_username);
        passwordEd = (EditText)findViewById(R.id.ed__registe_password);
        repasswordEd = (EditText)findViewById(R.id.ed__registe_repassword);
        confirmBtn = (Button)findViewById(R.id.btn_confirm);
        cancelBtn = (Button)findViewById(R.id.btn_cancel);                                          //获取实列

        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                registeUser();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            default:
        }
    }

    private void registeUser(){
        String userName = userNameEd.getText().toString();
        String password = passwordEd.getText().toString();
        String repassword = repasswordEd.getText().toString();

        HttpUtil.sendHttpRequestByPost(HttpUtil.WAN_ANDROID_REGISTE_ADRESS, "username=" +
                userName + "&password=" + password + "&repassword=" + repassword, new HttpCallBack() {
            @Override
            public void onFinish(String respone) {
                try {
                    JSONObject object = new JSONObject(respone);
                    final int errorCode = object.getInt("errorCode");
                    final String errorMessage = object.getString("errorMsg");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (errorCode == 0){
                                Toast.makeText(RegisteActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                Toast.makeText(RegisteActivity.this,"注册失败,"+
                                        errorMessage,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                    e.printStackTrace();
            }
        });
    }
}
