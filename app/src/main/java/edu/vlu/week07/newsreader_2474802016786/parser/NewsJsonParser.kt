package edu.vlu.week07.newsreader_2474802016786.parser

import com.google.gson.Gson
import edu.vlu.week07.newsreader_2474802016786.model.NewsItem
import org.json.JSONArray

class NewsJsonParser {
    fun parseManually(jsonString: String): List<NewsItem> {
        val array = JSONArray(jsonString)
        return List(array.length()) { i ->
            val obj = array.getJSONObject(i)
            NewsItem(
                id = obj.getInt("id"),
                userId = obj.getInt("userId"),
                title = obj.getString("title"),
                body = obj.getString("body")
            )
        }
    }

    fun parseWithGson(jsonString: String): List<NewsItem> =
        Gson().fromJson(jsonString, Array<NewsItem>::class.java).toList()
}
