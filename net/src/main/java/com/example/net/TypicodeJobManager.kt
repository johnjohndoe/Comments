@file:JvmName("TypicodeJobManager")

package com.example.net

import android.content.Context
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest
import com.example.api.TypicodeService
import com.example.store.TypicodeDatabase

const val LOAD_COMMENTS_ACTION = "LoadComments"
const val LOAD_COMMENTS_RESULT_BUNDLE_KEY = "LoadCommentsResult"

fun initJobScheduler(
        context: Context,
        store: TypicodeDatabase,
        service: TypicodeService
) {
    val creator = TypicodeJobCreator(store, service)
    JobManager.create(context).addJobCreator(creator)
}


fun scheduleLoadCommentsJob(): Int {
    return JobRequest.Builder(LoadCommentsJob.TAG)
            .startNow()
            .build()
            .schedule()
}

