package com.feedbackbox.client.ui.viewmodels.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.feedbackbox.client.data.repository.FeedbackRepository

class MainViewModelFactory(private val repository: FeedbackRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}