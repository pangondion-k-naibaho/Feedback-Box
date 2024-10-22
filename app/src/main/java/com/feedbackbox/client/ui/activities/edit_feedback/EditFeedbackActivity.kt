package com.feedbackbox.client.ui.activities.edit_feedback

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.feedbackbox.client.R
import com.feedbackbox.client.data.Constants
import com.feedbackbox.client.data.local.FeedbackDatabase
import com.feedbackbox.client.data.model.Feedback
import com.feedbackbox.client.data.model.ItemDropdown
import com.feedbackbox.client.data.repository.FeedbackRepositoryImpl
import com.feedbackbox.client.databinding.ActivityAddEditFeedbackBinding
import com.feedbackbox.client.ui.activities.main.MainActivity
import com.feedbackbox.client.ui.custom_components.InputDropdownView
import com.feedbackbox.client.ui.custom_components.InputTextView
import com.feedbackbox.client.ui.custom_components.PopUpNotificationListener
import com.feedbackbox.client.ui.custom_components.PopUpQuestionListener
import com.feedbackbox.client.ui.custom_components.showPopUpNotification
import com.feedbackbox.client.ui.custom_components.showPopUpQuestion
import com.feedbackbox.client.ui.viewmodels.edit_feedback.EditFeedbackViewModel
import com.feedbackbox.client.ui.viewmodels.edit_feedback.EditFeedbackViewModelFactory
import com.feedbackbox.client.utils.Extensions

class EditFeedbackActivity : AppCompatActivity() {
    private val TAG = EditFeedbackActivity::class.java.simpleName
    private lateinit var binding: ActivityAddEditFeedbackBinding
    private lateinit var deliveredFeedback : Feedback
    private lateinit var editFeedbackViewModel: EditFeedbackViewModel

    private var retrievedName: String?= null
    private var retrievedType: String?= null
    private var retrievedContent: String?= null

    companion object{
        const val DELIVERED_FEEDBACK = "DELIVERED_FEEDBACK"
        fun newIntent(context: Context, deliveredFeedback: Feedback): Intent = Intent(context, EditFeedbackActivity::class.java)
            .putExtra(DELIVERED_FEEDBACK, deliveredFeedback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditFeedbackBinding.inflate(layoutInflater)

        setContentView(binding.root)

        deliveredFeedback = intent.getParcelableExtra(DELIVERED_FEEDBACK)!!

        setSupportActionBar(binding.toolbarAddFeedback)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val feedbackDatabase = FeedbackDatabase.getDatabase(this)
        val repository = FeedbackRepositoryImpl(feedbackDatabase!!.feedbackDao())
        val viewModelFactory = EditFeedbackViewModelFactory(repository)

        editFeedbackViewModel = ViewModelProvider(this, viewModelFactory).get(EditFeedbackViewModel::class.java)

        observeStatus()

        setUpView()
    }

    private fun observeStatus(){
        editFeedbackViewModel.isLoading.observe(this@EditFeedbackActivity, {
            setLayoutForLoading(it)
        })

        editFeedbackViewModel.isFailDelete.observe(this@EditFeedbackActivity, {
            setLayoutForPopUp(true)
            if(it){
                showPopUpNotification(
                    textTitle = getString(R.string.popupDeleteFeedbackFailTitle),
                    textDesc = getString(R.string.popupDeleteFeedbackFailDesc),
                    backgroundImage = R.drawable.ic_fail,
                    listener = object: PopUpNotificationListener {
                        override fun onPopUpClosed() {
                            setLayoutForPopUp(false)
                            closeOptionsMenu()
                        }
                    }
                )
            }else{
                showPopUpNotification(
                    textTitle = getString(R.string.popupDeleteFeedbackSuccessTitle),
                    textDesc = getString(R.string.popupDeleteFeedbackSuccessDesc),
                    backgroundImage = R.drawable.ic_success,
                    listener = object: PopUpNotificationListener {
                        override fun onPopUpClosed() {
                            setLayoutForPopUp(false)
                            closeOptionsMenu()
                            startActivity(
                                MainActivity.newIntent(this@EditFeedbackActivity)
                            )
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            finish()
                        }
                    }
                )
            }
        })

        editFeedbackViewModel.isFailUpdate.observe(this@EditFeedbackActivity, {
            setLayoutForPopUp(true)
            if(it){
                showPopUpNotification(
                    textTitle = getString(R.string.popupUpdateFeedbackFailTitle),
                    textDesc = getString(R.string.popupUpdateFeedbackFailDesc),
                    backgroundImage = R.drawable.ic_fail,
                    listener = object: PopUpNotificationListener {
                        override fun onPopUpClosed() {
                            setLayoutForPopUp(false)
                            closeOptionsMenu()
                        }
                    }
                )
            }else{
                showPopUpNotification(
                    textTitle = getString(R.string.popupUpdateFeedbackSuccessTitle),
                    textDesc = getString(R.string.popupUpdateFeedbackSuccessDesc),
                    backgroundImage = R.drawable.ic_success,
                    listener = object: PopUpNotificationListener {
                        override fun onPopUpClosed() {
                            setLayoutForPopUp(false)
                            closeOptionsMenu()
                            startActivity(
                                MainActivity.newIntent(this@EditFeedbackActivity)
                            )
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            finish()
                        }
                    }
                )
            }
        })
    }

    private fun setUpView(){
        binding.btnSubmitFeedback.visibility = View.GONE

        binding.itvName.apply {
            setTitle(getString(R.string.itvTxtNameTitle))
            setInputType(InputTextView.INPUT_TYPE.REGULAR)
            setTextHelper(getString(R.string.itvTxtNameHint))
            setText(deliveredFeedback.name)
        }

        binding.idvStatusFeedback.apply {
            setTitle(getString(R.string.idvTxtTypeTitle))
            setData(Constants.ITEM.getListItemDropdown())
            setText(deliveredFeedback.type)
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

        binding.btnDelete.apply {
            setOnClickListener {
                setLayoutForPopUp(true)
                showPopUpQuestion(
                    textTitle = getString(R.string.popupDeleteFeedbackTitle),
                    textDesc = getString(R.string.popupDeleteFeedbackDesc),
                    textBtnPositive = getString(R.string.btnTxtYes),
                    textBtnNegative = getString(R.string.btnTxtCancel),
                    listener = object: PopUpQuestionListener{
                        override fun onPostiveClicked() {
                            editFeedbackViewModel.deleteFeedback(deliveredFeedback)
                            closeOptionsMenu()
                        }

                        override fun onNegativeClicked() {
                            setLayoutForPopUp(false)
                            closeOptionsMenu()
                        }
                    }
                )
            }
        }

        binding.btnUpdate.apply {
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

                        editFeedbackViewModel.saveEditedFeedback(
                            deliveredFeedback.id,
                            retrievedName!!,
                            retrievedType!!,
                            retrievedContent!!
                        )
                    }
                }
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
}