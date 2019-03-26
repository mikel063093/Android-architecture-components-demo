package co.mike.zemoga.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.mike.zemoga.R
import co.mike.zemoga.models.Post
import kotlinx.android.synthetic.main.view_post.view.imageview_status
import kotlinx.android.synthetic.main.view_post.view.textView_description

class PostAdapter(private val context: Context, private val data: ArrayList<Post>, private val onItemClickListener: OnItemClickListener? = null) : RecyclerView.Adapter<PostAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_post, parent, false)
        return Holder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(data[position])
    }

    fun loadPosts(posts: List<Post>) {
        if (!data.isEmpty()) {
            data.clear()
        }
        data.addAll(posts)
        notifyDataSetChanged()
    }

    class Holder(itemView: View?, private val onItemClickListener: OnItemClickListener? = null) : RecyclerView.ViewHolder(itemView!!) {
        fun bindView(itemPost: Post?) {
            itemPost?.let { post ->
                itemView.textView_description.text = post.title
                itemView.textView_description.visibility = if (post.title.isNotEmpty()) VISIBLE else GONE
                if (post.favorite) {
                    itemView.imageview_status.visibility = VISIBLE
                    itemView.imageview_status.setImageResource(R.drawable.ic_star_gold)
                } else {
                    itemView.imageview_status.setImageResource(R.drawable.ic_unread)
                    itemView.imageview_status.visibility = if (post.read) INVISIBLE else VISIBLE
                }
                itemView.setOnClickListener {
                    onItemClickListener?.onItemClicked(itemPost)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(itemView: Post)
    }
}