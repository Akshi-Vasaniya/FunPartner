package com.example.funpartner

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    var currentImgUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        loadMeme()
    }

    fun loadMeme(){
        progressBar.visibility = View.VISIBLE
        // Initialize/Create the reqQueue
//        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"

        // Send JSON req
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url, null,
            { response ->
                // Get the response
                currentImgUrl = response.getString("url")
                Log.d(TAG, "loadMeme: url = $url")
                val memeView = findViewById<ImageView>(R.id.memeImageView)
                // Load the image get from url
                Glide.with(this).load(currentImgUrl).listener(object: RequestListener<Drawable>{
                    // Calls two function when either image load or failed.
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Img failed to load, stop progress bar and display error log
                        progressBar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Image Failed to load!", Toast.LENGTH_SHORT).show()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(memeView)
            },
            { error ->
                // Get the error
                Log.d(TAG, "loadMeme: ${error.message}")
            }
        )

        // Execute the req from the queue one by one.
//        queue.add(jsonObjectRequest)

        // Single Instance is create by this, and hence used whole time.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun shareMe(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        // To know other application what we are sharing.
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, checkout this cool meme $currentImgUrl")

        // Chooser to choose where to share.
        val chooser = Intent.createChooser(intent, "Share this amazing meme!")
        startActivity(chooser)
    }
    fun nextMe(view: View) {
        loadMeme()
    }
}