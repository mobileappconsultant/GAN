package com.android.breakingbad.presentation.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem
import com.jakewharton.rxbinding4.view.clicks

class BreakingBadItemsAdapter(
    val context: Context,
    val adapterOnClick: (ClickActions) -> Unit
) :
    RecyclerView.Adapter<BreakingBadItemsAdapter.BreakingBadItemViewHolder>() {

    private var data = mutableListOf<BreakbadCharacterRoomItem?>()
    fun updateData(newData: List<BreakbadCharacterRoomItem?>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class BreakingBadItemViewHolder(val breakingBadActorView: BreakingBadActorView) :
        RecyclerView.ViewHolder(breakingBadActorView) {
        fun bind(breakbadCharacterRoomItem: BreakbadCharacterRoomItem?) {
            breakbadCharacterRoomItem?.let { breakingBadActorView.setBreakingBadData(it) }
            breakingBadActorView.clicks()
                .subscribe {
                    adapterOnClick.invoke(
                        ClickActions.MainViewClickAction(
                            breakbadCharacterRoomItem!!
                        )
                    )
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreakingBadItemViewHolder {
        return BreakingBadItemViewHolder(BreakingBadActorView(context))
    }

    override fun onBindViewHolder(holder: BreakingBadItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

}
