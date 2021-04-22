package com.sanleng.emergencystation.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.dialog.PhotoPopupWindow;
import com.sanleng.emergencystation.utils.Constance;
import com.sanleng.emergencystation.utils.FaceRegistrationUplaod;
import com.sanleng.emergencystation.utils.InfoPrefs;
import com.sanleng.emergencystation.utils.PictureUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 人脸注册
 * @author qiaoshi
 */
public class FaceRegistrationActivity extends BaseActivity implements OnClickListener {
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final int REQUEST_CHANGE_USER_NICK_NAME = 10;
    private static final String IMAGE_FILE_NAME = "user_head_icon.jpg";
    private RelativeLayout back;
    private Button commit;
    private Button registration;
    private ImageView face_image;
    private PhotoPopupWindow mPhotoPopupWindow;
    private FaceRegistrationUplaod post;
    private boolean whether = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faceuregistrationactivity);
        initview();
        InfoPrefs.init("user_info");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.faceuregistrationactivity;
    }

    // 初始化数据
    private void initview() {
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        commit = findViewById(R.id.commit_task);
        registration = findViewById(R.id.registration);
        commit.setOnClickListener(this);
        registration.setOnClickListener(this);
        face_image = findViewById(R.id.face_image);
    }

    private void showHeadImage() {
        boolean isSdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);// 判断sdcard是否存在
        if (isSdCardExist) {
            String path = InfoPrefs.getData(Constance.UserInfo.HEAD_IMAGE);//获取图片路径
            File file = new File(path);
            if (file.exists()) {
                whether = true;
                Bitmap bm = BitmapFactory.decodeFile(path);
                // 将图片显示到ImageView中
                face_image.setImageBitmap(bm);
            } else {
                face_image.setImageResource(R.drawable.facer_icon);
            }
        } else {
            face_image.setImageResource(R.drawable.facer_icon);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.commit_task:
                //创建存放头像的文件夹
                PictureUtil.mkdirMyPetRootDirectory();
                mPhotoPopupWindow = new PhotoPopupWindow(FaceRegistrationActivity.this, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 文件权限申请
                        if (ContextCompat.checkSelfPermission(FaceRegistrationActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            // 权限还没有授予，进行申请
                            ActivityCompat.requestPermissions(FaceRegistrationActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200); // 申请的 requestCode 为 200
                        } else {
                            // 如果权限已经申请过，直接进行图片选择
                            mPhotoPopupWindow.dismiss();
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            // 判断系统中是否有处理该 Intent 的 Activity
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(intent, REQUEST_IMAGE_GET);
                            } else {
                                Toast.makeText(FaceRegistrationActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 拍照及文件权限申请
                        if (ContextCompat.checkSelfPermission(FaceRegistrationActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                || ContextCompat.checkSelfPermission(FaceRegistrationActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            // 权限还没有授予，进行申请
                            ActivityCompat.requestPermissions(FaceRegistrationActivity.this,
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300); // 申请的 requestCode 为 300
                        } else {
                            // 权限已经申请，直接拍照
                            mPhotoPopupWindow.dismiss();
                            imageCapture();
                        }
                    }
                });
                View rootView = LayoutInflater.from(FaceRegistrationActivity.this).inflate(R.layout.faceuregistrationactivity, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.registration://人脸注册
                List<String> list = new ArrayList<>();
                String path = InfoPrefs.getData(Constance.UserInfo.HEAD_IMAGE);// 获取图片路径
                list.add(path);
                if (whether == true) {
                    post = new FaceRegistrationUplaod(FaceRegistrationActivity.this, list, m_handler);
                    post.execute();
                } else {
                    Toast.makeText(FaceRegistrationActivity.this, "请添加人脸照片", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    File cropFile = new File(PictureUtil.getMyPetRootDirectory(), "crop.jpg");
                    Uri cropUri = Uri.fromFile(cropFile);
                    setPicToView(cropUri);
                    break;
                // 相册选取
                case REQUEST_IMAGE_GET:
                    Uri uri = PictureUtil.getImageUri(this, data);
                    startPhotoZoom(uri);
                    break;
                // 拍照
                case REQUEST_IMAGE_CAPTURE:
                    File pictureFile = new File(PictureUtil.getMyPetRootDirectory(), IMAGE_FILE_NAME);
                    Uri pictureUri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        pictureUri = FileProvider.getUriForFile(FaceRegistrationActivity.this, "com.sanleng.emergencystation.fileprovider", pictureFile);
                    } else {
                        pictureUri = Uri.fromFile(pictureFile);
                    }
                    startPhotoZoom(pictureUri);
                    break;
                // 获取changeinfo销毁后回传的数据
                case REQUEST_CHANGE_USER_NICK_NAME:
                    String returnData = data.getStringExtra("data_return");
                    InfoPrefs.setData(Constance.UserInfo.NAME, returnData);
                    break;

                default:
            }
        } else {

        }
    }

    public void setPicToView(Uri uri) {
        if (uri != null) {
            Bitmap photo = null;
            try {
                photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File(PictureUtil.getMyPetRootDirectory(), "Icon");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                    } else {
                    }
                }
                File file = new File(dirFile, IMAGE_FILE_NAME);
                InfoPrefs.setData(Constance.UserInfo.HEAD_IMAGE, file.getPath());
                // 保存图片
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 在视图中显示图片
            showHeadImage();
        }
    }

    private void startPhotoZoom(Uri uri) {
        //保存裁剪后的图片
        File cropFile = new File(PictureUtil.getMyPetRootDirectory(), "crop.jpg");
        try {
            if (cropFile.exists()) {
                cropFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri cropUri;
        cropUri = Uri.fromFile(cropFile);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", false); // no face detection
        startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
    }

//    private File currentImageFile = null;

    private void imageCapture() {
        File pictureFile = new File(PictureUtil.getMyPetRootDirectory(), IMAGE_FILE_NAME);
        Uri cramuri;
        // 启动系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cramuri = FileProvider.getUriForFile(FaceRegistrationActivity.this, "com.sanleng.emergencystation.fileprovider", pictureFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            cramuri = Uri.fromFile(pictureFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cramuri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

    }


    @SuppressLint("HandlerLeak")
    private Handler m_handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 4354343:
                    new SVProgressHUD(FaceRegistrationActivity.this).showSuccessWithStatus("注册成功");
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            finish();
                        }
                    }, 2000);
                    break;
                default:
                    break;
            }

        }
    };
}
