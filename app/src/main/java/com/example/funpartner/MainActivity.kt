package com.example.funpartner

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }

    fun loadMeme(){
        // Initialize/Create the reqQueue
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"

        // Send JSON req
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url, null,
            { response ->
                // Get the response
                val url = response.getString("url")
                Log.d(TAG, "loadMeme: url = $url")
                val memeView = findViewById<ImageView>(R.id.memeImageView)
                Glide.with(this).load(url).into(memeView)
            },
            { error ->
                // Get the error
                Log.d(TAG, "loadMeme: ${error.message}")
            }
        )

        // Execute the req from the queue one by one.
        queue.add(jsonObjectRequest)
    }

    fun shareMe(view: View) {

    }
    fun nextMe(view: View) {

    }
}