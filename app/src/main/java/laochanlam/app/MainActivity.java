package laochanlam.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Intent globalService;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Startbutton = (Button) findViewById(R.id.Startbutton);


        if (Build.VERSION.SDK_INT >= 23) {  // Version
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);

            } else {
                globalService = new Intent(this, GlobalTouchService.class);
            }
        }else {
            globalService = new Intent(this,GlobalTouchService.class);
        }
    }

    public void StartbuttonClicked(View v){
        if (v.getTag() == null){
            Log.i(TAG, "Start");
            startService(globalService);
            v.setTag("on");
            Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
        }
        else if (v.getTag() == "on"){
            Log.i(TAG, "Stop");
            stopService(globalService);
            v.setTag(null);
            Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
        }
    }


}
