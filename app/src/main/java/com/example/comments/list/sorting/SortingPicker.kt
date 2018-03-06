package com.example.comments.list.sorting

import android.support.v4.app.FragmentActivity
import com.example.comments.BuildConfig
import com.example.comments.R
import com.example.comments.SORTING_INDEX_PREF_KEY
import com.example.comments.base.PickerDialogFragment


class SortingPicker : PickerDialogFragment() {

    private lateinit var onSortingPick: () -> Unit

    override fun layout(): Int = R.layout.sorting_dialog

    override fun title(): Int = R.string.comments_list_sorting_title

    override fun spinnerView(): Int = R.id.comments_list_sorting_view

    override fun spinnerTitles(): Int = R.array.comments_list_sorting_titles

    override fun defaultIndex(): Int = R.integer.comments_list_sorting_default_index

    override fun selectedIndexPrefKey(): String = SORTING_INDEX_PREF_KEY

    override fun onItemPick() {
        onSortingPick.invoke()
    }

    companion object {

        private const val FRAGMENT_TAG =
                BuildConfig.APPLICATION_ID + ".SORTING_PICKER_FRAGMENT_TAG"

        fun show(activity: FragmentActivity, onSortingPick: () -> Unit) {
            val dialogFragment = SortingPicker()
            dialogFragment.onSortingPick = onSortingPick
            val fragmentManager = activity.supportFragmentManager
            dialogFragment.show(fragmentManager, FRAGMENT_TAG)
        }

    }

}
