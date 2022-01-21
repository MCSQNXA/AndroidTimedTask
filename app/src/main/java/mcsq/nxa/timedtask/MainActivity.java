package mcsq.nxa.timedtask;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @Author MCSQNXA
 * @CreateTime 2022-01-21 21:11:08
 * @Description
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.main_activity);

        super.findViewById(R.id.main_activity_open_wuzhangai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                MainActivity.super.startActivity(intent);//跳转到无障碍
            }
        });
    }


}
