package me.letiantian.slidingmenuv2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import static android.view.View.*;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private static String LOG_TAG = "smv2";
    private static int STEP = 27;

    private LinearLayout menu;
    private LinearLayout content;
    private LinearLayout.LayoutParams menuParams;
    private LinearLayout.LayoutParams contentParams;

    private Toolbar myToolbar;

    // menu完全显示时，留给content的宽度值。
    private static final int menuPadding = 80;

    // 分辨率
    private int disPlayWidth;

    private boolean mIsShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        // 设置toolbar
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.menu);


        disPlayWidth = getWindowManager().getDefaultDisplay().getWidth();

        menu = (LinearLayout) findViewById(R.id.menu);
        content = (LinearLayout) findViewById(R.id.content);
        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
        contentParams = (LinearLayout.LayoutParams) content.getLayoutParams();

        content.setOnClickListener(this);

        contentParams.width = disPlayWidth;
        menuParams.width = disPlayWidth - menuPadding;
        menuParams.leftMargin = 0-menuParams.width;
        mIsShow = false;

    }

    @Override
    public void onClick(View v)
    {
        Log.d(LOG_TAG, "onclick");
        if (v.getId() == R.id.content)
        {
            Log.d(LOG_TAG, "content onclick");
            if (mIsShow) {  // 滑动菜单已经显示
                Log.d(LOG_TAG, "关闭菜单");
                new showMenuAsyncTask().execute(-STEP);
                mIsShow = false;
            } else {        // 滑动菜单未显示
                Log.d(LOG_TAG, "打开菜单");
                new showMenuAsyncTask().execute(STEP);
                mIsShow = true;
            }

        }

    }


    /**
     * 通过修改leftMargin达到滑动菜单的效果
     */

    class showMenuAsyncTask extends AsyncTask<Integer, Integer, Integer>
    {

        @Override
        protected Integer doInBackground(Integer... params)
        {
            int leftMargin = menuParams.leftMargin;

            /**
             * 注意：leftMargin不一定能整除STEP
             */

            while (true)
            {

                if (params[0] > 0 && leftMargin >= 0)
                {
                    break;
                } else if (params[0] < 0 && leftMargin <= -menuParams.width)
                {
                    break;
                }

                leftMargin += params[0];  // params[0]是步长

                if (params[0] > 0 && leftMargin >= 0)
                {
                    leftMargin = 0;
                    break;
                } else if (params[0] < 0 && leftMargin <= -menuParams.width)
                {
                    leftMargin = -menuParams.width;
                    break;
                }

                publishProgress(leftMargin);  // 触发onProgressUpdate

                try
                {
                    Thread.sleep(10);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            menuParams.leftMargin = values[0];
            menu.setLayoutParams(menuParams);
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);
            menuParams.leftMargin = result;
            menu.setLayoutParams(menuParams);
        }

    }
}
