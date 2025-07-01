package com.example.android

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val buttonBack = findViewById<ImageButton>(R.id.imageButton)
        buttonBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        val buttonLogin = findViewById<MaterialButton>(R.id.signInButton)
        buttonLogin.setOnClickListener {
            startActivity(Intent(this, ActivityActivity::class.java))
        }
    }
}