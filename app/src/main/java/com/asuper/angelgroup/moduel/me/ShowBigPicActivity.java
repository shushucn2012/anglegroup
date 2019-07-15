package com.asuper.angelgroup.moduel.me;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.tool.DevAttr;
import com.asuper.angelgroup.common.tool.ImageManager;
import com.asuper.angelgroup.moduel.me.adapter.PhotosViewPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * 照片大图查看页
 */
public class ShowBigPicActivity extends BaseActivity {

    private ImageView img_show;
    private String bmpPath;
    private int position;
    private ViewPager viewpager_photos;// 照片浏览
    private ArrayList<String> urlList;
    private String url;
    private ArrayList<View> views = new ArrayList<View>();
    ;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_show_bigpic);
    }

    @Override
    public void initView() {
        setPagTitle("图片展示");
        img_show = (ImageView) findViewById(R.id.img_show);
        //btn_delete = (Button) findViewById(R.id.btn_delete);
        viewpager_photos = (ViewPager) findViewById(R.id.viewpager_photos);
    }

    @Override
    public void initData() {
        bmpPath = getIntent().getStringExtra("big_pic");
        if (!TextUtils.isEmpty(bmpPath)) {
            position = getIntent().getIntExtra("position", -1);
            Bitmap bmp = ImageManager.getInstance().readFileBitMap(bmpPath);
            img_show.setImageBitmap(bmp);
            scaleImageView(img_show, bmp);
        }
        url = getIntent().getStringExtra("picUrl");
        if (!TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    showDialog();
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    dismissDialog();
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    dismissDialog();
                    img_show.setImageBitmap(loadedImage);
                    scaleImageView(img_show, loadedImage);
                }
            });
        }
        urlList = getIntent().getStringArrayListExtra("urlList");
        if (urlList != null && urlList.size() > 1) {
            viewpager_photos.setVisibility(View.VISIBLE);
            img_show.setVisibility(View.GONE);
            for (int i = 0; i < urlList.size(); i++) {
                View viewItem = LayoutInflater.from(mContext).inflate(R.layout.showbigpic_vp_item, null);
                ImageView img_big_photo = (ImageView) viewItem.findViewById(R.id.img_big_photo);
                String path = urlList.get(i);
                ImageLoader.getInstance().displayImage(path.startsWith("http") ? path : "file:///" + path, img_big_photo, options);
                views.add(viewItem);
            }
            viewpager_photos.setAdapter(new PhotosViewPagerAdapter(views));
            int index = urlList.indexOf(url);
            viewpager_photos.setCurrentItem(index);
            setPagTitle("图片展示(" + (index + 1) + "/" + urlList.size() + ")");
        }
    }

    @Override
    public void initListener() {
        viewpager_photos.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPagTitle("图片展示(" + (position + 1) + "/" + urlList.size() + ")");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 缩放到适应屏幕
     */
    public void scaleImageView(ImageView imgView, Bitmap bmp) {
        Matrix matrix = new Matrix();
        Matrix currentMaritx = new Matrix();
        currentMaritx.set(imgView.getImageMatrix());// 记录ImageView当期的移动位置
        float scale = DevAttr.getScreenWidth(mContext) / bmp.getWidth();// 放大倍数
        matrix.set(currentMaritx);
        matrix.postScale(scale, scale);
    }

}
