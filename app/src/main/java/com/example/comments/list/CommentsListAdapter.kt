package com.example.comments.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.comments.R
import com.example.store.models.Comment

class CommentsListAdapter(

        private val list: List<Comment>

) : RecyclerView.Adapter<CommentViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.comment_item, parent, false)
        context = itemView.context
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = list[position]
        with(holder) {
            postIdView.text = postIdText(comment.postId)
            remoteIdView.text = remoteIdText(comment.remoteId)
            emailAddressView.text = emailAddressText(comment.emailAddress)
            authorNameView.text = comment.authorName
            messageView.text = comment.message
        }
    }

    override fun getItemCount() = list.size
}
