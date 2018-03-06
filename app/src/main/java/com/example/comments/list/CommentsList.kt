package com.example.comments.list

import com.example.store.filtering.QueryFilter
import com.example.comments.list.sorting.SortingParams
import com.example.store.models.Comment
import rx.Observable

interface CommentsList {

    interface Model {

        fun loadComments(sortingParams: SortingParams,
                         queryFilter: QueryFilter,
                         isInitial: Boolean = true,
                         isVisualChange: Boolean)

        fun cancelLoadingComments()

        fun stateObservable(): Observable<CommentsListState>

    }

    interface View {

        fun showComments(comments: List<Comment>)

        fun hideComments()

        fun showProgress()

        fun hideProgress()

        fun showInfoMessage(text: String)

        fun hideInfoMessage()

        fun showErrorMessage(text: String)

        fun hideErrorMessage()

    }

    interface Presenter {

        fun onViewCreated()

        fun onViewDestroy()

        fun loadComments(sortingParams: SortingParams,
                         queryFilter: QueryFilter,
                         isVisualChange: Boolean = false)

        fun reloadComments(sortingParams: SortingParams,
                           queryFilter: QueryFilter)

        fun onLoadCommentsFailure()

        fun onConnectionMissing()

    }

}
