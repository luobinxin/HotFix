package cn.com.startai.testhotfix;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.tinker.entry.DefaultApplicationLike;

import java.util.Locale;

import cn.com.startai.testhotfix.utils.TAndL;

/**
 * Created by Robin on 2018/12/5.
 * qq: 419109715 彬影
 * <p>
 * 自定义ApplicationLike类.
 * <p>
 * 注意：这个类是Application的代理类，以前所有在Application的实现必须要全部拷贝到这里<br/>
 */


public class HotFixApplicationLike extends DefaultApplicationLike {

    public static final String TAG = "HotFixApplicationLike";

    public HotFixApplicationLike(Application application, int tinkerFlags,
                                 boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                                 long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        // 把原来在 Application onCreate()中的代码全部写到此处


        //监听补丁事件回调，可选
        listenPatch();

        // 多渠道需求塞入
         String channel = WalleChannelReader.getChannel(getApplication());
         Bugly.setAppChannel(getApplication(), channel);

        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(getApplication(), "effa71fc15", true);

    }

    private void listenPatch() {

//        // 设置是否开启热更新能力，默认为false
        Beta.enableHotfix = true;
//        // 设置是否自动下载补丁，默认为true
//        Beta.canAutoDownloadPatch = true;
//        // 设置是否自动合成补丁，默认为true
//        Beta.canAutoPatch = true;
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = true;

        // 补丁回调接口
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFile) {

                TAndL.TL(getApplication(), "onPatchReceived :补丁下载地址 " + patchFile);
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {

                TAndL.TL(getApplication(), "onDownloadReceived:" + String.format(Locale.getDefault(), "%s %d%%",
                        Beta.strNotificationDownloading,
                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)));
            }

            @Override
            public void onDownloadSuccess(String msg) {

                TAndL.TL(getApplication(), "onDownloadSuccess:补丁下载成功 " + msg);
            }

            @Override
            public void onDownloadFailure(String msg) {

                TAndL.TL(getApplication(), "onDownloadFailure:补丁下载失败 " + msg);
            }

            @Override
            public void onApplySuccess(String msg) {
                TAndL.TL(getApplication(), "onApplySuccess:补丁应用成功" + msg);
            }

            @Override
            public void onApplyFailure(String msg) {

                TAndL.TL(getApplication(), "onApplyFailure:补丁应用失败" + msg);
            }

            @Override
            public void onPatchRollback() {

                TAndL.TL(getApplication(), "onPatchRollback:补丁回滚");
            }
        };

    }


    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        Beta.installTinker(this);
    }


}
