package edu.vlu.week07.newsreader_2474802016786

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import edu.vlu.week07.newsreader_2474802016786.databinding.ActivityMainBinding
import edu.vlu.week07.newsreader_2474802016786.network.ApiConfig
import edu.vlu.week07.newsreader_2474802016786.network.HttpClient
import edu.vlu.week07.newsreader_2474802016786.parser.NewsJsonParser
import edu.vlu.week07.newsreader_2474802016786.ui.NewsAdapter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val newsAdapter = NewsAdapter()
    private val client = HttpClient()
    private val parser = NewsJsonParser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerNews.layoutManager = LinearLayoutManager(this)
        binding.recyclerNews.adapter = newsAdapter
        binding.buttonReload.setOnClickListener { loadPosts() }
        binding.switchManualParser.setOnCheckedChangeListener { _, _ -> loadPosts() }
        loadPosts()
    }

    private fun loadPosts() {
        binding.progressLoading.visibility = View.VISIBLE
        binding.buttonReload.isEnabled = false
        binding.textStatus.text = getString(R.string.loading_status)

        lifecycleScope.launch {
            try {
                // HttpClient.get switches network I/O to Dispatchers.IO.
                val json = client.get(ApiConfig.POSTS_URL)
                val manual = binding.switchManualParser.isChecked
                val posts = if (manual) parser.parseManually(json) else parser.parseWithGson(json)
                newsAdapter.submitPosts(posts)
                binding.textStatus.text = getString(
                    R.string.loaded_status, posts.size, if (manual) "JSONArray" else "Gson"
                )
                Log.d("LAB15_NETWORK", "Loaded ${posts.size} posts with ${if (manual) "manual" else "Gson"} parser")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("LAB15_NETWORK", "Failed to load posts", e)
                binding.textStatus.text = getString(R.string.error_status)
                newsAdapter.submitPosts(emptyList())
            } finally {
                binding.progressLoading.visibility = View.GONE
                binding.buttonReload.isEnabled = true
            }
        }
    }
}
