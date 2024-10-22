package com.feedbackbox.client.ui.activities.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.feedbackbox.client.R
import com.feedbackbox.client.data.local.FeedbackDatabase
import com.feedbackbox.client.data.model.Feedback
import com.feedbackbox.client.data.repository.FeedbackRepositoryImpl
import com.feedbackbox.client.databinding.ActivityMainBinding
import com.feedbackbox.client.ui.activities.add_feedback.AddFeedbackActivity
import com.feedbackbox.client.ui.activities.edit_feedback.EditFeedbackActivity
import com.feedbackbox.client.ui.rv_adapters.ItemFeedbackAdapter
import com.feedbackbox.client.ui.viewmodels.main.MainViewModel
import com.feedbackbox.client.ui.viewmodels.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var mainViewModel: MainViewModel

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

        val feedbackDatabase = FeedbackDatabase.getDatabase(this)
        val repository = FeedbackRepositoryImpl(feedbackDatabase!!.feedbackDao())
        val viewModelFactory = MainViewModelFactory(repository)

        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        observeStatus()

        setUpView()
    }

    private fun observeStatus(){
        mainViewModel.isLoading.observe(this@MainActivity,{
            setLayoutForLoading(it)
        })

        mainViewModel.isFail.observe(this@MainActivity, {
            if(it) Toast.makeText(this@MainActivity, "Failed to retrieve data...", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setUpView(){
        mainViewModel.getAllFeedback()

        mainViewModel.feedbacksCollection.observe(this@MainActivity, {collection ->
            if(collection.size == 0 || collection.isNullOrEmpty()){
                binding.rvFeedback.visibility = View.GONE
                binding.clEmptyData.visibility = View.VISIBLE
            }else{
                binding.clEmptyData.visibility = View.GONE
                binding.rvFeedback.apply {
                    visibility = View.VISIBLE

                    val rvAdapter = ItemFeedbackAdapter(
                        data = collection.toMutableList(),
                        listener = object: ItemFeedbackAdapter.ItemListener{
                            override fun onEditFeedback(item: Feedback) {
                                startActivity(
                                    EditFeedbackActivity.newIntent(this@MainActivity, item)
                                )
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            }
                        }
                    )

                    val rvLayoutManager = LinearLayoutManager(this@MainActivity)

                    adapter = rvAdapter
                    layoutManager = rvLayoutManager
                }
            }
        })
    }

    private fun setLayoutForLoading(isLoading: Boolean){
        if(isLoading){
            binding.loadingLayout.visibility = View.VISIBLE
            binding.pbLoading.visibility = View.VISIBLE
        }else{
            binding.loadingLayout.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun setLayoutForPopUp(isShown: Boolean){
        if(isShown){
            binding.loadingLayout.visibility = View.VISIBLE
            binding.pbLoading.visibility = View.GONE
        }
        else{
            binding.loadingLayout.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE
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