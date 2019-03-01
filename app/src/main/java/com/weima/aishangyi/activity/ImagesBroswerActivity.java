package com.weima.aishangyi.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.android.utils.Helper;
import com.mb.android.utils.ProgressDialogHelper;
import com.mb.android.utils.ToastHelper;
import com.squareup.picasso.Picasso;
import com.weima.aishangyi.R;
import com.weima.aishangyi.utils.ImageDownloadHelper;
import com.weima.aishangyi.widget.WheelViewPager;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * 图片查看器
 *
 * @author cgy
 */
public class ImagesBroswerActivity extends FragmentActivity {
    public final static String sImageList = "sImageList";
    public final static String sImagePostion = "sImagePostion";
    private List<String> mImageUrls = new ArrayList<>();
    private int mPostion = 0;
    private ImageView mImvDownload;
    private TextView mTxvPagerNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_broswer);
        initIntentData();
        initUI();
    }

    private void initIntentData(){
        try {
            mImageUrls = (List<String>) getIntent().getSerializableExtra(sImageList);
            mPostion = getIntent().getIntExtra(sImagePostion,0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initUI(){
        WheelViewPager viewPager = (WheelViewPager)findViewById(R.id.vip_image_viewpager);
        mImvDownload = (ImageView) findViewById(R.id.imv_download);
        mTxvPagerNumber = (TextView) findViewById(R.id.txv_pager_number);
        mTxvPagerNumber.setText((mPostion+1) +"/" + mImageUrls.size());
        viewPager.setAdapter(new ImageBrowserAdapter());
        viewPager.setCurrentItem(mPostion);
        mImvDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ProgressDialogHelper.showProgressDialog(ImagesBroswerActivity.this, "保存图片", "图片正在保存中，请稍等... ");
                ImageDownloadHelper.startDownLoadImg(mImageUrls.get(mPostion), new ImageDownloadHelper.DownLoadCallBack() {

                    @Override
                    public void onSuccess(String url, Object... extras) {
                        ProgressDialogHelper.dismissProgressDialog();
                        ToastHelper.showToast("保存成功,图片地址：sdcard/com.weima.aishangyi/download/");
                    }

                    @Override
                    public void onLoading(long current, Object... extras) {

                    }

                    @Override
                    public void onFail(String url, Object... extras) {
                        ProgressDialogHelper.dismissProgressDialog();
                        ToastHelper.showToast("保存失败");
                    }
                });
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mTxvPagerNumber.setText((position + 1) + "/" + mImageUrls.size());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }



    /**
     * 图片浏览适配器
     * @author cgy
     *
     */
    class ImageBrowserAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            View view = LayoutInflater.from(ImagesBroswerActivity.this).inflate(R.layout.item_image_pager, viewGroup, false);
            assert view != null;
            PhotoView mImagePager = (PhotoView)view.findViewById(R.id.full_image);
            if (Helper.isNotEmpty(mImageUrls) && Helper.isNotEmpty(mImageUrls.get(position))) {
                Picasso.with(ImagesBroswerActivity.this).load(mImageUrls.get(position)).placeholder(R.drawable.img_default).into(mImagePager);
            }
            viewGroup.addView(view, 0);
            return view;
        }
    }

}
