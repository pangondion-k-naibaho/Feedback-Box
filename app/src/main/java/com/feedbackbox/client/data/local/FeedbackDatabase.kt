package com.feedbackbox.client.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.feedbackbox.client.data.model.Feedback

@Database(
    entities = [Feedback::class],
    version = 1
)
abstract class FeedbackDatabase: RoomDatabase() {
    companion object{
        private var INSTANCE : FeedbackDatabase?= null

        fun getDatabase(context: Context): FeedbackDatabase?{
            if(INSTANCE==null){
                synchronized(FeedbackDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FeedbackDatabase::class.java,
                        "Feedback_Database"
                    ).allowMainThreadQueries().build()
                }
            }

            return INSTANCE
        }
    }

    abstract fun feedbackDao(): FeedbackDao
}