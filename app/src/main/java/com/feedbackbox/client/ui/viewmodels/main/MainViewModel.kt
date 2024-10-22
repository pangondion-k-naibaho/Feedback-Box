package com.feedbackbox.client.ui.viewmodels.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feedbackbox.client.data.model.Feedback
import com.feedbackbox.client.data.repository.FeedbackRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: FeedbackRepository): ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isFail = MutableLiveData<Boolean>()
    val isFail : LiveData<Boolean> = _isFail

    private var _feedbacksResponse = MutableLiveData<List<Feedback>>()
    val feedbacksResponse : LiveData<List<Feedback>> = _feedbacksResponse

    fun getAllFeedback(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.getAllFeedback().observeForever{
                    _feedbacksResponse.value = it
                    _isLoading.value = false
                }
            }catch (e: Exception){
                _isLoading.value = false
                _isFail.value = true
            }
        }
    }
}