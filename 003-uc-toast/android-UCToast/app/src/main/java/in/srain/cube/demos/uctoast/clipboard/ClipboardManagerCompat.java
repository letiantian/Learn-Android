package in.srain.cube.demos.uctoast.clipboard;

import android.content.Context;
import android.os.Build;

import java.util.ArrayList;

public abstract class ClipboardManagerCompat {

    // 剪贴板监听器列表，可能有多个。监听器是自定义的内部类
    protected final ArrayList<OnPrimaryClipChangedListener> mPrimaryClipChangedListeners
            = new ArrayList<OnPrimaryClipChangedListener>();

    public static ClipboardManagerCompat create(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return new ClipboardManagerImpl11(context);
        } else {
            return new ClipboardManagerImpl9(context);
        }
    }

    // 添加监听器
    public void addPrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        synchronized (mPrimaryClipChangedListeners) {
            mPrimaryClipChangedListeners.add(listener);
        }
    }

    // 通知监听器做出动作
    protected final void notifyPrimaryClipChanged() {
        synchronized (mPrimaryClipChangedListeners) {
            for (int i = 0; i < mPrimaryClipChangedListeners.size(); i++) {
                mPrimaryClipChangedListeners.get(i).onPrimaryClipChanged();
            }
        }
    }

    // 删除某个监听器
    public void removePrimaryClipChangedListener(OnPrimaryClipChangedListener listener) {
        synchronized (mPrimaryClipChangedListeners) {
            mPrimaryClipChangedListeners.remove(listener);
        }
    }

    public abstract CharSequence getText();


    // 监听器，在 API level 11 中开始存在该API，见 http://developer.android.com/intl/zh-cn/reference/android/content/ClipboardManager.OnPrimaryClipChangedListener.html
    public interface OnPrimaryClipChangedListener {
        void onPrimaryClipChanged();
    }
}
