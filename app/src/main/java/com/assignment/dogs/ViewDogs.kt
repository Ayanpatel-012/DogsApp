package com.assignment.dogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.dogs.databinding.ActivityViewDogsBinding

class ViewDogs : AppCompatActivity() {
    private lateinit var binding: ActivityViewDogsBinding
    private var adaptor: DogsAdaptor? = null
    private var dogsList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewDogsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SetupRecyclerView()
        SetupToolbar()
        SetupListeners()
    }

    private fun SetupListeners() {
        binding.clrBtn.setOnClickListener {
            LRUCache.clearCache()
            dogsList.clear()
            dogsList.addAll(LRUCache.get())
            adaptor?.notifyDataSetChanged()

        }
    }

    private fun SetupToolbar() {
        setSupportActionBar(binding.toolbarvd)
        supportActionBar?.title = ""
        binding.backbtnvd.setOnClickListener {
            onBackPressed()
        }
    }

    private fun SetupRecyclerView() {
        dogsList.addAll(LRUCache.get())
        adaptor = DogsAdaptor(dogsList)
        binding.rv.adapter = adaptor
        binding.rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}