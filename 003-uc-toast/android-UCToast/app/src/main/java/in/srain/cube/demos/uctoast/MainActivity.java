package in.srain.cube.demos.uctoast;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.*;
import android.widget.TextView;

public final class MainActivity extends AppCompatActivity {

    private final static String KEY_CONTENT = "content";
    private TextView mTextView;

    public static void startForContent(Context context, String content) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(KEY_CONTENT, content);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.text_view);


        /**
         * 复制文本后，会有悬浮窗显示复制的内容，点击悬浮窗后会重新打开该activity，并在textview中显示内容
         */
        Intent intent = getIntent();
        Utils.printIntent("MainActivity::onCreate()", intent);
        tryToShowContent(intent);

        ListenClipboardService.start(this);  // 启动service
    }

    /**
     * 这个应该是当前activity在启动中，且没有被停止（比如在最前台），接受intent时触发
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Utils.printIntent("MainActivity::onNewIntent()", intent);

        tryToShowContent(intent);
    }

    /**
     * 在textView中显示复制的内容
     * @param intent
     */
    private void tryToShowContent(Intent intent) {
        String content = intent.getStringExtra(KEY_CONTENT);
        if (!TextUtils.isEmpty(content)) {
            mTextView.setText(content);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // 打开网页
        if (id == R.id.action_about) {
            String url = "https://github.com/liaohuqiu/android-UCToast";
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}