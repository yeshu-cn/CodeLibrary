package work.yeshu.codelibrary;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class JavaTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_test);

        test();
    }

    //不会崩溃，打印出haha:null
    private void test(){
        String test = null;
        System.out.println("haha:" + test);
    }

}
