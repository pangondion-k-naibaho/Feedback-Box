package com.feedbackbox.client.ui.activities.add_feedback

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.feedbackbox.client.R
import com.feedbackbox.client.data.Constants.ITEM.Companion.getListItemDropdown
import com.feedbackbox.client.data.local.FeedbackDatabase
import com.feedbackbox.client.data.model.Feedback
import com.feedbackbox.client.data.model.ItemDropdown
import com.feedbackbox.client.data.repository.FeedbackRepositoryImpl
import com.feedbackbox.client.databinding.ActivityAddEditFeedbackBinding
import com.feedbackbox.client.ui.activities.main.MainActivity
import com.feedbackbox.client.ui.custom_components.InputDropdownView
import com.feedbackbox.client.ui.custom_components.InputTextView
import com.feedbackbox.client.ui.custom_components.PopUpNotificationListener
import com.feedbackbox.client.ui.custom_components.showPopUpNotification
import com.feedbackbox.client.ui.viewmodels.add_feedback.AddFeedbackViewModel
import com.feedbackbox.client.ui.viewmodels.add_feedback.AddFeedbackViewModelFactory
import com.feedbackbox.client.utils.Extensions.Companion.generateId

class AddFeedbackActivity : AppCompatActivity() {
    private val TAG = AddFeedbackActivity::class.java.simpleName
    private lateinit var binding: ActivityAddEditFeedbackBinding
    private lateinit var addFeedbackViewModel: AddFeedbackViewModel

    private var retrievedName: String?= null
    private var retrievedType: String?= null
    private var retrievedContent: String?= null

    companion object{
        fun newIntent(context: Context): Intent = Intent(context, AddFeedbackActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAddFeedback)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val feedbackDatabase = FeedbackDatabase.getDatabase(this)
        val repository = FeedbackRepositoryImpl(feedbackDatabase!!.feedbackDao())
        val viewModelFactory = AddFeedbackViewModelFactory(repository)

        addFeedbackViewModel = ViewModelProvider(this, viewModelFactory).get(AddFeedbackViewModel::class.java)

        observeStatus()

        setUpView()

    }

    private fun observeStatus(){
        addFeedbackViewModel.isLoading.observe(this@AddFeedbackActivity, {
            setLayoutForLoading(it)
        })

        addFeedbackViewModel.isFail.observe(this@AddFeedbackActivity, {
            setLayoutForPopUp(true)
            if(it) {
                showPopUpNotification(
                    textTitle = getString(R.string.popupAddFeedbackFailTitle),
                    textDesc = getString(R.string.popupAddFeedbackFailDesc),
                    backgroundImage = R.drawable.ic_fail,
                    listener = object: PopUpNotificationListener{
                        override fun onPopUpClosed() {
                            setLayoutForPopUp(false)
                        }
                    }
                )
            }else{
                showPopUpNotification(
                    textTitle = getString(R.string.popupAddFeedbackSuccessTitle),
                    textDesc = getString(R.string.popupAddFeedbackSuccessDesc),
                    backgroundImage = R.drawable.ic_success,
                    listener = object: PopUpNotificationListener{
                        override fun onPopUpClosed() {
                            setLayoutForPopUp(false)
                            startActivity(MainActivity.newIntent(this@AddFeedbackActivity))
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            finish()
                        }
                    }
                )
            }
        })
    }

    private fun setUpView(){
        binding.btnDelete.visibility = View.GONE
        binding.btnSave.visibility = View.GONE

        binding.itvName.apply {
            setTitle(getString(R.string.itvTxtNameTitle))
            setTextHelper(getString(R.string.itvTxtNameHint))
            setInputType(InputTextView.INPUT_TYPE.REGULAR)
        }

        binding.idvStatusFeedback.apply {
            setTitle(getString(R.string.idvTxtTypeTitle))
            setHint(getString(R.string.idvTxtTypeHint))
            setData(getListItemDropdown())
            setListener(object: InputDropdownView.DropdownListener{
                override fun onDropdownClicked() {
                    setNormal()
                    binding.itvName.clearingFocus()
                    binding.itvContentFeedback.clearingFocus()
                }

                override fun onItemSelected(
                    position: Int,
                    item: String,
                    selectedData: ItemDropdown
                ) {
                    retrievedType = selectedData.type
                    setText(item)
                    setNormal()
                }

                override fun onDismissPopUp() {
                    if(retrievedType == null){
                        setError()
                    }
                }

            })
        }

        binding.itvContentFeedback.apply {
            setTitle(getString(R.string.itvTxtContentTitle))
            setTextHelper(getString(R.string.itvTxtContentHint))
            setInputType(InputTextView.INPUT_TYPE.MULTILINE)
        }

        binding.btnSubmitFeedback.apply {
            setOnClickListener {
                retrievedName = binding.itvName.getText()
                retrievedContent = binding.itvContentFeedback.getText()

                when{
                    retrievedName.isNullOrEmpty() && retrievedType.isNullOrEmpty() && retrievedContent.isNullOrEmpty() ->{
                        binding.itvName.setOnBlankWarning()
                        binding.idvStatusFeedback.setError()
                        binding.itvContentFeedback.setOnBlankWarning()
                    }
                    !retrievedName.isNullOrEmpty() && retrievedType.isNullOrEmpty() && retrievedContent.isNullOrEmpty() ->{
                        binding.idvStatusFeedback.setError()
                        binding.itvContentFeedback.setOnBlankWarning()
                    }
                    retrievedName.isNullOrEmpty() && !retrievedType.isNullOrEmpty() && retrievedContent.isNullOrEmpty() ->{
                        binding.itvName.setOnBlankWarning()
                        binding.itvContentFeedback.setOnBlankWarning()
                    }
                    retrievedName.isNullOrEmpty() && retrievedType.isNullOrEmpty() && !retrievedContent.isNullOrEmpty() ->{
                        binding.itvName.setOnBlankWarning()
                        binding.idvStatusFeedback.setError()
                    }
                    !retrievedName.isNullOrEmpty() && !retrievedType.isNullOrEmpty() && retrievedContent.isNullOrEmpty() ->{
                        binding.itvContentFeedback.setOnBlankWarning()
                    }
                    retrievedName.isNullOrEmpty() && !retrievedType.isNullOrEmpty() && !retrievedContent.isNullOrEmpty() ->{
                        binding.itvName.setOnBlankWarning()
                    }
                    !retrievedName.isNullOrEmpty() && retrievedType.isNullOrEmpty() && !retrievedContent.isNullOrEmpty() ->{
                        binding.idvStatusFeedback.setError()
                    }
                    else ->{
                        Log.d(TAG, "retrieved: $retrievedName, $retrievedType, $retrievedContent")
                        val feedbackRequest = Feedback(
                            id = generateId(),
                            name = retrievedName!!,
                            type = retrievedType!!,
                            contents = retrievedContent!!
                        )

                        addFeedbackViewModel.saveFeedback(feedbackRequest)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                onBackPressed()
                true
            }
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
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
}