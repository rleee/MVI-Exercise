package com.drdlee.mviexercise_livedata.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.drdlee.mviexercise_livedata.R
import com.drdlee.mviexercise_livedata.databinding.ActivityMainBinding
import com.drdlee.mviexercise_livedata.util.DataState
import com.drdlee.mviexercise_livedata.util.Event

class MainActivity : AppCompatActivity(), DataStateListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpFragment()
    }

    private fun setUpFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment())
            .commitNow()
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        dataState?.let {

            // set loading progressbar on or off
            if (it.loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

            // set Toast message if new message appear
            it.message?.let { event: Event<String> ->

                // if configuration change wont assign the same value to viewState
                event.getContentIfNotHandled()?.let { message: String ->
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
