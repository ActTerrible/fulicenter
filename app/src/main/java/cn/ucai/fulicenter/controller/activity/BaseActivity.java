package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.fulicenter.model.utils.MFGT;

/**
 * Created by mac-yk on 2016/10/19.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    protected void setListener() {
    }

    protected void initData() {
    }

    protected void initView() {
    }
    public void onBackPressed(){
        MFGT.finish(this);
    }
}
