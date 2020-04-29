package com.drdlee.mviexercise_livedata.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.drdlee.mviexercise_livedata.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpFragment()
    }

    private fun setUpFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment())
            .commitNow()
    }
}
