package com.aglook.imageup;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends BaseActivity {


    private TextView tv1;
    private TextView tv2;

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        setTitleBar("首页");
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv1:
                intent.setClass(MainActivity.this, WuActivity.class);
                startActivity(intent);
                break;
            case R.id.tv2:

                intent.setClass(MainActivity.this, QuanActivity.class);
                startActivity(intent);
                break;
        }
    }


}
