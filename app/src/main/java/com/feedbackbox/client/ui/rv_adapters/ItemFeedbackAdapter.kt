package com.feedbackbox.client.ui.rv_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.feedbackbox.client.R
import com.feedbackbox.client.data.Constants.TYPE.Companion.TYPE_CRITIQUE
import com.feedbackbox.client.data.Constants.TYPE.Companion.TYPE_SUGGESTION
import com.feedbackbox.client.data.model.Feedback
import com.feedbackbox.client.databinding.ItemFeedbackLayoutBinding

class ItemFeedbackAdapter(
    var data: MutableList<Feedback>,
    private val listener: ItemListener
): RecyclerView.Adapter<ItemFeedbackAdapter.ItemHolder>(){
    interface ItemListener{
        fun onEditFeedback(item: Feedback)
    }

    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = ItemFeedbackLayoutBinding.bind(itemView)
        fun bind(item: Feedback, listener: ItemListener) = with(itemView){
            binding.apply {
                tvName.text = item.name
                when(item.type){
                    TYPE_CRITIQUE -> tvCoS.setTextColor(ContextCompat.getColor(itemView.context, R.color.puppeteers))
                    TYPE_SUGGESTION -> tvCoS.setTextColor(ContextCompat.getColor(itemView.context, R.color.megaman))
                }
                tvCoS.text = item.contents

                btnEditFeedback.setOnClickListener {
                    listener.onEditFeedback(item)
                }

                if(item == data.get(data.size-1)) ivCircle2.visibility = View.VISIBLE else ivCircle2.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_feedback_layout, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(data.get(position), listener)
    }
}