package com.example.comments.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.comments.R

class CommentViewHolder(

        private val view: View

) : RecyclerView.ViewHolder(view) {

    private val resources = view.resources

    val postIdView by lazy {
        view.findViewById(R.id.postIdView) as TextView
    }

    val remoteIdView by lazy {
        view.findViewById(R.id.remoteIdView) as TextView
    }

    val authorNameView by lazy {
        view.findViewById(R.id.authorNameView) as TextView
    }

    val emailAddressView by lazy {
        view.findViewById(R.id.emailAddressView) as TextView
    }

    val messageView by lazy {
        view.findViewById(R.id.messageView) as TextView
    }

    fun postIdText(postId: Int): String = resources.getString(R.string.comment_post_id, postId)

    fun remoteIdText(remoteId: Int): String = resources.getString(R.string.comment_remote_id, remoteId)

    fun emailAddressText(emailAddress: String) = emailAddress.toLowerCase()

}
