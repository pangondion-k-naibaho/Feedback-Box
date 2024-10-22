package com.feedbackbox.client.data

import com.feedbackbox.client.data.model.ItemDropdown

class Constants {
    interface TYPE{
        companion object{
            const val TYPE_CRITIQUE = "CRITIQUE"
            const val TYPE_SUGGESTION = "SUGGESTION"
        }
    }

    interface ITEM{
        companion object{
            fun getListItemDropdown(): ArrayList<ItemDropdown> = arrayListOf(
                ItemDropdown("001", name = "Critique", type = "CRITIQUE"),
                ItemDropdown("002", name = "Suggestion", type = "SUGGESTION"),
            )
        }
    }
}