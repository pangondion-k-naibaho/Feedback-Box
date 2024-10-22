package com.feedbackbox.client.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemDropdown(
    var id: String?= "",
    var name: String?= "",
    var type: String?= "",
    var isSelected: Boolean = false
): Parcelable