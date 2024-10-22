package com.feedbackbox.client.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "feedback_table")
data class Feedback(
    @PrimaryKey
    val id: String,

    val name: String,

    val type: String,

    val contents: String
):Parcelable