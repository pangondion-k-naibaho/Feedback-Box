package com.feedbackbox.client.utils

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

class Extensions {
    companion object{
        fun Int.dpToPx(context: Context): Int {
            val scale = context.resources.displayMetrics.density
            return (this * scale + 0.5f).toInt()
        }

        fun generateId(): String {
            // Format awal yang diinginkan
            val prefix = "fbox"

            // Mendapatkan waktu saat ini dalam format "ddMMyyyyHHmm"
            val currentTime = SimpleDateFormat("ddMMyyyyHHmm").format(Date())

            // Menghasilkan angka random sebanyak 9 digit
            val randomNumber = Random.nextLong(100_000_000L, 999_999_999L)

            // Menggabungkan semua komponen
            return "$prefix-$currentTime-$randomNumber"
        }

        fun String.capitalizeFirstLetter(): String {
            return if (this.isNotEmpty()) {
                this[0].uppercaseChar() + this.substring(1).lowercase()
            } else {
                this
            }
        }
    }
}