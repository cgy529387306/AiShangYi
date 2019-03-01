package com.weima.aishangyi.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mb.android.utils.Helper;
import com.mb.android.utils.JsonHelper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.weima.aishangyi.R;
import com.weima.aishangyi.adapter.PicAdapter;
import com.weima.aishangyi.base.BaseActivity;
import com.weima.aishangyi.constants.ProjectConstants;
import com.weima.aishangyi.entity.CommonEntity;
import com.weima.aishangyi.entity.UploadResp;
import com.weima.aishangyi.map.SelectAddressActivity;
import com.weima.aishangyi.utils.ImageFactory;
import com.weima.aishangyi.utils.NavigationHelper;
import com.weima.aishangyi.widget.BottomMenuDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.utils.FileUtils;

/**
 * 作者：cgy on 16/12/6 23:24
 * 邮箱：593960111@qq.com
 * 发布才艺
 */
public class TalentPostActivity extends BaseActivity implements View.OnClickListener{
    public static final int REQUEST_DATA_WAY = 0xf1;
    public static final int REQUEST_CODE_CAMERA = 0xf3;
    public static final int REQUEST_CODE_ALUBM = 0xf4;
    private EditText mInputView;
    private GridView mPicGridView;
    private List<String> pathList = new ArrayList<String>();
    private File mTmpFile = null;
    private ImgDel imgDel = null;
    private PicAdapter mAdaptar = null;
    private TextView btn_select_address,btn_select_visible;
    private BottomMenuDialog bottomMenuDialog;
    private BottomMenuDialog visibleDialog;
    private int isVisible = 0;//是否公开 0是，1否
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle("发布动态");
        setRightButton("发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCommit();
            }
        });
        setContentView(R.layout.activity_talent_post);
        initUI();
    }

    private void initUI() {
        btn_select_address = findView(R.id.btn_select_address);
        btn_select_visible = findView(R.id.btn_select_visible);
        mInputView = findView(R.id.add_text_input);
        mPicGridView = findView(R.id.add_pic_grid);
        imgDel = new ImgDel();
        pathList.add(R.drawable.ic_pic_add + "");
        mAdaptar = new PicAdapter(TalentPostActivity.this, pathList, imgDel);
        mPicGridView.setAdapter(mAdaptar);
        btn_select_address.setOnClickListener(this);
        btn_select_visible.setOnClickListener(this);
    }

    @Override
    public boolean onResponseSuccess(int gact, String response,
                                     Object... extras) {
        super.onResponseSuccess(gact, response, extras);
        ProgressDialogHelper.dismissProgressDialog();
        CommonEntity entity = JsonHelper.fromJson(response, CommonEntity.class);
        if ("200".equals(entity.getCode())){
            LocalBroadcastManager.getInstance(TalentPostActivity.this).sendBroadcast(new Intent(ProjectConstants.BroadCastAction.UPDATE_TALENT_LIST));
            ToastHelper.showToast("发布成功！");
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
        ToastHelper.showToast("发布失败");
        return true;
    }

    private void doCommit(){
        String content = mInputView.getText().toString();
        if (Helper.isEmpty(content)){
            ToastHelper.showToast("说点什么吧...");
            return;
        }
        ProgressDialogHelper.showProgressDialog(TalentPostActivity.this, "提交中...");
        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("content",content);
        requestMap.put("status",isVisible);
        String address = btn_select_address.getText().toString();
        if (!"请选择".equals(address)){
            requestMap.put("address",address);
        }
        if (Helper.isNotEmpty(pathList) && pathList.size()>1) {
            pathList.remove(pathList.size() - 1);
            requestMap.put("images",pathList);
        }
        post(ProjectConstants.Url.TALENT_PUBLISH, requestMap);
    }

    /**
     * 图片选择
     */
    private void selectImage() {
        try {
            Intent intent = new Intent(TalentPostActivity.this, MultiImageSelectorActivity.class);
            // 是否显示拍摄图片
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
            // 最大可选择图片数量
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 10-pathList.size());
            // 选择模式
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
            startActivityForResult(intent, REQUEST_CODE_ALUBM);
        }catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择相机
     */
    private void showCameraAction() {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(this.getPackageManager()) != null){
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            mTmpFile = FileUtils.createTmpFile(this);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
        }else{
            Toast.makeText(this, R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理图片选择结果
     * @param data
     */
    private void handleImageSelect(Intent data) {
        if(null == data) {
            return;
        }
        List<String> selecImags = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
        if(null == selecImags || 0 == selecImags.size()) {
            return;
        }
        for (String path:selecImags) {
            uploadPhoto(path);
        }
    }

    private void handleImageCamera(Intent data){
        try {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Uri uri = data.getData();
                    File pic = new File(new URI(uri.toString()));
                    uploadPhoto(pic.getPath());
                }
            }else{
                Bitmap bm = ImageFactory.getImageThumbnail(mTmpFile.getPath(), 1024 * 1024);
                if (Helper.isNotEmpty(bm)){
                    try {
                        FileOutputStream fos = new FileOutputStream(new File(mTmpFile.getPath()));
                        fos.write(ImageFactory.bitmapToBytes(bm, Bitmap.CompressFormat.JPEG));
                        fos.flush();
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    uploadPhoto(mTmpFile.getPath());
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void uploadPhoto(final String path){
        ProgressDialogHelper.showProgressDialog(TalentPostActivity.this, "上传中...");
        upload(ProjectConstants.Url.FILE_UPLOAD, "file", path, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                ProgressDialogHelper.dismissProgressDialog();
                if (responseBody != null){
                    String response = new String(responseBody);
                    if (Helper.isNotEmpty(response)){
                        UploadResp entity = JsonHelper.fromJson(response, UploadResp.class);
                        if (Helper.isNotEmpty(entity) && "200".equals(entity.getCode())){
                            if (Helper.isNotEmpty(entity.getData()) && Helper.isNotEmpty(entity.getData().getUrl())){
                                pathList.add(0, entity.getData().getUrl());
                                mAdaptar.updateList(pathList);
                            }
                        }else{
                            Log.e("=====upload===","上次失败,path:"+path);
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                ProgressDialogHelper.dismissProgressDialog();
                Log.e("=====upload===","上次失败,path:"+path);
            }
        });
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        if (request == REQUEST_CODE_CAMERA) {
            handleImageCamera(data);
        } else if (request == REQUEST_CODE_ALUBM) {
            handleImageSelect(data);
        } else if ( request ==ProjectConstants.ActivityRequestCode.REQUEST_SELECT_ADDRESS) {
            String address = data.getStringExtra(ProjectConstants.BundleExtra.KEY_ADDRESS_NAME);
            btn_select_address.setText(Helper.isEmpty(address)?"请选择":address);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_select_address){
            NavigationHelper.startActivityForResult(TalentPostActivity.this, SelectAddressActivity.class, null, ProjectConstants.ActivityRequestCode.REQUEST_SELECT_ADDRESS);
        }else if(id == R.id.btn_select_visible){
            visibleDialog = new BottomMenuDialog.Builder(TalentPostActivity.this)
                    .addMenu("公开", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isVisible = 0;
                            btn_select_visible.setText("公开");
                            visibleDialog.dismiss();
                        }
                    }).addMenu("不公开", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isVisible = 1;
                            btn_select_visible.setText("不公开");
                            visibleDialog.dismiss();
                        }
                    }).create();
            visibleDialog.show();
        }
    }

    private class ImgDel implements PicAdapter.DelImg {

        @Override
        public void delImg(int position) {
            pathList.remove(position);
            mAdaptar.updateList(pathList);
        }

        @Override
        public void addImg() {
           if (pathList.size()<10){
               bottomMenuDialog = new BottomMenuDialog.Builder(TalentPostActivity.this)
                       .addMenu("拍照", new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               showCameraAction();
                               bottomMenuDialog.dismiss();
                           }
                       }).addMenu("相册", new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               selectImage();
                               bottomMenuDialog.dismiss();
                           }
                       }).create();
               bottomMenuDialog.show();
           }else{
               ToastHelper.showToast("最多只能选择9张图片");
           }
        }
    }
}
