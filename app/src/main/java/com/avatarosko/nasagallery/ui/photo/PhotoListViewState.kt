package com.avatarosko.nasagallery.ui.photo

import android.view.View

data class PhotoListViewState(
    var isLoading: Boolean = false,
    val errorMessage: String = "",
) {
    fun getLoadingVisibility(): Int {
        return if (isLoading) View.VISIBLE
        else View.GONE
    }

    fun getVisibilityErrorContainer(): Int {
        return if (isLoading || errorMessage.isNotBlank()) View.VISIBLE
        else View.GONE
    }

    fun getVisibilityErrorContainerNot(): Int {
        return if (isLoading || errorMessage.isNotBlank()) View.GONE
        else View.VISIBLE
    }

    fun errorMessageVisibility(): Int {
        return if (errorMessage.isBlank()) View.GONE
        else View.VISIBLE
    }
}