package laochanlam.app;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.Objects;


public class GlobalTouchService extends Service implements View.OnTouchListener{

    private String TAG = this.getClass().getSimpleName();

    private WindowManager mWindowManager;

    private LinearLayout touchLayout;

    private long TimeCounter = 0;
    private long PrevTime = 0;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();


        /**********************************Fake View****************************/
        touchLayout = new LinearLayout(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams( 1,1);
        touchLayout.setLayoutParams(lp);
        touchLayout.setBackgroundColor(Color.GREEN);
        touchLayout.setOnTouchListener(this);

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                1,
                1,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSPARENT);

        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        Log.i(TAG, "add View");

        mWindowManager.addView(touchLayout , mParams);
        /**********************************Fake View****************************/


    }




    @Override
    public void onDestroy(){
        if (mWindowManager != null){
            if (touchLayout != null) mWindowManager.removeView(touchLayout);
        }
        super.onDestroy();
    }


    boolean ShowAlert_5sec = false;
    boolean ShowAlert_10sec = false;
    boolean ShowAlert_30sec = false;


    @Override
    public boolean onTouch(View v , MotionEvent event){
        long CurrentTime = SystemClock.elapsedRealtime()/1000;

        if (TimeCounter > 5 && !ShowAlert_5sec)
        {
            ShowAlert_5sec = true;
            /**********************************Notification****************************/
//            final int notifyID = 1;
//            final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                final Notification notification = new NotificationCompat.Builder(this)
//                                                                        .setSmallIcon(R.drawable.ic_launcher)
//                                                                        .setContentTitle("溫馨提示")
//                                                                        .setContentText("您已滑手機超過5秒囉")
//                                                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
//                                                                        .setAutoCancel(true)
//                                                                        .build();
//            notificationManager.notify(notifyID, notification);
            /**********************************Notification****************************/


            /**********************************AlertDialog****************************/
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setPositiveButton("關閉",null);
            builder.setIcon(R.drawable.ic_launcher);
            builder.setMessage("您已經滑動手機5秒。");
            AlertDialog AlertDialog = builder.create();
            AlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            AlertDialog.show();
            /**********************************AlertDialog****************************/
        }

        if (TimeCounter > 10 && !ShowAlert_10sec)
        {
            ShowAlert_10sec = true;

            /**********************************AlertDialog****************************/
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setPositiveButton("關閉",null);
            builder.setIcon(R.drawable.ic_launcher);
            builder.setMessage("您已經滑動手機10秒。");
            AlertDialog AlertDialog = builder.create();
            AlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            AlertDialog.show();
            /**********************************AlertDialog****************************/
        }

        if (TimeCounter > 30 && !ShowAlert_30sec)
        {
            ShowAlert_30sec = true;

            /**********************************AlertDialog****************************/
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setPositiveButton("關閉",null);
            builder.setIcon(R.drawable.ic_launcher);
            builder.setMessage("您已經滑動手機30秒。");
            AlertDialog AlertDialog = builder.create();
            AlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            AlertDialog.show();
            /**********************************AlertDialog****************************/
        }



        if (CurrentTime - PrevTime < 30)  // Count in 30sec.
        {
            TimeCounter = TimeCounter + (CurrentTime - PrevTime);
            PrevTime = CurrentTime;
        }
        else
        {
            PrevTime = CurrentTime;
        }

        Log.d(TAG, "Touch event: " + event.toString());
       if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
           Log.i(TAG,  Objects.toString(TimeCounter, null));
           Log.i(TAG, "Action" + event.getAction() + "\t X:" + event.getRawX() + "\t Y:" + event.getRawY());
       }
        return false;
    }




}
