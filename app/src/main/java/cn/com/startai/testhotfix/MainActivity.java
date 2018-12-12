package cn.com.startai.testhotfix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;

import cn.com.startai.testhotfix.utils.TAndL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = findViewById(R.id.bt_test);
        final TextView tv = findViewById(R.id.tv);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                int a = 5 / 0;
                tv.setText("bug 已经修复");
                TAndL.TL(getApplicationContext(), "bug 已经修复");

            }
        });


    }

}
