package com.feedbackbox.client.data.repository

import androidx.lifecycle.LiveData
import com.feedbackbox.client.data.local.FeedbackDao
import com.feedbackbox.client.data.model.Feedback

class FeedbackRepositoryImpl(private val feedbackDao: FeedbackDao): FeedbackRepository {
    override fun getAllFeedback(): LiveData<List<Feedback>> {
        return feedbackDao.getAllFeedback()
    }

    override suspend fun insertFeedback(feedback: Feedback) {
        return feedbackDao.insert(feedback)
    }

    override suspend fun deleteFeedback(feedback: Feedback) {
        return feedbackDao.delete(feedback)
    }

    override suspend fun updateFeedback(id: String, name: String, type: String, contents: String) {
        return feedbackDao.updateFeedback(id, name, type, contents)
    }

}