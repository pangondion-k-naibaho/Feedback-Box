package com.feedbackbox.client.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.feedbackbox.client.data.model.Feedback

@Dao
interface FeedbackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(feedback: Feedback)

    @Query("SELECT * FROM feedback_table WHERE id = :id")
    suspend fun getFeedbackById(id: String): Feedback?

    @Query("UPDATE feedback_table SET name = :name, type = :type, contents = :contents WHERE id = :id")
    suspend fun updateFeedback(id: String, name: String, type: String, contents: String)

    @Delete
    suspend fun delete(feedback: Feedback)

    @Query("SELECT * FROM feedback_table")
    fun getAllFeedback(): LiveData<List<Feedback>>
}