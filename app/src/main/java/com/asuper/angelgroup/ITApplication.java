package com.asuper.angelgroup;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.asuper.angelgroup.common.set.AppInit;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 应用程序application
 *
 * @author super
 */
public class ITApplication extends Application {

   /* public LocationService locationService;
    public Vibrator mVibrator;*/

    @Override
    public void onCreate() {
        super.onCreate();
        AppInit.initEnvironment(this, true, true);

        /***
         * 初始化定位sdk，建议在Application中创建
         */
       /* locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        WriteLog.getInstance().init(); // 初始化日志
        SDKInitializer.initialize(getApplicationContext());*/

        /*if (DateTool.getSysDateFormatyyyyMM().equals("2017-12")) {
            String s = null;
            System.out.print("s===============" + s.length());
        }*/

        // 创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }


}
