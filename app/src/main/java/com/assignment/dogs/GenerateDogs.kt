package com.assignment.dogs

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.assignment.dogs.databinding.ActivityGenerateDogsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GenerateDogs : AppCompatActivity() {
    private lateinit var binding: ActivityGenerateDogsBinding
    private var jsonObjectRequest: JsonObjectRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerateDogsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SetupToolbar()
        SetupListeners()
    }

    private fun SetupListeners() {
        binding.generateDogs.setOnClickListener {
            generateNewRandomDog()
        }
    }

    override fun onPause() {
        super.onPause()
        jsonObjectRequest?.cancel()
    }

    private fun hideProgress() {
        binding.generateDogs.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgress() {
        binding.generateDogs.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun SetupToolbar() {
        setSupportActionBar(binding.toolbargd)
        supportActionBar?.title = ""
        binding.backbtngd.setOnClickListener {
            onBackPressed()
        }
    }

    private fun generateNewRandomDog() {
        showProgress()
        val url = "https://dog.ceo/api/breeds/image/random"
        val queue = Volley.newRequestQueue(this)
        jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val url = response.get("message")
                LRUCache.put(url.toString())
                Glide.with(this).load(url).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        hideProgress()
                        showToast("failed to load image")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        hideProgress()
                        return false
                    }

                }).into(binding.dogImage)


            },
            {
                showToast("error generating new dog image")
                hideProgress()
            }
        )
        queue.add(jsonObjectRequest)
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}