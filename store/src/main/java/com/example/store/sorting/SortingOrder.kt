package com.example.store.sorting

import com.example.store.models.Comment

sealed class SortingOrder {

    abstract val value: String

    class RemoteId : SortingOrder() {
        override val value: String
            get() = Comment.COLUMN_NAME_REMOTE_ID
    }

    class EmailAddress : SortingOrder() {
        override val value: String
            get() = Comment.COLUMN_NAME_EMAIL_ADDRESS
    }

    companion object {

        fun create(value: String): SortingOrder = when (value.toLowerCase()) {
            RemoteId().value -> RemoteId()
            EmailAddress().value -> EmailAddress()
            else -> throw RuntimeException("Unknown value: $value")
        }

    }


}
