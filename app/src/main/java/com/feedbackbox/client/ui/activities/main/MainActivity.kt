package com.feedbackbox.client.ui.activities.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.feedbackbox.client.R
import com.feedbackbox.client.databinding.ActivityMainBinding
import com.feedbackbox.client.ui.activities.add_feedback.AddFeedbackActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName

    companion object{
        fun newIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMain)

        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menuAddFeedback -> {
                //Method will be added later
                startActivity(
                    AddFeedbackActivity.newIntent(this@MainActivity)
                )
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}