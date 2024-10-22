package com.feedbackbox.client.ui.activities.edit_feedback

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.feedbackbox.client.R
import com.feedbackbox.client.data.model.Feedback
import com.feedbackbox.client.databinding.ActivityAddEditFeedbackBinding

class EditFeedbackActivity : AppCompatActivity() {
    private val TAG = EditFeedbackActivity::class.java.simpleName
    private lateinit var binding: ActivityAddEditFeedbackBinding

    companion object{
        const val DELIVERED_FEEDBACK = "DELIVERED_FEEDBACK"
        fun newIntent(context: Context, deliveredFeedback: Feedback): Intent = Intent(context, EditFeedbackActivity::class.java)
            .putExtra(DELIVERED_FEEDBACK, deliveredFeedback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}