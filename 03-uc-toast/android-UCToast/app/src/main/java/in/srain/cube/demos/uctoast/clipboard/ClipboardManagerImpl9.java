package in.srain.cube.demos.uctoast.clipboard;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.Log;

public class ClipboardManagerImpl9 extends ClipboardManagerCompat implements Runnable {

    /**
     * It's better to check clipboard data for a static thread
     */
    private static Handler sHandler;

    static {
        sHandler = new Handler(Looper.getMainLooper());
    }

    ClipboardManager mClipboardManager;
    private CharSequence mLastData;
    private boolean mWorking = false;

    public ClipboardManagerImpl9(Context context) {
        mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    /**
     * 添加监听器时也启动了监听
     * @param listener
     */
    @Override
    public void addPrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        super.addPrimaryClipChangedListener(listener);
        synchronized (mPrimaryClipChangedListeners) {
            if (mPrimaryClipChangedListeners.size() == 1) {
                startListen();  // 启动监听（开始监听）
            }
        }
    }

    private void startListen() {
        mWorking = true;
        sHandler.postDelayed(this, 10000); // 这个有问题，应该是1000，毕竟单位是ms
    }

    private void stopListen() {
        mWorking = false;
        sHandler.removeCallbacks(this);
    }

    @Override
    public void removePrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        super.removePrimaryClipChangedListener(listener);
        synchronized (mPrimaryClipChangedListeners) {
            if (mPrimaryClipChangedListeners.size() == 0) {
                stopListen();
            }
        }
    }

    @Override
    public CharSequence getText() {
        return mClipboardManager.getText();
    }

    /**
     * 运行在主线程中
     */
    @Override
    public void run() {
        if (mWorking) {
            CharSequence data = getText();
            Log.d("uc-toast", "run: " + data);
            check(data);
            sHandler.postDelayed(this, 1000);
        }
    }

    private void check(CharSequence data) {
        if (TextUtils.isEmpty(mLastData) && TextUtils.isEmpty(data)) {
            return;
        }

        if (!TextUtils.isEmpty(mLastData) && mLastData.equals(data)) {
            return;
        }
        mLastData = data;
        notifyPrimaryClipChanged();
    }
}
