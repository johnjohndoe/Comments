package com.example.store.sorting

sealed class SortingDirection {

    abstract val value: String

    class ASC : SortingDirection() {
        override val value: String
            get() = "ASC"
    }

    class DESC : SortingDirection() {
        override val value: String
            get() = "DESC"
    }

    companion object {

        fun create(value: String): SortingDirection = when (value.toUpperCase()) {
            ASC().value -> ASC()
            DESC().value -> DESC()
            else -> throw RuntimeException("Unknown value: $value")
        }
    }

}
