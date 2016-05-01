package me.letiantian.memorycleanerv4;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button usageBtn, cleanBtn;
    private TextView infoTv;

    private static String LOG_TAG = "log_cleaner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(LOG_TAG, "onCreate...");

        usageBtn = (Button) findViewById(R.id.usage_btn);
        cleanBtn = (Button) findViewById(R.id.clean_btn);
        infoTv = (TextView) findViewById(R.id.info_tv);

        usageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "" + Build.VERSION_CODES.JELLY_BEAN);  // 16
                infoTv.setText(getCurrentMeminfo());
            }
        });

        cleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoTv.setText("清理中...");
                clearMem();
                infoTv.setText("清理完成\n" + getCurrentMeminfo());
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String infoTvContent = savedInstanceState.getString("infotv");
        infoTv.setText(infoTvContent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("infotv", infoTv.getText().toString());
    }

    /**
     * 获取内存信息
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private String getCurrentMeminfo() {
        StringBuffer sb = new StringBuffer();
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        sb.append("剩余内存："+(mi.availMem/1024/1024)+"MB\n");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            sb.append("总内存： " + (mi.totalMem/1024/1024) + "MB\n");
        }
        sb.append("内存是否过低：" + mi.lowMemory);
        return sb.toString();
    }

    /**
     * 清理内存
     */
    private void clearMem() {
        ActivityManager activityManger = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appList = activityManger.getRunningAppProcesses();
        if (appList != null) {
            for (int i = 0; i < appList.size(); i++) {
                ActivityManager.RunningAppProcessInfo appInfo = appList.get(i);

                Log.v(LOG_TAG, "pid: " + appInfo.pid);
                Log.v(LOG_TAG, "processName: " + appInfo.processName);
                Log.v(LOG_TAG, "importance: " + appInfo.importance);

                String[] pkgList = appInfo.pkgList;
                if (appInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    for (int j = 0; j < pkgList.length; j++) {
                        activityManger.killBackgroundProcesses(pkgList[j]);
                    }
                }
            }
        }
    }

}