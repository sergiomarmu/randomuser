package com.sermarmu.randomuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sermarmu.randomuser.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityBinding
            .inflate(layoutInflater)
            .let {
                binding = it
                setContentView(it.root)
            }
    }
}