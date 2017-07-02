package me.letiantian.slidingmenuv1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity implements OnTouchListener{

    private LinearLayout menu;
    private LinearLayout content;
    private LinearLayout.LayoutParams menuParams;
    private LinearLayout.LayoutParams contentParams;

    private Toolbar myToolbar;

    // menu完全显示时，留给content的宽度值。
    private static final int menuPadding = 80;

    // 分辨率
    private int disPlayWidth;

    private float xDown;
    private float xMove;

    private boolean mIsShow = false;
    private static final int speed = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // 隐藏标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();


        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.menu);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this.getApplicationContext(), "clicked!",  Toast.LENGTH_SHORT).show();
                showMenu(!mIsShow);
            }
        });

        disPlayWidth = getWindowManager().getDefaultDisplay().getWidth();

        menu = (LinearLayout) findViewById(R.id.menu);
        content = (LinearLayout) findViewById(R.id.content);
        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
        contentParams = (LinearLayout.LayoutParams) content.getLayoutParams();
        findViewById(R.id.layout).setOnTouchListener(this);

        menuParams.width = disPlayWidth - menuPadding;
        contentParams.width = disPlayWidth;
        showMenu(mIsShow);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                showMenu(!mIsShow);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    /**
     * 通过修改leftMargin达到滑动菜单的效果
     * @param isShow
     */
    private void showMenu(boolean isShow)
    {
        if (isShow)
        {
            mIsShow = true;
            menuParams.leftMargin = 0;
        } else
        {
            mIsShow = false;
            menuParams.leftMargin = 0 - menuParams.width;
        }
        menu.setLayoutParams(menuParams);
    }
}
