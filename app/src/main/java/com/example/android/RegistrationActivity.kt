package com.example.android

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        val buttonBack = findViewById<ImageButton>(R.id.imageButton)
        buttonBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        val buttonRegistration = findViewById<MaterialButton>(R.id.registerButton)
        buttonRegistration.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
