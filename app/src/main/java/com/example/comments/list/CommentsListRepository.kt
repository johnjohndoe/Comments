package com.example.comments.list

import com.example.comments.list.sorting.SortingParams
import com.example.net.scheduleLoadCommentsJob
import com.example.store.TypicodeDatabase
import com.example.store.filtering.QueryFilter
import com.example.store.models.Comment
import com.example.store.sorting.SortingDirection
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class CommentsListRepository(

        private val store: TypicodeDatabase

) {

    private val jobs = mutableListOf<Job>()

    fun loadComments(sortingParams: SortingParams,
                     queryFilter: QueryFilter,
                     isInitial: Boolean,
                     isVisualChange: Boolean,
                     onCommentsLoaded: (List<Comment>) -> Unit) {
        var job: Job? = null
        job = launch(UI) {
            try {
                val comments = loadCommentsFromDatabase(sortingParams, queryFilter).await()
                onCommentsLoaded.invoke(comments)
            } catch (throwable: Throwable) {
                handleError(job, throwable)
            }
            jobs.add(job!!)
        }
        if (isInitial && !isVisualChange) {
            scheduleLoadCommentsJob()
        }
    }

    fun cancelLoadingComments() {
        jobs.forEach {
            it.cancel()
        }
    }

    private fun loadCommentsFromDatabase(sortingParams: SortingParams, queryFilter: QueryFilter)
            : Deferred<List<Comment>> = async {
        when {
            sortingParams.order.value == Comment.COLUMN_NAME_REMOTE_ID &&
                    sortingParams.direction.value == SortingDirection.DESC().value &&
                    queryFilter.value == QueryFilter.ShowAll().value
            ->
                store.commentDao().readAllOrderByRemoteIdDesc()

            sortingParams.order.value == Comment.COLUMN_NAME_REMOTE_ID &&
                    sortingParams.direction.value == SortingDirection.DESC().value &&
                    queryFilter.value == QueryFilter.ShowEvenPostIds().value
            ->
                store.commentDao().readAllOrderByRemoteIdDescWithEvenPostId(true)

            sortingParams.order.value == Comment.COLUMN_NAME_EMAIL_ADDRESS &&
                    sortingParams.direction.value == SortingDirection.ASC().value &&
                    queryFilter.value == QueryFilter.ShowAll().value
            ->
                store.commentDao().readAllOrderByEmailAddressAsc()

            sortingParams.order.value == Comment.COLUMN_NAME_EMAIL_ADDRESS &&
                    sortingParams.direction.value == SortingDirection.ASC().value &&
                    queryFilter.value == QueryFilter.ShowEvenPostIds().value
            ->
                store.commentDao().readAllOrderByEmailAddressAscWithEvenPostId(true)

            else -> throw RuntimeException("Unknown sortingParams: $sortingParams")
        }
    }

    private fun handleError(job: Job?, throwable: Throwable) {
        jobs.remove(job)
        throw RuntimeException(throwable)
    }

}
