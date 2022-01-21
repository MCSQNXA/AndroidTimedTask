package mcsq.nxa.timedtask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.util.HashSet;

/**
 * @Author MCSQNXA
 * @CreateTime 2022-01-21 20:57:27
 * @Description
 */
public class TimedTask extends Application implements Application.ActivityLifecycleCallbacks, Thread.UncaughtExceptionHandler {

    @Override
    protected void attachBaseContext(Context base) {
        TimedTask.context = this;

        super.attachBaseContext(base);
        super.registerActivityLifecycleCallbacks(this);

        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * @CreateTime 2022-01-15 23:28:05
     * @Description TimedTask
     */
    @SuppressLint("StaticFieldLeak")
    public static TimedTask context;

    /**
     * @CreateTime 2022-01-01 21:52:44
     * @Description 获取上下文对象
     */
    public static Context getContext() {
        return TimedTask.context.getApplicationContext();
    }

    /**
     * @CreateTime 2022-01-15 23:30:03
     * @Description 获取下载更新文件路径
     */
    public static File getDownloadUpdateFile() {
        return TimedTask.context.getFileStreamPath("Update.apk");
    }

    /**
     * @CreateTime 2022-01-01 21:52:57
     * @Description Activity对象集合
     */
    private static final HashSet<Activity> activitys = new HashSet<>();

    /**
     * @CreateTime 2022-01-01 21:53:16
     * @Description 获取Activity对象
     */
    public static Activity getActivity() {
        Activity[] activitys = TimedTask.activitys.toArray(new Activity[]{});

        if (activitys == null || activitys.length == 0) {
            return null;
        }

        return activitys[activitys.length - 1];
    }

    /**
     * @CreateTime 2022-01-01 21:53:25
     * @Description 关闭软键盘
     */
    public static void hideSoftInput() {
        TimedTask.hideSoftInput(TimedTask.getActivity());
    }

    /**
     * @CreateTime 2022-01-01 21:53:31
     * @Description 关闭软键盘
     */
    public static void hideSoftInput(Activity activity) {
        if (activity != null) {
            View view = activity.getWindow().peekDecorView();

            if (view != null) {
                ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        TimedTask.activitys.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        TimedTask.activitys.remove(activity);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        throwable.printStackTrace();

        for (Activity activity : TimedTask.activitys) {
            activity.finish();
        }

        System.exit(0);
    }


}

