package wangdong.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

/**
 * Created by wangdong on 2/9/17.
 */

public class SimulationHandler extends Handler {

    public int width = 0;
    public int height = 0;

    public SimulationHandler(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // 屏幕宽度（像素）
        height = metric.heightPixels;   // 屏幕高度（像素）
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
//        int what = msg.what;
//
//        switch (what) {
//            case MSG_PLAY_STOPPED:
//                PlayerFeedback fb = (PlayerFeedback) msg.obj;
//                fb.playbackStopped();
//                break;
//            default:
//                throw new RuntimeException("Unknown msg " + what);
//        }

        Instrumentation mInst = new Instrumentation();
        mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, height / 2, 0)); //x,y 即是事件的坐标
        mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, width, height / 2, 0));

    }


}
