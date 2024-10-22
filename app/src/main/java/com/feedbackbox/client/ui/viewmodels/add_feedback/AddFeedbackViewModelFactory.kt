package com.feedbackbox.client.ui.viewmodels.add_feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.feedbackbox.client.data.repository.FeedbackRepository
import com.feedbackbox.client.ui.viewmodels.main.MainViewModel

class AddFeedbackViewModelFactory(private val repository: FeedbackRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFeedbackViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddFeedbackViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}