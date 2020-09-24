package co.id.newsapplication.model

import java.io.Serializable

data class Article(
    val source: ArticleSource,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String) :
    Serializable {
}

class ArticleSource(val id: String, val name: String) : Serializable