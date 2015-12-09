package com.aglook.imageup;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public abstract class BaseActivity extends Activity implements View.OnClickListener {
    private ImageView mLeftIcon;
    private ImageView mRightIcon;
    private TextView mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_base);
        initView();
    }
    //初始化view
    public abstract void initView();

    //控件点击事件
    public abstract void widgetClick(View view);
    @Override
    public void onClick(View view) {
        widgetClick(view);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mLeftIcon=(ImageView)findViewById(R.id.left_icon);
        mRightIcon=(ImageView) findViewById(R.id.right_image);

        if (mLeftIcon!=null){
            mLeftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onLeftIconClick(view);
                }
            });
        }
    }

    /**
     * 设置标题栏文字
     */
    public void setTitleBar(CharSequence title){
        mTitle =(TextView) findViewById(R.id.title);
        if (mTitle!=null){
            mTitle.setText(title);
        }
    }


    /**
     * 标题栏左边按钮点击事件，默认等于返回键
     */
    public void onLeftIconClick(View view){
        finish();
    }
    /**
     * 设置右边按钮
     *
     *
     */
    public ImageView setRightIcon() {
        mRightIcon.setVisibility(View.VISIBLE);
        return mRightIcon;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
