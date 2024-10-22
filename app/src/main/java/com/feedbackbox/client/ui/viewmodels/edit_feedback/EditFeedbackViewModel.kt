package com.feedbackbox.client.ui.viewmodels.edit_feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feedbackbox.client.data.model.Feedback
import com.feedbackbox.client.data.repository.FeedbackRepository
import kotlinx.coroutines.launch

class EditFeedbackViewModel(private val repository: FeedbackRepository): ViewModel() {
    private val TAG = EditFeedbackViewModel::class.java.simpleName

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isFailUpdate = MutableLiveData<Boolean>()
    val isFailUpdate: LiveData<Boolean> = _isFailUpdate

    private var _isFailDelete = MutableLiveData<Boolean>()
    val isFailDelete: LiveData<Boolean> = _isFailDelete

    fun saveEditedFeedback(id: String, name: String, type: String, content: String){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.updateFeedback(id, name, type, content)
                _isLoading.value = false
                _isFailUpdate.value = false
            }catch (e:Exception){
                _isLoading.value = false
                _isFailUpdate.value = true
            }
        }
    }

    fun deleteFeedback(feedback: Feedback){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.deleteFeedback(feedback)
                _isLoading.value = false
                _isFailDelete.value = false
            }catch (e:Exception){
                _isLoading.value = false
                _isFailDelete.value = true
            }
        }
    }
}