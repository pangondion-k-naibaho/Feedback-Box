package com.feedbackbox.client.ui.viewmodels.add_feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feedbackbox.client.data.model.Feedback
import com.feedbackbox.client.data.repository.FeedbackRepository
import kotlinx.coroutines.launch

class AddFeedbackViewModel(private val repository: FeedbackRepository): ViewModel() {
    private val TAG = AddFeedbackViewModel::class.java.simpleName

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isFail = MutableLiveData<Boolean>()
    val isFail: LiveData<Boolean> = _isFail

    fun saveFeedback(feedback: Feedback){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.insertFeedback(feedback)
                _isLoading.value = false
                _isFail.value = false
            }catch (e:Exception){
                _isLoading.value = false
                _isFail.value = true
            }
        }
    }
}