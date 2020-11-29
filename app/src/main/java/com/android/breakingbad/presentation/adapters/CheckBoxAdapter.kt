package com.android.breakingbad.presentation.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.android.breakingbad.R
import com.jakewharton.rxbinding4.widget.checkedChanges

class CheckBoxAdapter(
    val adapterOnClick: (ClickActions) -> Unit
) :
    RecyclerView.Adapter<CheckBoxAdapter.CheckBoxViewHolder>() {

    private var data = mutableListOf<Int>()
    fun updateData(newData: MutableList<Int>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class CheckBoxViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val checkBox = itemView.findViewById<CheckBox>(R.id.checkBox_select)
            checkBox.text = "Season $position"
            checkBox.checkedChanges().subscribe {
                adapterOnClick.invoke(ClickActions.CheckboxClickAction(position))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckBoxViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.checkboxholder,
            parent,
            false
        )
        return CheckBoxViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CheckBoxViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

}
