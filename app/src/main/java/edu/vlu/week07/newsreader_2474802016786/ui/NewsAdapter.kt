package edu.vlu.week07.newsreader_2474802016786.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.vlu.week07.newsreader_2474802016786.databinding.ItemNewsBinding
import edu.vlu.week07.newsreader_2474802016786.model.NewsItem

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.PostHolder>() {
    private val items = mutableListOf<NewsItem>()

    fun submitPosts(posts: List<NewsItem>) {
        items.clear()
        items.addAll(posts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) = holder.bind(items[position])
    override fun getItemCount(): Int = items.size

    class PostHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: NewsItem) {
            binding.textTitle.text = "#${post.id} · ${post.title}"
            binding.textBody.text = post.body
            binding.textAuthor.text = "User #${post.userId}"
        }
    }
}
