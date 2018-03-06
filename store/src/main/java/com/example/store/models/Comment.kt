package com.example.store.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.example.store.models.Comment.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
class Comment(

        @PrimaryKey(autoGenerate = true)
        val clientId: Int,

        @ColumnInfo(name = COLUMN_NAME_REMOTE_ID)
        val remoteId: Int,

        @ColumnInfo(name = COLUMN_NAME_POST_ID)
        val postId: Int,

        @ColumnInfo(name = COLUMN_NAME_AUTHOR_NAME)
        val authorName: String,

        @ColumnInfo(name = COLUMN_NAME_EMAIL_ADDRESS)
        val emailAddress: String,

        @ColumnInfo(name = COLUMN_NAME_MESSAGE)
        val message: String

) {

    @Ignore
    constructor(
            remoteId: Int,
            postId: Int,
            authorName: String,
            emailAddress: String,
            message: String
    ) : this(
            clientId = DEFAULT_CLIENT_ID_VALUE,
            remoteId = remoteId,
            postId = postId,
            authorName = authorName,
            emailAddress = emailAddress,
            message = message
    )

    companion object {
        const val TABLE_NAME = "comments"
        const val DEFAULT_CLIENT_ID_VALUE = 0
        const val COLUMN_NAME_REMOTE_ID = "remote_id"
        const val COLUMN_NAME_POST_ID = "post_id"
        const val COLUMN_NAME_AUTHOR_NAME = "author_name"
        const val COLUMN_NAME_EMAIL_ADDRESS = "email_address"
        const val COLUMN_NAME_MESSAGE = "message"
    }

}
