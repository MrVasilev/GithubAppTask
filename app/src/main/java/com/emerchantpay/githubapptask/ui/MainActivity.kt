package com.emerchantpay.githubapptask.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.emerchantpay.githubapptask.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
