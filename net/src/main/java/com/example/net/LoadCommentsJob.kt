package com.example.net

import android.content.Intent
import android.util.Log
import com.evernote.android.job.Job
import com.example.api.TypicodeService
import com.example.api.models.Comment
import com.example.store.TypicodeDatabase
import com.example.store.dao.CommentDao
import retrofit2.HttpException
import java.io.IOException

class LoadCommentsJob(

        private val store: TypicodeDatabase,
        private val service: TypicodeService

) : Job() {

    private lateinit var commentDao: CommentDao

    override fun onRunJob(params: Params): Result {
        val call = service.getComments()
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val comments = response.body()
                comments?.let {
                    storeComments(comments)
                }
            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            // TODO Track error
            Log.e(javaClass.name, e.message)
            context.sendBroadcast(getLoadCommentsIntent(false))
            return Result.FAILURE
        }
        context.sendBroadcast(getLoadCommentsIntent(true))
        return Result.SUCCESS
    }

    private fun storeComments(comments: List<Comment>) {
        commentDao = store.commentDao()
        comments.forEach { storeComment(it) }
    }

    private fun storeComment(comment: Comment) {
        val storeableComment = comment.toStoreComment()
        commentDao.create(storeableComment)
    }

    private fun getLoadCommentsIntent(isSuccessful: Boolean) =
            Intent(LOAD_COMMENTS_ACTION).apply {
                putExtra(LOAD_COMMENTS_RESULT_BUNDLE_KEY, isSuccessful)
            }

    companion object {
        const val TAG = "${BuildConfig.APPLICATION_ID}.LoadCommentsJob"
    }

}
