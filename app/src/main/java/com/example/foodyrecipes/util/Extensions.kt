package com.example.foodyrecipes.util

import androidx.appcompat.widget.SearchView


fun SearchView.submitQueryText(search: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (!query.isNullOrEmpty()) {
                search(query)
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean = true
    })
}
