package com.example.newsappzeesh.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.newsappzeesh.R
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment

class IntroActivity : AppIntro() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("Intro_Screen", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        showStatusBar(true)

        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.app_intro_screen_first))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.app_intro_screen_second))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.app_intro_screen_third))

        setIndicatorColor(
            selectedIndicatorColor = ContextCompat.getColor(this, R.color.titleColor),
            unselectedIndicatorColor = ContextCompat.getColor(this, R.color.grey_color)
        )

        setNextArrowColor(ContextCompat.getColor(this, R.color.titleColor))
        setColorDoneText(ContextCompat.getColor(this, R.color.titleColor))
        setColorSkipButton(ContextCompat.getColor(this, R.color.titleColor))
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        editor.putBoolean("isIntroDone", true).apply()

        startActivity(Intent(this@IntroActivity, MainActivity::class.java))
        finish()
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        editor.putBoolean("isIntroDone", true).apply()

        startActivity(Intent(this@IntroActivity, MainActivity::class.java))
        finish()
    }
}