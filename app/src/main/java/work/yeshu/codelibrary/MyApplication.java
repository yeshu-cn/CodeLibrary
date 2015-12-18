package work.yeshu.codelibrary;

import android.app.Application;
import android.util.Log;

/**
 * Created by yeshu on 15/12/18.
 */
public class MyApplication extends Application{
    private static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate called");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG, "onLowMemory called");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i(TAG, "onTrimMemory called");
    }


}
