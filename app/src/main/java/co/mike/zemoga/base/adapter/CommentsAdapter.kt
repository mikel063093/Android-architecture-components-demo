package co.mike.zemoga.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.mike.zemoga.R
import co.mike.zemoga.models.Comment
import kotlinx.android.synthetic.main.view_item_comment.view.bodyTextView
import kotlinx.android.synthetic.main.view_item_comment.view.emailTextView
import kotlinx.android.synthetic.main.view_item_comment.view.titleTextView

class CommentsAdapter(private val context: Context, private val data: ArrayList<Comment>,
                      private val onItemClickListener: OnItemClickListener? = null) : RecyclerView.Adapter<CommentsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_comment, parent, false)
        return Holder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(data[position])
    }

    fun loadItems(posts: List<Comment>) {
        if (!data.isEmpty()) {
            data.clear()
        }
        data.addAll(posts)
        notifyDataSetChanged()
    }

    class Holder(itemView: View?, private val onItemClickListener: OnItemClickListener? = null) : RecyclerView.ViewHolder(itemView!!) {
        fun bindView(item: Comment?) {
            item?.let { comment ->
                itemView.emailTextView.text = comment.email
                itemView.titleTextView.text = comment.name
                itemView.bodyTextView.text = comment.body
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(item: Comment)
    }
}