package com.assignment.dogs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.assignment.dogs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SetupCache()
        SetupListeners()
    }

    private fun SetupListeners() {
        binding.generateDogs.setOnClickListener {
            val intent = Intent(this, GenerateDogs::class.java)
            startActivity(intent)
        }
        binding.viewDogs.setOnClickListener {
            val intent = Intent(this, ViewDogs::class.java)
            startActivity(intent)
        }
    }

    private fun SetupCache() {
        SharedPrefManager.initialize(this)
        LRUCache.initialize()
    }
}