package work.yeshu.codelibrary;

import android.app.Activity;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.awt.font.TextAttribute;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 1.测试Activity结束后，各种成员变量的生命周期
 * 结论：
 * 1.Activity结束后，Application会不会立刻结束
 * 2.Activity结束后，其线程会继续执行
 */
public class ObjectLiftCycleActivity extends Activity {
    private final static String TAG = ObjectLiftCycleActivity.class.getSimpleName();

    private TextView mTextViewOne;
    private TextView mTextViewTwo;
    private ArrayList<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_lift_cycle);

        mTextViewOne = (TextView) findViewById(R.id.textview1);
        mTextViewTwo = (TextView) findViewById(R.id.textview2);

        mData = new ArrayList();
        mData.add("1111111");
        mData.add("2222222");
        mData.add("3333333");


        mTextViewOne.setText("测试当Activity结束时，线程回调使用Activity中成员变量");
        mTextViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ThreadA().start();
            }
        });

        mTextViewTwo.setText("测试包含线程的内部类中，使用WeakReference来操作Activity成员变量");
        mTextViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ThreadB(ObjectLiftCycleActivity.this).start();
            }
        });
    }

    private class ThreadA extends Thread {
        @Override
        public void run() {
            //模拟耗时操作
            ObjectLiftCycleActivity.this.finish();
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //静态内部类，含有父类的引用，这里可以继续使用Activity的成员变量,但会造成内存泄露
            Log.i(TAG, "ThreadA get mData, data size:" + mData.size());
        }
    }

    private static class ThreadB extends Thread {
        //WeadReference回收不是固定的，看Gcc
        private WeakReference<ObjectLiftCycleActivity> mActvityReference;

        public ThreadB(ObjectLiftCycleActivity activty) {
            mActvityReference = new WeakReference<ObjectLiftCycleActivity>(activty);
        }

        @Override
        public void run() {
            mActvityReference.get().finish();

            //模拟耗时操作
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (null != mActvityReference.get()) {
                Log.i(TAG, "ThreadB get mData, data size:" + mActvityReference.get().mData.size());
            } else {
                Log.i(TAG, "activity is null");
            }
        }
    }
}
