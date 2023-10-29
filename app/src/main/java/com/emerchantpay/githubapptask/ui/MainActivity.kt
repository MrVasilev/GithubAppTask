package com.emerchantpay.githubapptask.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.emerchantpay.githubapptask.R
import com.emerchantpay.githubapptask.databinding.ActivityMainBinding
import com.emerchantpay.githubapptask.ui.user.profile.UserProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserProfileFragment.newInstance())
                .commitNow()
        }
    }

    companion object {
        fun start(from: Context) = from.startActivity(Intent(from, MainActivity::class.java))
    }
}