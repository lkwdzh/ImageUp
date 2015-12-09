package com.aglook.imageup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class WuActivity extends BaseActivity {


    private ImageView iv;

    @Override
    public void initView() {
        setContentView(R.layout.activity_wu);
        setTitleBar("货物图片");
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);

        if ( ImageUtil.wuImage!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(ImageUtil.wuImage);
            iv.setImageBitmap(bitmap);
        }
    }

    @Override
    public void widgetClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv:
                intent.setClass(WuActivity.this, UploadImageActivity.class);
                intent.putExtra("num","0");
                startActivityForResult(intent, 1);
                break;
        }
    }

    private String picPath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            picPath = data.getStringExtra("image_path");
            ImageUtil.wuImage=picPath;
            Log.d("result_path", picPath);
            Bitmap bm = BitmapFactory.decodeFile(picPath);
            Drawable drawable = new BitmapDrawable(this.getResources(), bm);
            iv.setImageBitmap(bm);

        }
    }
}
