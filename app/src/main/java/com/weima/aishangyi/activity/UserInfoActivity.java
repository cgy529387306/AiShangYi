package com.weima.aishangyi.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.liangmutian.mypicker.DatePickerDialog;
import com.example.liangmutian.mypicker.DateUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mb.android.utils.AppHelper;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.CurrentUser;
import com.weima.aishangyi.entity.UploadResp;
import com.weima.aishangyi.map.SelectAddressActivity;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.widget.BottomMenuDialog;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 个人资料
 *
 * @author cgy
 */
public class UserInfoActivity extends BaseActivity{
    private TextView txv_mobile, txv_sex, txv_birth, txv_address;
    private ImageView imv_user_avater;
    private EditText edit_nickname, edit_name, edit_interest, edit_signature;
    private int tag = 0;
    private BottomMenuDialog pickDialog;
    private BottomMenuDialog sexDialog;
    private int sex = 0;//1男 2女 0未知
    private String birthStr;
    private String mTempFilePath = AppHelper.getBaseCachePath()
            .concat(String.valueOf(System.currentTimeMillis())).concat(".jpg");
    private String imageUrl = "";
    private Dialog dateDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("个人信息");
        setContentView(R.layout.activity_user_info);
        initUI();
        initData();
    }

    private void initUI() {
        imv_user_avater = findView(R.id.imv_user_avater);//头像
        edit_nickname = findView(R.id.edit_nickname);//昵称
        edit_name = findView(R.id.edit_name);//姓名
        txv_mobile = findView(R.id.txv_mobile);//手机号码
        txv_sex = findView(R.id.txv_sex);//性别
        txv_birth = findView(R.id.txv_birth);//生日
        txv_address = findView(R.id.txv_address);//我的地址
        edit_interest = findView(R.id.edit_interest);//兴趣爱好
        edit_signature = findView(R.id.edit_signature);//个性签名
        txv_sex.setOnClickListener(mClickListener);
        txv_mobile.setOnClickListener(mClickListener);
        txv_birth.setOnClickListener(mClickListener);
        txv_address.setOnClickListener(mClickListener);
        findView(R.id.btn_save).setOnClickListener(mClickListener);
        findView(R.id.btn_avatar).setOnClickListener(mClickListener);
    }

    private void initData(){
        CurrentUser currentUser = CurrentUser.getInstance();
        if (Helper.isNotEmpty(currentUser)){
            if (Helper.isNotEmpty(currentUser.getIcon())){
                imageUrl = currentUser.getIcon();
                Picasso.with(UserInfoActivity.this).load(currentUser.getIcon()).placeholder(R.drawable.ic_avatar_default).into(imv_user_avater);
            }
            edit_nickname.setText(Helper.isEmpty(currentUser.getNickname()) ? "" : currentUser.getNickname());
            edit_nickname.setSelection(Helper.isEmpty(currentUser.getNickname()) ? 0 : currentUser.getNickname().length());
            edit_name.setText(Helper.isEmpty(currentUser.getName()) ? "" : currentUser.getName());
            txv_mobile.setText(Helper.isEmpty(currentUser.getBind_phone()) ? "未绑定" : currentUser.getBind_phone());
            if (Helper.isEmpty(currentUser.getSex())){
                txv_sex.setText("未知");
            }else{
                txv_sex.setText("1".equals(currentUser.getSex())?"男":"女");
            }
            birthStr = currentUser.getBirth();
            txv_birth.setText(Helper.isEmpty(birthStr)?"请选择":birthStr);
            txv_address.setText(Helper.isEmpty(currentUser.getAddress()) ? "请选择" : currentUser.getAddress());
            edit_interest.setText(Helper.isEmpty(currentUser.getInterest()) ? "" : currentUser.getInterest());
            edit_signature.setText(Helper.isEmpty(currentUser.getSignature()) ? "" : currentUser.getSignature());
        }
    }



    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        ProgressDialogHelper.dismissProgressDialog();
        CommonEntity entity = JsonHelper.fromJson(response,CommonEntity.class);
        if ("200".equals(entity.getCode())){
            LocalBroadcastManager.getInstance(UserInfoActivity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_USER_INFO));
            ToastHelper.showToast("保存成功");
            finish();
        }else{
            ToastHelper.showToast(entity.getMessage());
        }
        return true;
    }

    @Override
    public boolean onResponseError(int gact, String response,
                                   VolleyError error, Object... extras) {
        ProgressDialogHelper.dismissProgressDialog();
        ToastHelper.showToast("保存失败");
        return true;
    }

    private void doSaveInfo() {
        ProgressDialogHelper.showProgressDialog(UserInfoActivity.this, "保存中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("icon", imageUrl);
        requestMap.put("nickname", edit_nickname.getText().toString());
//        requestMap.put("name", edit_name.getText().toString());
        requestMap.put("bind_phone", txv_mobile.getText().toString());
        requestMap.put("sex", sex+"");
        requestMap.put("birth", birthStr);
        requestMap.put("address", txv_address.getText().toString());
        requestMap.put("interest", edit_interest.getText().toString());
        requestMap.put("signature", edit_signature.getText().toString());
        post(ProjectConstants.Url.ACCOUNT_EDIT_INFO, requestMap);
    }

    /**
     * 点击事件
     */
    private OnClickListener mClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_avatar://头像
                    pickPhoto();
                    break;
                case R.id.txv_sex://性别
                    sexDialog = new BottomMenuDialog.Builder(UserInfoActivity.this)
                            .addMenu("男", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sex = 1;
                                    txv_sex.setText("男");
                                    sexDialog.dismiss();
                                }
                            }).addMenu("女", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sex = 2;
                                    txv_sex.setText("女");
                                    sexDialog.dismiss();
                                }
                            }).create();
                    sexDialog.show();
                    break;

                case R.id.txv_birth://年龄
                    showDateDialog();
                    break;

                case R.id.txv_mobile://手机号
                    NavigationHelper.startActivityForResult(UserInfoActivity.this, UserphoneActivity.class, null, ProjectConstants.ActivityRequestCode.REQUEST_BIND_PHONE);

                    break;
                case R.id.txv_address://我的地址
                    NavigationHelper.startActivityForResult(UserInfoActivity.this,SelectAddressActivity.class,null,ProjectConstants.ActivityRequestCode.REQUEST_SELECT_ADDRESS);
                    break;
                case R.id.btn_save:
                    doSaveInfo();
                    break;
                default:
                    break;
            }
        }
    };

    private void showDateDialog() {
        try {
            Date date = null;
            if (Helper.isNotEmpty(birthStr)) {
                date = Helper.string2Date(birthStr, "yyyy-MM-dd");
            } else {
                date = new Date();
            }
            List<Integer> dateList = DateUtil.getDateForString(Helper.date2String(date,"yyyy-MM-dd"));
            DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
            builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
                @Override
                public void onDateSelected(int[] dates) {
                    String dateStr = dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                            + (dates[2] > 9 ? dates[2] : ("0" + dates[2]));
                    Date date1 = Helper.string2Date(dateStr,"yyyy-MM-dd");
                    if (date1.compareTo(new Date()) > 0) {
                        ToastHelper.showToast("选择的时间要小于当前时间!请重新选择.");
                    } else {
                        birthStr = dateStr;
                        txv_birth.setText(birthStr);
                        dateDialog.dismiss();
                    }
                }

                @Override
                public void onCancel() {
                    dateDialog.dismiss();
                }
            });
            builder.setSelectYear(dateList.get(0) - 1);
            builder.setSelectMonth(dateList.get(1) - 1);
            builder.setSelectDay(dateList.get(2) - 1);
            dateDialog = builder.create();
            dateDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //begin region 上传头像
    private void pickPhoto() {
        if (pickDialog == null) {
            pickDialog = new BottomMenuDialog.Builder(UserInfoActivity.this)
                    .addMenu("拍照", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickImageFromCamera();
                            pickDialog.dismiss();
                        }
                    }).addMenu("相册", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickImageFromGallery();
                            pickDialog.dismiss();
                        }
                    }).create();
        }
        pickDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1:
                // 相册
                cropPickedImage(data.getData());
                break;
            case 2:
                // 拍照
                File file = new File(mTempFilePath);
                if (!file.exists()) {
                    return;
                }
                cropPickedImage(Uri.parse("file://".concat(mTempFilePath)));
                break;
            case 3:
                upload(ProjectConstants.Url.FILE_UPLOAD, "file", mTempFilePath, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                        if (responseBody != null){
                            String response = new String(responseBody);
                            if (Helper.isNotEmpty(response)){
                                UploadResp entity = JsonHelper.fromJson(response,UploadResp.class);
                                if (Helper.isNotEmpty(entity) && "200".equals(entity.getCode())){
                                    if (Helper.isNotEmpty(entity.getData()) && Helper.isNotEmpty(entity.getData().getUrl())){
                                        imageUrl = entity.getData().getUrl();
                                        Picasso.with(UserInfoActivity.this).load(imageUrl).placeholder(R.drawable.ic_avatar_default).into(imv_user_avater);
                                    }
                                }else{
                                    ToastHelper.showToast("上传失败！");
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                        ToastHelper.showToast("上传失败！");
                    }
                });
                break;
            case ProjectConstants.ActivityRequestCode.REQUEST_SELECT_ADDRESS:
                String address = data.getStringExtra(ProjectConstants.BundleExtra.KEY_ADDRESS_NAME);
                if (Helper.isNotEmpty(address)) {
                    txv_address.setText(address);
                }
                break;
            case ProjectConstants.ActivityRequestCode.REQUEST_BIND_PHONE:
                String phone = data.getStringExtra(ProjectConstants.BundleExtra.KEY_BIND_PHONE);
                if (Helper.isNotEmpty(phone)) {
                    txv_mobile.setText(phone);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 相册图片选取
     */
    private void pickImageFromGallery() {
        try {
            Intent intentFromGallery = new Intent();
            // 设置文件类型
            intentFromGallery.setType("image/*");
            intentFromGallery.setAction(Intent.ACTION_PICK);
            startActivityForResult(intentFromGallery, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照获取图片
     */
    private void pickImageFromCamera() {
        try {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            // 图片缓存的地址
            mTempFilePath = AppHelper.getBaseCachePath()
                    .concat(String.valueOf(System.currentTimeMillis()))
                    .concat(".jpg");
            File file = new File(mTempFilePath);
            Uri uri = Uri.fromFile(file);
            // 设置图片的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪选取的图片
     *
     * @param uri
     */
    private void cropPickedImage(Uri uri) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");// 可裁剪
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("scale", true);

            // 图片缓存的地址
            mTempFilePath = AppHelper.getBaseCachePath()
                    .concat(String.valueOf(System.currentTimeMillis()))
                    .concat(".jpg");
            File file = new File(mTempFilePath);
            Uri uriCache = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriCache);
            intent.putExtra("outputFormat", "JPEG");// 图片格式
            intent.putExtra("noFaceDetection", true);// 取消人脸识别
            intent.putExtra("return-data", false);
            startActivityForResult(intent, 3);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


    //end region 上传头像


}
