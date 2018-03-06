package com.example.store.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.store.models.Comment

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(comment: Comment)

    // TODO Pass ORDER BY params to function once Room supports this.

    @Query("SELECT * FROM ${Comment.TABLE_NAME} ORDER BY ${Comment.COLUMN_NAME_REMOTE_ID} DESC")
    fun readAllOrderByRemoteIdDesc(): List<Comment>

    @Query("SELECT * FROM ${Comment.TABLE_NAME} WHERE post_id % 2 != :isEvenPostId ORDER BY ${Comment.COLUMN_NAME_REMOTE_ID} DESC")
    fun readAllOrderByRemoteIdDescWithEvenPostId(isEvenPostId: Boolean): List<Comment>

    @Query("SELECT * FROM ${Comment.TABLE_NAME} ORDER BY ${Comment.COLUMN_NAME_EMAIL_ADDRESS} ASC")
    fun readAllOrderByEmailAddressAsc(): List<Comment>

    @Query("SELECT * FROM ${Comment.TABLE_NAME} WHERE post_id % 2 != :isEvenPostId ORDER BY ${Comment.COLUMN_NAME_EMAIL_ADDRESS} ASC")
    fun readAllOrderByEmailAddressAscWithEvenPostId(isEvenPostId: Boolean): List<Comment>

}
