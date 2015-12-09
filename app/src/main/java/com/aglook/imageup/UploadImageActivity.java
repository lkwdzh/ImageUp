package com.aglook.imageup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class UploadImageActivity extends BaseActivity {

    private Intent lastIntent;
    /** 开启相机 */
    private Button btn_take_photo;
    /** 开启图册 */
    private Button btn_pick_photo;
    /** 取消 */
    private Button btn_cancel;

    /** 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;// 图片库
    private static final int CAMERA_REQUEST_CODE = 1;// 拍照
    private static final int RESULT_REQUEST_CODE = 2;// 裁剪图片

    // 判断请求的对象
    private String num;
    private String imageName;


    @Override
    public void initView() {
        setContentView(R.layout.activity_upload_image);
        lastIntent = getIntent();
        // 初始化控件
        btn_take_photo = (Button) findViewById(R.id.btn_take_photo);
        btn_pick_photo = (Button) findViewById(R.id.btn_pick_photo);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_take_photo.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        num = lastIntent.getStringExtra("num");
        imageName="quan.png";
        if (num.equals("1")) {
            imageName = "quan1.png";
        } else if (num.equals("2")) {
            imageName = "quan2.png";
        } else if (num.equals("3")) {
            imageName = "quan3.png";
        } else if (num.equals("4")) {
            imageName = "quan4.png";
        } else if (num.equals("5")) {
            imageName = "quan5.png";
        }

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_photo: // 开启相机
                takePhoto(imageName);
                break;
            case R.id.btn_pick_photo: // 开启图册
                pickPhoto();
                break;
            case R.id.btn_cancel: // 取消操作
                this.finish();
                break;
            default:
                break;
        }

    }

    private void takePhoto(String imageName) {
        // 拍照
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File file = new File(path, imageName);
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(file));
        }
        startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
    }

    private void pickPhoto() {
        // 选择图片
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentFromGallery.setType("image/*");// 相片类型
        startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

//    /**
//     * 压缩图片(bitmap)
//     */
//    private void CropImage(Bitmap bitmap, final String iconName) {
//        final Bitmap photo = bitmap;
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    int quality = 50;
//                    Bitmap bit = photo;
//                    String dirFile = Environment
//                            .getExternalStorageDirectory()
//                            .getAbsolutePath();
//                    File jpegTrueFile = new File(dirFile, iconName);
//                    NativeUtil.compressBitmap(bit, quality,
//                            jpegTrueFile.getAbsolutePath(), true);
//
//                    lastIntent.putExtra("image_path", Environment
//                            .getExternalStorageDirectory().getAbsolutePath()
//                            + "/"
//                            + iconName);
//                    setResult(Activity.RESULT_OK, lastIntent);
//                    finish();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    /**
     * 保存裁剪之后的图片数据
     *
     */
    private void getImageToView(Intent data, String iconName) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            saveBitmap(photo, iconName);
        }
    }

    /** 保存方法 */
    public void saveBitmap(Bitmap bm, String iconName) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/", iconName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.i("TAG", "已经保存");
            lastIntent.putExtra("image_path", Environment
                    .getExternalStorageDirectory().getAbsolutePath()
                    + "/"
                    + iconName);
            setResult(Activity.RESULT_OK, lastIntent);
            finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 结果码不等于取消时候
//        if (resultCode != RESULT_CANCELED) {
//            if (num.equals("1")) {

                switch (requestCode) {
                    case IMAGE_REQUEST_CODE:
                        startPhotoZoom(data.getData());// 裁剪
//                        saveBitmap(createImageThumbnail(data.getDataString(),
//                        200,
//                        300));
                        break;
                    case CAMERA_REQUEST_CODE:
                        // 判断存储卡是否可以用，可用进行存储
                        String state = Environment.getExternalStorageState();
                        if (state.equals(Environment.MEDIA_MOUNTED)) {
                            File path = Environment
                                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                            File tempFile = new File(path, imageName);
                            startPhotoZoom(Uri.fromFile(tempFile));//裁剪
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case RESULT_REQUEST_CODE: // 图片缩放完成后
                        if (data != null) {
                            getImageToView(data, imageName);
                        }
                        break;
                }

//            } else {
//                switch (requestCode) {
//                    case IMAGE_REQUEST_CODE:
//                        ContentResolver resolver = getContentResolver();
//                        //照片的原始资源地址
//                        Uri originalUri = data.getData();
//                        //使用ContentProvider通过URI获取原始图片
//                        try {
//                            Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
//                            if(photo!=null){
//                                CropImage(photo, imageName);
//                            }
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        break;
//                    case CAMERA_REQUEST_CODE:
//                        // 判断存储卡是否可以用，可用进行存储
//                        String state = Environment.getExternalStorageState();
//                        if (state.equals(Environment.MEDIA_MOUNTED)) {
//                            File path = Environment
//                                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//                            File tempFile = new File(path, imageName);
//                            Bitmap bm = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
//                            CropImage(bm, imageName);
//                        } else {
//                            Toast.makeText(getApplicationContext(),
//                                    "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                }
//
//            }
////        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
