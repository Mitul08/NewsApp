package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var btnGetNews: Button
    private lateinit var newsListContainer: LinearLayout
    private lateinit var likedNewsContainer: LinearLayout
    private lateinit var likedLabel: TextView
    private val likedNewsTitles = mutableListOf<String>()
    private lateinit var newsList: List<NewsItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGetNews = findViewById(R.id.btnGetNews)
        newsListContainer = findViewById(R.id.newsListContainer)
        likedNewsContainer = findViewById(R.id.likedNewsContainer)
        likedLabel = findViewById(R.id.tvLikedLabel)

        btnGetNews.setOnClickListener {
            loadNewsFromAssets()
            displayNewsTitles()
            btnGetNews.visibility = View.GONE
        }

        handleReturnFromDetail()
    }

    private fun loadNewsFromAssets() {
        val inputStream = assets.open("news.json")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val json = reader.readText()
        val type = object : TypeToken<List<NewsItem>>() {}.type
        newsList = Gson().fromJson(json, type)
    }

    private fun displayNewsTitles() {
        newsListContainer.removeAllViews()
        for (news in newsList) {
            val tv = TextView(this)
            tv.text = news.title
            tv.textSize = 16f
            tv.setPadding(0, 12, 0, 12)
            tv.setOnClickListener {
                val intent = Intent(this, NewsDetailActivity::class.java)
                intent.putExtra("title", news.title)
                intent.putExtra("content", news.content)
                startActivity(intent)
            }
            newsListContainer.addView(tv)
        }
    }

    private fun handleReturnFromDetail() {
        val likedTitle = intent.getStringExtra("likedTitle")
        likedTitle?.let {
            if (!likedNewsTitles.contains(it)) {
                likedNewsTitles.add(it)
                likedLabel.visibility = View.VISIBLE

                val bulletItem = TextView(this)
                bulletItem.text = "• $it"
                bulletItem.textSize = 14f
                bulletItem.setPadding(0, 8, 0, 8)
                likedNewsContainer.addView(bulletItem)
            }
        }
    }
}