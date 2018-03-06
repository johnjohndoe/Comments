package com.example.store.filtering

sealed class QueryFilter {

    abstract val value: String

    class ShowAll : QueryFilter() {
        override val value: String
            get() = "all_posts"
    }

    class ShowEvenPostIds : QueryFilter() {
        override val value: String
            get() = "even_post_ids"
    }

    companion object {

        fun create(value: String): QueryFilter = when (value.toLowerCase()) {
            ShowAll().value -> ShowAll()
            ShowEvenPostIds().value -> ShowEvenPostIds()
            else -> throw RuntimeException("Unknown value: $value")
        }
    }

}
