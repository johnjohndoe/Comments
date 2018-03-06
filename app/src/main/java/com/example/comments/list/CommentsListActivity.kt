package com.example.comments.list

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.example.comments.App
import com.example.comments.FILTERING_INDEX_PREF_KEY
import com.example.comments.R
import com.example.comments.SORTING_INDEX_PREF_KEY
import com.example.comments.extensions.getPreferences
import com.example.comments.extensions.hide
import com.example.comments.extensions.isConnectionAvailable
import com.example.comments.extensions.show
import com.example.comments.list.filtering.FilteringPicker
import com.example.comments.list.sorting.SortingParams
import com.example.comments.list.sorting.SortingPicker
import com.example.net.LOAD_COMMENTS_ACTION
import com.example.net.LOAD_COMMENTS_RESULT_BUNDLE_KEY
import com.example.store.filtering.QueryFilter
import com.example.store.models.Comment
import kotlinx.android.synthetic.main.activity_comments_list.*
import java.util.*

class CommentsListActivity : AppCompatActivity(), CommentsList.View {

    private lateinit var presenter: CommentsListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(receiver, IntentFilter(LOAD_COMMENTS_ACTION))
        initView()
        initPresenter()
        loadComments()
    }

    private fun initView() {
        setContentView(R.layout.activity_comments_list)
        setSupportActionBar(commentsListToolbar as Toolbar)
        supportActionBar!!.setTitle(R.string.app_name)
        commentsList.setHasFixedSize(true)
        commentsList.layoutManager = LinearLayoutManager(this)
        commentsList.adapter = CommentsListAdapter(Collections.emptyList())
    }

    private fun initPresenter() {
        val repository = CommentsListRepository(App().store)
        val model = CommentsListModel(repository)
        val commentsNotFoundText = getString(R.string.comments_not_found)
        val commentsNotYetText = getString(R.string.comments_not_yet)
        val commentsErrorLoadingText = getString(R.string.comments_error_loading)
        val connectionMissingText = getString(R.string.connection_missing)
        presenter = CommentsListPresenter(
                model,
                this,
                commentsNotFoundText,
                commentsNotYetText,
                commentsErrorLoadingText,
                connectionMissingText)
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        presenter.onViewDestroy()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.comments_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_sort) {
            SortingPicker.show(this, onSortingPick = {
                loadComments(true)
            })
            return true
        }
        if (id == R.id.action_filter) {
            FilteringPicker.show(this, onFilteringPick = {
                loadComments(true)
            })
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadComments(isVisualChange: Boolean = false) {
        val sortingParams = getSortingParams()
        val queryFilter = getQueryFilter()
        if (!isConnectionAvailable()) {
            presenter.loadComments(sortingParams, queryFilter, isVisualChange = true)
        } else {
            presenter.loadComments(sortingParams, queryFilter, isVisualChange = isVisualChange)
        }
    }

    private fun getSortingParams(): SortingParams {
        val defaultIndex = resources.getInteger(R.integer.comments_list_sorting_default_index)
        val index = getPreferences().getInt(SORTING_INDEX_PREF_KEY, defaultIndex)
        val sortingValues = resources.getStringArray(R.array.comments_list_sorting_values)
        val sortingValue = sortingValues[index]
        val sortingParamsList: List<String> = sortingValue.split(" ")
        return SortingParams(sortingParamsList)
    }

    private fun getQueryFilter(): QueryFilter {
        val defaultIndex = resources.getInteger(R.integer.comments_list_filtering_default_index)
        val index = getPreferences().getInt(FILTERING_INDEX_PREF_KEY, defaultIndex)
        val filteringValues = resources.getStringArray(R.array.comments_list_filtering_values)
        val filteringValue = filteringValues[index]
        return QueryFilter.create(filteringValue)
    }

    override fun hideComments() {
        commentsList.hide()
    }

    override fun showComments(comments: List<Comment>) {
        commentsList.show()
        commentsList.adapter = CommentsListAdapter(comments)
        commentsList.adapter.notifyDataSetChanged()
    }

    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }

    override fun showInfoMessage(text: String) {
        infoMessage.text = text
        infoMessage.show()
    }

    override fun hideInfoMessage() {
        infoMessage.hide()
    }

    override fun showErrorMessage(text: String) {
        errorMessage.text = text
        errorMessage.show()
    }

    override fun hideErrorMessage() {
        errorMessage.hide()
    }

    private var receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                if (intent.action == LOAD_COMMENTS_ACTION) {
                    val isSuccessful = intent.getBooleanExtra(
                            LOAD_COMMENTS_RESULT_BUNDLE_KEY,
                            false)
                    val sortingParams = getSortingParams()
                    val queryFilter = getQueryFilter()
                    if (isSuccessful) {
                        presenter.reloadComments(sortingParams, queryFilter)
                    } else {
                        if (isConnectionAvailable()) {
                            presenter.onLoadCommentsFailure()
                        } else {
                            presenter.onConnectionMissing()
                        }
                    }
                }
            }
        }
    }

}
