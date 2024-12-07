package com.example.halantwittercounter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.halantwittercounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBarHome.imageView.setOnClickListener {
            finish()
        }
    }
}