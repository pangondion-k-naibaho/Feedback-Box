package com.feedbackbox.client.ui.viewmodels.edit_feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.feedbackbox.client.data.repository.FeedbackRepository

class EditFeedbackViewModelFactory(private val repository: FeedbackRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EditFeedbackViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return EditFeedbackViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}