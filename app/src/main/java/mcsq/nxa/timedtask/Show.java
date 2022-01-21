package mcsq.nxa.timedtask;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

/**
 * @Author MCSQNXA
 * @CreateTime 2022-01-01 14:44:29
 * @Description View显示
 */
public class Show {
    /**
     * @CreateTime 2022-01-01 21:58:07
     * @Description 显示文本对话框
     */
    public static synchronized void showMessage(String messgae) {
        Show.showMessage(new Dialog[]{}, messgae);
    }

    /**
     * @CreateTime 2022-01-01 21:58:14
     * @Description 显示文本对话框
     */
    public static synchronized void showMessage(Dialog dismiss, String messgae) {
        Show.showMessage(new Dialog[]{dismiss}, messgae);
    }

    /**
     * @CreateTime 2022-01-01 21:58:29
     * @Description 显示文本对话框
     */
    public static synchronized void showMessage(final Dialog[] dismiss, final String message) {
        final Activity activity = TimedTask.getActivity();

        if (activity == null || message == null) {
            return;
        }

        if (Looper.getMainLooper() == Looper.myLooper()) {
            if (dismiss != null) {
                for (Dialog dialog : dismiss) {
                    dialog.dismiss();
                }
            }

            TextView text = new TextView(activity);
            text.setText(message);
            text.setTextSize(19);
            text.setTextColor(ContextCompat.getColor(activity, R.color.main));

            AlertDialog dialog = new AlertDialog.Builder(activity).
                    setTitle("INFO").
                    setView(text).
                    setCancelable(false).
                    setNegativeButton("好的", null).
                    create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            dialog.show();

            Show.fixDialog(dialog);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (dismiss != null) {
                        for (Dialog dialog : dismiss) {
                            dialog.dismiss();
                        }
                    }

                    TextView text = new TextView(activity);
                    text.setText(message);
                    text.setTextSize(19);
                    text.setTextColor(ContextCompat.getColor(activity, R.color.main));

                    AlertDialog dialog = new AlertDialog.Builder(activity).
                            setTitle("INFO").
                            setView(text).
                            setCancelable(false).
                            setNegativeButton("好的", null).
                            create();
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                    dialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    dialog.show();

                    Show.fixDialog(dialog);
                }
            });
        }
    }

    /**
     * @CreateTime 2022-01-01 21:58:55
     * @Description Toast提示
     */
    public static synchronized void showToast(String message) {
        Show.showToast(null, message);
    }

    /**
     * @CreateTime 2022-01-01 21:59:17
     * @Description Toast提示
     */
    public static synchronized void showToast(final Dialog dismiss, final String message) {
        Activity activity = TimedTask.getActivity();

        if (activity == null || message == null) {
            return;
        }

        if (Looper.getMainLooper() == Looper.myLooper()) {
            if (dismiss != null) {
                dismiss.dismiss();
            }

            TextView text = new TextView(TimedTask.getActivity());
            text.setText(message);
            text.setTextSize(23);

            Toast toast = new Toast(TimedTask.getActivity());
            toast.setGravity(Gravity.BOTTOM, 12, 60);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(text);
            toast.show();
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (dismiss != null) {
                        dismiss.dismiss();
                    }

                    TextView text = new TextView(TimedTask.getActivity());
                    text.setText(message);
                    text.setTextSize(23);

                    Toast toast = new Toast(TimedTask.getActivity());
                    toast.setGravity(Gravity.BOTTOM, 12, 60);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(text);
                    toast.show();
                }
            });
        }
    }

    /**
     * @CreateTime 2022-01-01 21:59:32
     * @Description 确认操作对话框
     */
    public static synchronized void createContinueDialog(DialogInterface.OnClickListener listener) {
        Activity activity = TimedTask.getActivity();

        if (activity == null) {
            return;
        }

        TextView text = new TextView(activity);
        text.setText("是否继续操作???");
        text.setTextSize(19);
        text.setTextColor(ContextCompat.getColor(activity, R.color.main));

        AlertDialog dialog = new AlertDialog.Builder(activity).
                setTitle("INFO").
                setView(text).
                setCancelable(false).
                setNeutralButton("继续", listener).
                setNegativeButton("取消", null).
                create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        dialog.show();

        Show.fixDialog(dialog);
    }

    /**
     * @CreateTime 2022-01-01 21:59:41
     * @Description 固定Dialog大小
     */
    public static synchronized void fixDialog(Dialog dialog) {
        Activity activity = TimedTask.getActivity();

        if (dialog == null || activity == null) {
            return;
        }

        if (dialog.isShowing()) {
            DisplayMetrics display = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(display);

            WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
            param.width = display.widthPixels - 130;
            //param.height = display.heightPixels / 3;
            dialog.getWindow().setAttributes(param);
        }
    }

    /**
     * @CreateTime 2022-01-01 21:59:51
     * @Description 创建加载中对话框
     */
    public static synchronized Dialog createLoadDialog() {
        AlertDialog dialog = new AlertDialog.Builder(TimedTask.getActivity()).
                setCancelable(false).
                setView(View.inflate(TimedTask.getActivity(), R.layout.view_progressbar, null)).
                create();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//去除阴影
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//透明效果
        dialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);//禁止弹出虚拟按键
        dialog.show();

        DisplayMetrics display = new DisplayMetrics();
        TimedTask.getActivity().getWindowManager().getDefaultDisplay().getMetrics(display);

        WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
        param.height = display.heightPixels - 10;
        dialog.getWindow().setAttributes(param);

        return dialog;
    }

    /**
     * @CreateTime 2022-01-01 22:00:08
     * @Description 设置TextView内容
     */
    public static synchronized void setText(TextView view, String text) {
        Show.setText(new Dialog[]{}, view, text);
    }

    /**
     * @CreateTime 2022-01-01 22:00:18
     * @Description 设置TextView内容
     */
    public static synchronized void setText(Dialog dismiss, TextView view, String text) {
        Show.setText(new Dialog[]{dismiss}, view, text);
    }

    /**
     * @CreateTime 2022-01-01 22:00:28
     * @Description 设置TextView内容
     */
    public static synchronized void setText(Dialog[] dismiss, final TextView view, final String text) {
        if (view == null || text == null) {
            return;
        }

        if (dismiss != null) {
            for (Dialog dialog : dismiss) {
                dialog.dismiss();
            }
        }

        if (Looper.myLooper() == Looper.getMainLooper()) {
            view.setText(text);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    view.setText(text);
                }
            });
        }
    }


}
