package com.example.newsappzeesh.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("Intro_Screen", MODE_PRIVATE)
        val isIntroDone = sharedPreferences.getBoolean("isIntroDone", false)

        Log.d("splash0", "onCreate: chali")

        Log.d("splash1", "onCreate: $isIntroDone")

        if (isIntroDone) {
            Log.d("splash2", "intent if $isIntroDone")
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
        } else {
            Log.d("splash3", "intent else: $isIntroDone")
            startActivity(Intent(this@SplashScreen, IntroActivity::class.java))
            finish()
        }
    }
}