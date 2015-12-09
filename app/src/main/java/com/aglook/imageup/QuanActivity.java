package com.aglook.imageup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class QuanActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    @Override
    public void initView() {
        setContentView(R.layout.activity_quan);
        setTitleBar("货权图片");
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv1.setOnClickListener(this);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv2.setOnClickListener(this);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv3.setOnClickListener(this);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv4.setOnClickListener(this);
        iv5 = (ImageView) findViewById(R.id.iv5);
        iv5.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.iv1:
                intent.setClass(QuanActivity.this,UploadImageActivity.class);
                intent.putExtra("num","1");
                startActivityForResult(intent, 1);
                break;
            case R.id.iv2:
                intent.setClass(QuanActivity.this,UploadImageActivity.class);
                intent.putExtra("num","2");
                startActivityForResult(intent,2);
                break;
            case R.id.iv3:
                intent.setClass(QuanActivity.this,UploadImageActivity.class);
                intent.putExtra("num","3");
                startActivityForResult(intent,3);
                break;
            case R.id.iv4:
                intent.setClass(QuanActivity.this,UploadImageActivity.class);
                intent.putExtra("num","4");
                startActivityForResult(intent,4);
                break;
            case R.id.iv5:
                intent.setClass(QuanActivity.this,UploadImageActivity.class);
                intent.putExtra("num","5");
                startActivityForResult(intent,5);
                break;
        }
    }

    private String picPath;
private Bitmap bm;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null) {
            picPath = data.getStringExtra("image_path");
            Log.d("result_path", picPath);
             bm = BitmapFactory.decodeFile(picPath);
        }
//            Drawable drawable = new BitmapDrawable(this.getResources(), bm);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            iv1.setImageBitmap(bm);

        }else  if (requestCode == 2 && resultCode == RESULT_OK) {
            iv2.setImageBitmap(bm);

        }else  if (requestCode == 3 && resultCode == RESULT_OK) {
            iv3.setImageBitmap(bm);

        }else  if (requestCode == 4 && resultCode == RESULT_OK) {
            iv4.setImageBitmap(bm);

        }else  if (requestCode == 5 && resultCode == RESULT_OK) {
            iv5.setImageBitmap(bm);

        }
    }

}
