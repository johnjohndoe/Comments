package com.example.comments.list

import com.example.store.filtering.QueryFilter
import com.example.comments.list.sorting.SortingParams
import rx.Observable
import rx.subjects.BehaviorSubject

class CommentsListModel(

        private val repository: CommentsListRepository

) : CommentsList.Model {

    private val stateSubject = BehaviorSubject
            .create<CommentsListState>(CommentsListState.Idle())

    override fun loadComments(sortingParams: SortingParams,
                              queryFilter: QueryFilter,
                              isInitial: Boolean,
                              isVisualChange: Boolean) {
        stateSubject.onNext(CommentsListState.Loading())
        repository.loadComments(
                sortingParams = sortingParams,
                queryFilter = queryFilter,
                isInitial = isInitial,
                isVisualChange = isVisualChange,
                onCommentsLoaded = {
                    stateSubject.onNext(CommentsListState.Data(it, isInitial))
                }
        )
    }

    override fun cancelLoadingComments() {
        repository.cancelLoadingComments()
    }

    override fun stateObservable(): Observable<CommentsListState> = stateSubject

}
