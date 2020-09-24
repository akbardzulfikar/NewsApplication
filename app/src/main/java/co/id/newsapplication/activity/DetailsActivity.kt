package co.id.newsapplication.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.core.view.ViewCompat
import co.id.newsapplication.R
import co.id.newsapplication.model.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(R.transition.news_detail_transition)
        setContentView(R.layout.activity_details)

        val article = intent.getSerializableExtra("article") as Article
        val tName = intent.getStringExtra("transitionName")

        ViewCompat.setTransitionName(news_item_img, tName)

        setArticle(article)
    }

    private fun setArticle(article: Article) {
        news_item_body.text = if (article.content.length >= 260) article.content.substring(0, 260) else article.content.substring(0, article.content.indexOf("["))
        news_item_title.text = article.title
        Glide.with(this)
            .load(article.urlToImage)
            .fitCenter()
            .into(news_item_img)

        news_item_source.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(article.url)
            startActivity(i)
        }
    }
}
