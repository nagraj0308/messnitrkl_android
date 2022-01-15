package com.nagraj.messnitrkl.select

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nagraj.messnitrkl.R

class SearchSelectAdapter(
    private val dataList: ArrayList<String>,
    private val mListener: (Int) -> Unit
) :

    RecyclerView.Adapter<SearchSelectAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_search_select, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvData.text = "I will do " + dataList[position]
        holder.tvData.setOnClickListener {
            mListener.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var tvData: TextView = itemView.findViewById(R.id.tv_data)
    }
}
