package com.example.comments.list.filtering

import android.support.v4.app.FragmentActivity
import com.example.comments.BuildConfig
import com.example.comments.FILTERING_INDEX_PREF_KEY
import com.example.comments.R
import com.example.comments.base.PickerDialogFragment


class FilteringPicker : PickerDialogFragment() {

    private lateinit var onFilteringPick: () -> Unit

    override fun layout(): Int = R.layout.filtering_dialog

    override fun title(): Int = R.string.comments_list_filtering_title

    override fun spinnerView(): Int = R.id.comments_list_filtering_view

    override fun spinnerTitles(): Int = R.array.comments_list_filtering_titles

    override fun defaultIndex(): Int = R.integer.comments_list_filtering_default_index

    override fun selectedIndexPrefKey(): String = FILTERING_INDEX_PREF_KEY

    override fun onItemPick() {
        onFilteringPick.invoke()
    }

    companion object {

        private const val FRAGMENT_TAG =
                BuildConfig.APPLICATION_ID + ".SORTING_PICKER_FRAGMENT_TAG"

        fun show(activity: FragmentActivity, onFilteringPick: () -> Unit) {
            val dialogFragment = FilteringPicker()
            dialogFragment.onFilteringPick = onFilteringPick
            val fragmentManager = activity.supportFragmentManager
            dialogFragment.show(fragmentManager, FRAGMENT_TAG)
        }

    }

}
