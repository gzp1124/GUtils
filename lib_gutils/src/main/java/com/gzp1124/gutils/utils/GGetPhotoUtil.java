package com.gzp1124.gutils.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 请求 相册，相机 获取照片，可以设置裁剪和不裁剪
 *
 * start:
        getPhotoUtil = GetPhotoUtil.getIns(MainActivity.this);
        getPhotoUtil.goToSelectPicture(GetPhotoUtil.ACTION_TYPE_ALBUM,true);
 * get:
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            Bitmap bitmap = getPhotoUtil.onActivityResult(true,requestCode, resultCode, data);
            if (bitmap != null) {
                Toast.makeText(this, "haha" + bitmap, Toast.LENGTH_SHORT).show();
            }
        }
 *
 * author：高志鹏 on 16/4/21 14:16
 * email:imbagaozp@163.com
 */
public class GGetPhotoUtil {

    Activity mActivity;
    private static GGetPhotoUtil mGetPhotoUtil;
    private final static int CROP = 200;
    //相册
    public static final int ACTION_TYPE_ALBUM = 0;
    //相机
    public static final int ACTION_TYPE_PHOTO = 1;
    /** 请求相册 */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
    /** 请求相机 */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
    /** 请求裁剪 */
    public static final int REQUEST_CODE_GETIMAGE_BYCROP = 2;
    private String protraitPath;
    private File protraitFile;
    private Uri origUri;
    private Uri cropUri;
    private final static String FILE_SAVEPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + File.separator
            + "cache" + File.separator + "Portrait" + File.separator;


    private GGetPhotoUtil(Activity activity){
        mActivity = activity;
    }

    public static GGetPhotoUtil getIns(Activity activity){
//        if (mGetPhotoUtil == null){
            mGetPhotoUtil = new GGetPhotoUtil(activity);
//        }
        return mGetPhotoUtil;
    }

    /**
     * 获取照片入口
     *
     *  ACTION_TYPE_ALBUM 相册
     *  ACTION_TYPE_PHOTO 相机
     * @param position
     */
    public void goToSelectPicture(int position,boolean isNeedCaijian) {
        switch (position) {
            case ACTION_TYPE_ALBUM:
                if (isNeedCaijian) {
                    startImagePick();//相册
                }else{
                    getImageFromAlbum();
                }
                break;
            case ACTION_TYPE_PHOTO:
                if (isNeedCaijian) {
                    startTakePhoto();//相机
                }else{
                    getImageFromCamera();
                }
                break;
            default:
                break;
        }
    }

    private void startTakePhoto() {
        Intent intent;
        // 判断是否挂载了SD卡
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "cache" + File.separator + "Camera" + File.separator;
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (TextUtils.isEmpty(savePath)) {
            GToastUtil.showToast("无法保存照片，请检查SD卡是否挂载");
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String fileName = "osc_" + timeStamp + ".jpg";// 照片命名
        File out = new File(savePath, fileName);
        Uri uri = Uri.fromFile(out);
        origUri = uri;

        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        mActivity.startActivityForResult(intent,REQUEST_CODE_GETIMAGE_BYCAMERA);
    }

    /**
     * 选择图片裁剪
     */
    private void startImagePick() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            mActivity.startActivityForResult(Intent.createChooser(intent, "选择图片"),REQUEST_CODE_GETIMAGE_BYCROP);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            mActivity.startActivityForResult(Intent.createChooser(intent, "选择图片"),REQUEST_CODE_GETIMAGE_BYCROP);
        }
    }

    /**
     * 返回获取到的图片
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    public Bitmap onActivityResult(boolean isNeedcaijian,final int requestCode, final int resultCode,
                                 final Intent data) {
        Bitmap bp = null;
        if (isNeedcaijian){
            bp = caijianResult(requestCode,resultCode,data);
        }else{
            bp = noCaijianResult(requestCode,resultCode,data);
        }
        return bp;
    }

    /**
     * 裁剪的result
     * @param requestCode
     * @param resultCode
     * @param imageReturnIntent
     * @return
     */
    private Bitmap caijianResult(final int requestCode, final int resultCode,
                                 final Intent imageReturnIntent){
        Bitmap bp = null;
        if (resultCode != Activity.RESULT_OK) {
            return null;
        }

        switch (requestCode) {
            case REQUEST_CODE_GETIMAGE_BYCAMERA:
                startActionCrop(origUri);// 拍照后裁剪
                break;
            case REQUEST_CODE_GETIMAGE_BYCROP:
                startActionCrop(imageReturnIntent.getData());// 选图后裁剪
                break;
            case REQUEST_CODE_GETIMAGE_BYSDCARD:
                bp = getPhoto();
                break;
        }
        return bp;
    }

    /**
     * 上传新照片
     */
    private Bitmap getPhoto() {
        //获取到选取的图片，进行相应的操作
        Bitmap bp = BitmapFactory.decodeFile(protraitPath);
        return bp;
    }

    /**
     * 拍照后裁剪
     *
     * @param data 原始图片
     */
    private void startActionCrop(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("output", this.getUploadTempFile(data));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", CROP);// 输出图片大小
        intent.putExtra("outputY", CROP);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        mActivity.startActivityForResult(intent,REQUEST_CODE_GETIMAGE_BYSDCARD);
    }

    // 裁剪头像的绝对路径
    private Uri getUploadTempFile(Uri uri) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
//            AppContext.showToast("无法保存上传的头像，请检查SD卡是否挂载");
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String thePath = GImageUtil.getAbsolutePathFromNoStandardUri(uri);

        // 如果是标准Uri
        if (TextUtils.isEmpty(thePath)) {
            thePath = GImageUtil.getAbsoluteImagePath(mActivity, uri);
        }
        String ext = GFileUtil.getFileFormat(thePath);
        ext = TextUtils.isDigitsOnly(ext) ? "jpg" : ext;
        // 照片命名
        String cropFileName = "aixin_crop_" + timeStamp + "." + ext;
        // 裁剪头像的绝对路径
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);

        cropUri = Uri.fromFile(protraitFile);
        return this.cropUri;
    }
    //裁剪
    //=======================================
    //不裁剪
    private int REQUEST_CODE_PICK_IMAGE = 1;
    private int REQUEST_CODE_CAPTURE_CAMEIA = 2;

    //相册
    public void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        mActivity.startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    //相机
    public void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            mActivity.startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        }
        else {
            GToastUtil.showToast("请确认已经插入SD卡");
        }
    }
    /**
     * 保存图片
     * @param photo
     * @param spath
     * @return
     */
    public boolean saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Bitmap getBitmapFromUri(Uri uri)
    {
        try
        {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), uri);
            return bitmap;
        }
        catch (Exception e)
        {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap noCaijianResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri uri = data.getData();
            //to do find the path of pic by uri
            Bitmap photo = getBitmapFromUri(uri);
//            Toast.makeText(mActivity,photo+"11",Toast.LENGTH_SHORT).show();
            return photo;
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            Uri uri = data.getData();
            if (uri == null) {
                //use bundle to get data
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                    //spath :生成图片取个名字和路径包含类型
//                    saveImage(photo, "a11.jpg");
//                    Toast.makeText(mActivity,photo+"22",Toast.LENGTH_SHORT).show();
                    return photo;
                } else {
//                    Toast.makeText(mActivity, "err****", Toast.LENGTH_LONG).show();
                    return null;
                }
            } else {
                //to do find the path of pic by uri
                Bitmap photo = getBitmapFromUri(uri);
//                Toast.makeText(mActivity,photo+"3333",Toast.LENGTH_SHORT).show();
                return photo;
            }
        }
        return null;
    }
}
