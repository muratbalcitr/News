package com.m2lifeapps.challenge.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.m2lifeapps.challenge.R
import com.m2lifeapps.challenge.core.platform.BaseActivity
import com.m2lifeapps.challenge.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewModel>(
        layoutId = R.layout.activity_main,
        viewModelClass = MainViewModel::class.java
    ) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDataBinding() {
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
