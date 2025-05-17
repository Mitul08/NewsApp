package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class NewsDetailActivity : AppCompatActivity() {

    private var liked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvContent = findViewById<TextView>(R.id.tvContent)
        val btnLike = findViewById<Button>(R.id.btnLike)

        tvTitle.text = title
        tvContent.text = content

        btnLike.setOnClickListener {
            liked = true
            val returnIntent = Intent(this, MainActivity::class.java)
            returnIntent.putExtra("likedTitle", title)
            startActivity(returnIntent)
            finish()
        }

        // Disable back button unless liked
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (liked) {
                    finish()
                }
                // If not liked, do nothing
            }
        })
    }
}