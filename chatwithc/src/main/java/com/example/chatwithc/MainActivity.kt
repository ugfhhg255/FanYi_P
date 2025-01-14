package com.example.chatwithc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var dengluButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // 找到注册按钮
        val btnRegister: Button = findViewById(R.id.zhuce)
        // 设置点击事件
        btnRegister.setOnClickListener {
            // 跳转到注册页面
            val intent = Intent(this, ZhuceLayout::class.java)
            startActivity(intent)
        }

        dengluButton = findViewById(R.id.denglu)

        // 使用 lambda 表达式作为点击监听器，避免额外的类创建
        dengluButton.setOnClickListener {
            val intent = Intent(this, denglu::class.java)
            startActivity(intent)
        }

    }
}