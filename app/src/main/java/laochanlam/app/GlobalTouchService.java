package laochanlam.app;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;



public class GlobalTouchService extends Service implements View.OnTouchListener{

    private String TAG = this.getClass().getSimpleName();

    private WindowManager mWindowManager;

    private LinearLayout touchLayout;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        touchLayout = new LinearLayout(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams( 1,1);
        touchLayout.setLayoutParams(lp);

        touchLayout.setBackgroundColor(Color.GREEN);
        touchLayout.setOnTouchListener(this);

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);


        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                50,
                50,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSPARENT);

        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        Log.i(TAG, "add View");


        mWindowManager.addView(touchLayout , mParams);

    }


    @Override
    public void onDestroy(){
        if (mWindowManager != null){
            if (touchLayout != null) mWindowManager.removeView(touchLayout);
        }
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v , MotionEvent event){



        Log.d(TAG, "Touch event: " + event.toString());
       if(event.getAction() == MotionEvent.ACTION_OUTSIDE)//|| event.getAction()==MotionEvent.ACTION_UP
           Log.i(TAG,"Action" + event.getAction() + "\t X:" + event.getRawX() + "\t Y:" + event.getRawY());


        return false;
    }




}
