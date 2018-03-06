package com.example.comments.list

import com.example.store.models.Comment

sealed class CommentsListState {

    data class Data(

            val comments: List<Comment>,
            val isInitial: Boolean

    ) : CommentsListState()

    class Loading : CommentsListState()

    class Idle : CommentsListState()

}
