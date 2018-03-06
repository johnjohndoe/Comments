package com.example.comments.list

import com.example.store.filtering.QueryFilter
import com.example.comments.list.sorting.SortingParams
import com.example.store.models.Comment
import rx.Subscription
import rx.schedulers.Schedulers

class CommentsListPresenter(

        private val model: CommentsList.Model,
        private val view: CommentsList.View,
        private val notFoundText: String,
        private val notYetText: String,
        private val errorLoadingText: String,
        private val connectionMissingText: String

) : CommentsList.Presenter {

    private lateinit var modelSubscription: Subscription

    override fun onViewCreated() {
        modelSubscription = model.stateObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(::renderState, ::onError)
    }

    private fun onError(throwable: Throwable) {
        throw RuntimeException(throwable)
    }

    override fun onViewDestroy() {
        model.cancelLoadingComments()
        if (!modelSubscription.isUnsubscribed) {
            modelSubscription.unsubscribe()
        }
    }

    private fun renderState(state: CommentsListState) = when (state) {
        is CommentsListState.Idle -> renderInfoMessage(notYetText)
        is CommentsListState.Loading -> renderProgress()
        is CommentsListState.Data -> renderData(state)
    }

    private fun renderData(state: CommentsListState.Data) {
        if (state.comments.isEmpty()) {
            if (state.isInitial) {
                // Keep progress
            } else {
                renderInfoMessage(notFoundText)
            }
        } else {
            renderComments(state.comments)
        }
    }

    private fun renderInfoMessage(message: String) = with(view) {
        hideComments()
        hideProgress()
        hideErrorMessage()
        showInfoMessage(message)
    }

    private fun renderErrorMessage() = with(view) {
        hideComments()
        hideProgress()
        hideInfoMessage()
        showErrorMessage(errorLoadingText)
    }

    private fun renderProgress() = with(view) {
        hideComments()
        hideErrorMessage()
        hideInfoMessage()
        showProgress()
    }

    private fun renderComments(comments: List<Comment>) = with(view) {
        hideInfoMessage()
        hideErrorMessage()
        hideProgress()
        showComments(comments)
    }

    override fun loadComments(sortingParams: SortingParams,
                              queryFilter: QueryFilter,
                              isVisualChange: Boolean) =
            model.loadComments(sortingParams,
                    queryFilter,
                    isInitial = true,
                    isVisualChange = isVisualChange)

    override fun reloadComments(sortingParams: SortingParams, queryFilter: QueryFilter) =
            model.loadComments(sortingParams,
                    queryFilter,
                    isInitial = false,
                    isVisualChange = false)

    override fun onLoadCommentsFailure() = renderErrorMessage()

    override fun onConnectionMissing() = renderInfoMessage(connectionMissingText)

}
