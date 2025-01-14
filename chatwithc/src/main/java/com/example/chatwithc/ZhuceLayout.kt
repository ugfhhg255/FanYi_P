package com.example.chatwithc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ZhuceLayout : AppCompatActivity() {


    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_zhuce_layout)

        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (validateInputs(username, email, password)) {
                // 调用注册逻辑
                registerUser(username, email, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }




    private fun validateInputs(username: String, email: String, password: String): Boolean {
        return username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }

    private fun registerUser(username: String, email: String, password: String) {
        // 模拟注册逻辑（替换为实际的后端 API 调用）
        Toast.makeText(this, "Registering user...", Toast.LENGTH_SHORT).show()

        // 假设注册成功，跳转到主页面
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // 关闭当前 Activity，避免返回栈堆积
    }
}