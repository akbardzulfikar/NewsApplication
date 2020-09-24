package co.id.newsapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.newsapplication.R
import co.id.newsapplication.adapter.NewsAdapter
import co.id.newsapplication.model.ApiResponse
import co.id.newsapplication.model.Article
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val apiKey = "ce990bbc59fb43b8b77b5f2cf8beafe0"

    private lateinit var adapter: NewsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var articleList: ArrayList<Article> = ArrayList()
    private var gson = Gson()


    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            sharedElementExitTransition = Slide()
        }

        setContentView(R.layout.activity_main)

        initialize()
        fetchData()

    }

    private fun initialize() {

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_news.layoutManager = linearLayoutManager

        adapter = NewsAdapter(articleList, this@MainActivity)
        rv_news.adapter = adapter

        swipeContainer.setOnRefreshListener {
            fetchData()
        }
    }

    private fun fetchData() {
        val deferred = GlobalScope.launch {
            var jsonReturn = URL("https://newsapi.org/v2/top-headlines?country=ca&apiKey=$apiKey").readText()

            var data = gson.fromJson(jsonReturn, ApiResponse::class.java)

            articleList.clear()
            articleList.addAll(data.articles as ArrayList<Article>)

            runOnUiThread {
                adapter.notifyDataSetChanged()

                swipeContainer.isRefreshing = false
            }
        }
    }
}