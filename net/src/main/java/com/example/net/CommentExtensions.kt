package com.example.net

import com.example.store.models.Comment

fun com.example.api.models.Comment.toStoreComment() =
        Comment(remoteId = remoteId,
                postId = postId,
                authorName = authorName,
                emailAddress = emailAddress,
                message = message
        )
