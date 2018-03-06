package com.example.net

import com.evernote.android.job.JobCreator
import com.example.api.TypicodeService
import com.example.store.TypicodeDatabase

class TypicodeJobCreator(

        private val store: TypicodeDatabase,
        private val service: TypicodeService

) : JobCreator {

    override fun create(tag: String) = when (tag) {
        LoadCommentsJob.TAG -> LoadCommentsJob(store, service)
        else -> null
    }

}
