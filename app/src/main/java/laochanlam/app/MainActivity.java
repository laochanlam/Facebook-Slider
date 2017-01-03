package laochanlam.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Intent globalService;
    public static Handler handler;
    RelativeLayout relativeLayout;
    private String TAG = this.getClass().getSimpleName();
    RelativeLayout.LayoutParams ViewLayoutParams;
    boolean ServiceFlag = false;
    Stack ViewItem = new Stack();
    private Button startButton;
    private TextView textMsg;


    TextView view_for_5sec,view_for_10sec,view_for_30sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button) findViewById(R.id.Startbutton);

        handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //textMsg.append(msg);
            switch (msg.what) {
                case 1:
                    relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
                    view_for_5sec = new TextView(MainActivity.this);
                    view_for_5sec.setId(1);
                    view_for_5sec.setText("你已經滑動了五秒!!!");
                    ViewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    ViewLayoutParams.addRule(RelativeLayout.BELOW, R.id.Startbutton);
                    ViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.Startbutton);

                    view_for_5sec.setLayoutParams(ViewLayoutParams);
                    relativeLayout.addView(view_for_5sec);
                    ViewItem.push(view_for_5sec);
                    setContentView(relativeLayout);
                    break;
                case 2:
                    relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
                    view_for_10sec = new TextView(MainActivity.this);
                    view_for_10sec.setId(2);
                    view_for_10sec.setText("你已經滑動了十秒!!!");
                    ViewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    ViewLayoutParams.addRule(RelativeLayout.BELOW, 1);
                    ViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,1);

                    view_for_10sec.setLayoutParams(ViewLayoutParams);
                    relativeLayout.addView(view_for_10sec);
                    ViewItem.push(view_for_10sec);
                    setContentView(relativeLayout);
                    break;
                case 3:
                    relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
                    view_for_30sec = new TextView(MainActivity.this);
                    view_for_30sec.setId(3);
                    view_for_30sec.setText("你已經滑動了三十秒!!!");
                    ViewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    ViewLayoutParams.addRule(RelativeLayout.BELOW, 2);
                    ViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,2);

                    view_for_30sec.setLayoutParams(ViewLayoutParams);
                    relativeLayout.addView(view_for_30sec);
                    ViewItem.push(view_for_30sec);
                    setContentView(relativeLayout);
                    break;
            }

        }
    };

        if (Build.VERSION.SDK_INT >= 23)
        {  // Version
            if (!Settings.canDrawOverlays(getApplicationContext()))
            {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
            }
            else
                globalService = new Intent(this, GlobalTouchService.class);
        }
        else
            globalService = new Intent(this,GlobalTouchService.class);
    }

    public void StartbuttonClicked(View v){
        ServiceFlag = !ServiceFlag;
        if (ServiceFlag) {
            Log.i(TAG, "Start");
            startService(globalService);
            Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
            startButton.setText("Stop");
        } else {
            Log.i(TAG, "Stop");
            stopService(globalService);
            Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
            startButton.setText("Start");
        }
    }

    public void ResetButtonClicked(View v){
            if (ServiceFlag) {
                ServiceFlag = false;
                while (!ViewItem.empty())
                {
                    relativeLayout.removeView((View)ViewItem.peek());
                    ViewItem.pop();
                }
                Log.i(TAG, "Stop");
                stopService(globalService);
                Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
                startButton.setText("Start");
        }
    }
}
