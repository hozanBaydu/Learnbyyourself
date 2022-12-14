package com.hozanbaydu.learnbyyourself.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hozanbaydu.learnbyyourself.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var fragmentFactory: FragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory=fragmentFactory
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
    }
}