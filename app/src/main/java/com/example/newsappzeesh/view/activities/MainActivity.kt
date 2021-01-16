package com.example.newsappzeesh.view.activities

import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.airbnb.lottie.LottieAnimationView
import com.example.newsappzeesh.broadcast.MyReceiver
import com.example.newsappzeesh.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), MyReceiver.ConnectivityInterface {

    lateinit var mToolBar : Toolbar
    lateinit var navController: NavController
    lateinit var mBottomNavigationView : BottomNavigationView
    lateinit var connectionErrorText : TextView
    lateinit var lottieAnim : LottieAnimationView
//    lateinit var frameLayoutContainer: FrameLayout
    lateinit var relativeLayout : RelativeLayout
    lateinit var myReceiver: MyReceiver
    lateinit var nightModeSharedPreferences : SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    var nightModeKeyValue : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkNightMode()

        mToolBar = findViewById(R.id.toolbarID)
        mBottomNavigationView = findViewById(R.id.bottomNavBarID)
        relativeLayout = findViewById(R.id.relativeLayout)
        lottieAnim = findViewById(R.id.lottieAnimID)
        connectionErrorText = findViewById(R.id.connection_error_text_id)

        navController = Navigation.findNavController(this, R.id.nav_host_fragID)
        NavigationUI.setupWithNavController(mBottomNavigationView, navController)

        myReceiver = MyReceiver()

        setSupportActionBar(mToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.title = null

//        mBottomNavigationView.setOnNavigationItemSelectedListener(this)
//        mBottomNavigationView.selectedItemId = R.id.news_India

    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.news_India -> mFragment = India()
//            R.id.news_Science -> mFragment = Entertainment()
//            R.id.news_Tech -> mFragment = BBC()
//            R.id.news_BBC -> mFragment = Tech()
//        }
//        supportFragmentManager.beginTransaction().replace(R.id.containerID, mFragment).commit()
//        return true
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nighModeButton -> {
                if (nightModeKeyValue){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    editor.putBoolean("Night_Mode_Key", false).apply()
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    editor.putBoolean("Night_Mode_Key", true).apply()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun connectivity(isConnected : Boolean) {
        if(isConnected){
            lottieAnim.visibility = View.GONE
            connectionErrorText.visibility = View.GONE
//            frameLayoutContainer.visibility = view.VISIBLE
            relativeLayout.visibility = View.VISIBLE
        }
        else{
            relativeLayout.visibility = View.GONE
            lottieAnim.visibility = View.VISIBLE
            connectionErrorText.visibility = View.VISIBLE
//            frameLayoutContainer.visibility = view.GONE
        }
    }

    fun checkNightMode(){
        nightModeSharedPreferences = getSharedPreferences("nighModeFile", MODE_PRIVATE)
        editor = nightModeSharedPreferences.edit()
        nightModeKeyValue = nightModeSharedPreferences.getBoolean("Night_Mode_Key", false)

        if (nightModeKeyValue)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(myReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(myReceiver)
    }
}