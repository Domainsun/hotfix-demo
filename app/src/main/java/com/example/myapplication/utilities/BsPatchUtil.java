package com.example.myapplication.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.AppUtils;

import java.io.File;

public class BsPatchUtil {
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * @param oldApkPath 旧apk文件路径
     * @param newApkPath 新apk文件路径
     * @param patchPath  生成的差分包的存储路径
     */
    public static native void patch(String oldApkPath, String newApkPath, String patchPath);


    /**
     * 获取当前应用的安装包路径
     *
     * @param context
     * @return
     */
    public static String extract(Context context) {
        context = context.getApplicationContext();
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String apkPath = applicationInfo.sourceDir;
        Log.d("hongyang", apkPath);
        return apkPath;
    }

    /**
     * 安装应用
     *
     * @param context
     * @param apkPath
     */
    public static void install(Context context, String apkPath) {
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.setDataAndType(Uri.fromFile(new File(apkPath)),
//                "application/vnd.android.package-archive");
//        context.startActivity(i);


        File apk = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(context, "com.yiqi.hotfit.demo.fileprovider", apk);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }else{
            intent.setDataAndType(Uri.fromFile(apk),"application/vnd.android.package-archive");
        }
        try {
            context.startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }


    }


    public static void doPatch(Context context) {


        final File destApk = new File(Environment.getExternalStorageDirectory(), "hotfix-1.apk");
        final File patch = new File(Environment.getExternalStorageDirectory(), "patch.patch");

//        //一定要检查文件都存在
//        patch(extract(context),
//                destApk.getAbsolutePath(),
//                patch.getAbsolutePath());
//        if (destApk.exists())
//            install(context, destApk.getAbsolutePath());


        //已安装的旧apk的路径
        String oldApkPath = extract(context);
        // 差分包的路径
        String patchPath = patch.getAbsolutePath();
        // 合成之后的新apk的存储路径
        String newApkPath = destApk.getAbsolutePath();
        //合成apk包
        patch(oldApkPath, patchPath, newApkPath);
        if (destApk.exists()){
            AppUtils.installApp(destApk);
        }
//            install(context, destApk.getAbsolutePath());


    }


}
