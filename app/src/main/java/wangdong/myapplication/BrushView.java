package wangdong.myapplication;

import android.app.Instrumentation;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class BrushView extends View {
    private Paint brush = new Paint();
    private Path path = new Path();
    public Button btnEraseAll;
    public LayoutParams params;

    public BrushView(final Context context) {
        super(context);
        brush.setAntiAlias(true);
        brush.setColor(Color.BLUE);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(15f);

        btnEraseAll = new Button(context);
        btnEraseAll.setText("Erase Everything!!");
        params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        btnEraseAll.setLayoutParams(params);

        final SimulationHandler handler = new SimulationHandler(context);

        final Thread thread = new Thread(new Runnable() {
            public void run() {

                System.out.println("adsfsdafsf");

                Instrumentation mInst = new Instrumentation();

                mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, handler.width, handler.height / 2, 0));

                int currentX = 0;

                mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, handler.height / 2, 0)); //x,y 即是事件的坐标

                while (true) {

                    currentX += 1;
                    mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE, currentX, handler.height / 2, 0));
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (currentX >= handler.width) {
                        mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                                SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, handler.width, handler.height / 2, 0));
                        break;
                    }
                    // 耗时的方法
                    //handler.sendEmptyMessage(1);
                    // 执行耗时的方法之后发送消给handler
                }
            }
        });

        btnEraseAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // reset the path
                path.reset();
                // invalidate the view
                postInvalidate();



//                //handler.sendMessage(handler.obtainMessage());
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Instrumentation mInst = new Instrumentation();
//                        mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
//                                SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, handler.height / 2, 0)); //x,y 即是事件的坐标
//                        mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
//                                SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, handler.width, handler.height / 2, 0));
//                    }
//                });

                thread.start();

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        // Checks for the event that occurs
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pointY);

                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pointY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }
        // Force a view to draw.
        postInvalidate();
        return false;

    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, brush);
    }
}
