package com.example.comments.list.sorting

import com.example.store.sorting.SortingDirection
import com.example.store.sorting.SortingOrder

data class SortingParams(

        val order: SortingOrder,
        val direction: SortingDirection

) {

    constructor(params: List<String>) : this(
            // Indices must match string parts in comments_list_sorting_values array.
            order = SortingOrder.create(params[0]),
            direction = SortingDirection.create(params[1])
    )

}
