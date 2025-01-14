
package com.example.fanyi232;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {
    private EditText sourceText;
    private TextView resultText;
    private Button translateButton;
    private Button clearButton;  // 新增清除按钮

    // 替换成你的有道API密钥
    private static final String APP_KEY = "5c47f5c0853055d6";
    private static final String APP_SECRET = "QLEK9amwLREigyCwysBnCunRqbGJyDFy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceText = findViewById(R.id.sourceText);
        resultText = findViewById(R.id.resultText);
        translateButton = findViewById(R.id.translateButton);
        clearButton = findViewById(R.id.clearButton);  // 初始化清除按钮
        translateButton.setOnClickListener(v -> {
            String text = sourceText.getText().toString();
            if (!TextUtils.isEmpty(text)) {
                translate(text);
            }
        });

        // 清除按钮点击事件
        clearButton.setOnClickListener(v -> {
            sourceText.setText("");  // 清除输入框
            resultText.setText("");  // 清除结果显示
            Toast.makeText(MainActivity.this, "已清除", Toast.LENGTH_SHORT).show();
        });
    }

    private void translate(String text) {
        new Thread(() -> {
            try {
                String salt = String.valueOf(System.currentTimeMillis());
                String curtime = String.valueOf(System.currentTimeMillis() / 1000);
                String sign = calculateSign(APP_KEY, text, salt, curtime, APP_SECRET);

                String url = "https://openapi.youdao.com/api";
                String param = "q=" + URLEncoder.encode(text, "UTF-8") +
                        "&from=auto" +
                        "&to=auto" +
                        "&appKey=" + APP_KEY +
                        "&salt=" + salt +
                        "&sign=" + sign +
                        "&signType=v3" +
                        "&curtime=" + curtime;

                URL realUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(param.getBytes());
                }

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // 解析JSON响应
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String translation = jsonObject.getJSONArray("translation")
                            .getString(0);

                    // 在UI线程更新结果
                    runOnUiThread(() -> resultText.setText(translation));
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MainActivity.this,
                        "翻译失败: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private String calculateSign(String appKey, String q, String salt,
                                 String curtime, String appSecret) throws Exception {
        String strSrc = appKey + truncate(q) + salt + curtime + appSecret;
        return SHA256(strSrc);
    }

    private String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }

    private String SHA256(String string) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(string.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}