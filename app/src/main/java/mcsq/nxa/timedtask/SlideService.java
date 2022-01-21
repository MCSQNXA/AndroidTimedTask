package mcsq.nxa.timedtask;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.view.Gravity;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

/**
 * @Author MCSQNXA
 * @CreateTime 2021-12-25 09:52:38
 * @Description 模拟手势服务
 */
public class SlideService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
//        try {
//            AccessibilityNodeInfo rootInfo = getRootInActiveWindow();
//
//            if (rootInfo == null || rootInfo.getChildCount() == 0) {
//                return;
//            }
//
//            if (TextUtils.isEmpty(rootInfo.getClassName())) {
//                return;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onServiceConnected() {
        this.showToast("模拟手势启动");

        new Thread(new Runnable() {//屏幕滑动
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000 * 15);

                        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
                            Path path = new Path();
                            path.moveTo(500, 999);//滑动起点
                            path.lineTo(500, 500);//滑动终点
                            GestureDescription.Builder builder = new GestureDescription.Builder();
                            GestureDescription description = builder.addStroke(new GestureDescription.StrokeDescription(path, 1, 1)).build();

                            SlideService.super.dispatchGesture(description, new AccessibilityService.GestureResultCallback() {
                                @Override
                                public void onCompleted(GestureDescription gestureDescription) {
                                    super.onCompleted(gestureDescription);
                                }

                                @Override
                                public void onCancelled(GestureDescription gestureDescription) {
                                    super.onCancelled(gestureDescription);
                                }
                            }, null);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onInterrupt() {
        this.showToast("模拟手势中断");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.showToast("模拟手势停止");
    }

    /**
     * @CreateTime 2022-01-21 21:28:27
     * @Description
     */
    public synchronized void showToast(String message) {
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.TOP, 12, 60);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }


}
