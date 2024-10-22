package com.feedbackbox.client.data.repository

import androidx.lifecycle.LiveData
import com.feedbackbox.client.data.model.Feedback

interface FeedbackRepository {
    fun getAllFeedback(): LiveData<List<Feedback>>
    suspend fun insertFeedback(feedback: Feedback)
    suspend fun deleteFeedback(feedback: Feedback)
    suspend fun updateFeedback(id: String, name: String, type: String, contents: String)
}