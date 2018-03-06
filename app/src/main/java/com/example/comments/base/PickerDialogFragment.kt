package com.example.comments.base

import android.app.Dialog
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.comments.extensions.getLayoutInflater
import com.example.comments.extensions.getPreferences

abstract class PickerDialogFragment : DialogFragment() {

    private lateinit var spinner: Spinner

    abstract fun layout(): Int

    abstract fun title(): Int

    abstract fun spinnerView(): Int

    abstract fun spinnerTitles(): Int

    abstract fun defaultIndex(): Int

    abstract fun selectedIndexPrefKey(): String

    abstract fun onItemPick()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = context!!.getLayoutInflater()
        val layout = inflater.inflate(layout(), null, false)
        initializeSpinner(layout)
        return showDialog(layout)
    }

    private fun showDialog(layout: View) = AlertDialog.Builder(activity!!)
            .setView(layout)
            .setTitle(title())
            .setPositiveButton(android.R.string.ok) { _, _ ->
                storeSelectionIfChanged()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()

    private fun initializeSpinner(@NonNull rootView: View) {
        spinner = rootView.findViewById(spinnerView())
        val adapter = ArrayAdapter.createFromResource(
                activity!!,
                spinnerTitles(),
                android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(storedSelectedIndex())
    }

    private fun storeSelectionIfChanged() {
        val selectedIndex = spinner.selectedItemPosition
        if (selectedIndex != storedSelectedIndex()) {
            storeSelection(selectedIndex)
        }
    }

    private fun storeSelection(selectedIndex: Int) {
        val preferences = activity!!.getPreferences()
        preferences.edit().putInt(selectedIndexPrefKey(), selectedIndex).apply()
        onItemPick()
    }

    private fun storedSelectedIndex(): Int {
        val context = activity!!
        val defaultIndex = context.resources.getInteger(defaultIndex())
        val preferences = context.getPreferences()
        return preferences.getInt(selectedIndexPrefKey(), defaultIndex)
    }

}
