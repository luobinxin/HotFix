package cn.com.startai.testhotfix.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.tencent.tinker.android.dex.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Robin on 2018/6/22.
 * qq: 419109715 彬影
 */

public class TAndL {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static final String TAG = "TAndL";
    private static Handler hd;
    private static PrintWriter pwTemp;
    private static Toast toast;
    private int title;

    public static void L(String text) {
        Log.i(TAG, text);
    }

    public static void T(final Context context, final String text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {

                    toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                } else {

                    toast.setText(text);
                }
                toast.show();

            }
        });
    }

    public static void TL(Context context, String text) {

        L(text);
        T(context, text);

    }




}
