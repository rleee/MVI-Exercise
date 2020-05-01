package com.drdlee.mviexercise_livedata.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.drdlee.mviexercise_livedata.R
import com.drdlee.mviexercise_livedata.databinding.ActivityMainBinding
import com.drdlee.mviexercise_livedata.util.DataState

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
            if (it.loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

            if (it.message != null) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
