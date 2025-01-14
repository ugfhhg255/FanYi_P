package com.example.anping2wew;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private boolean isScreenDark = false;
    private WindowManager.LayoutParams layoutParams;
    private float originalBrightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        // 获取当前窗口对象
        layoutParams = getWindow().getAttributes();
        // 保存原始亮度
        originalBrightness = layoutParams.screenBrightness;

        Button darkButton = findViewById(R.id.darkButton);
        darkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleScreenBrightness();
            }
        });
    }

    private void toggleScreenBrightness() {
        Log.d("MainActivity", "Button clicked, isScreenDark: " + isScreenDark);
        if (isScreenDark) {
            layoutParams.screenBrightness = originalBrightness;
        } else {
            layoutParams.screenBrightness = 0.01f;
        }
        getWindow().setAttributes(layoutParams);
        isScreenDark = !isScreenDark;
        Log.d("MainActivity", "Screen brightness changed, isScreenDark: " + isScreenDark);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }


}


