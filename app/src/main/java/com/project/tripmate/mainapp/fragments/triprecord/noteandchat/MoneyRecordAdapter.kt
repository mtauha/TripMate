package com.project.tripmate.mainapp.fragments.triprecord.noteandchat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.tripmate.R

class MoneyRecordAdapter(val context: Context, val arrList: ArrayList<NoteData>): RecyclerView.Adapter<MoneyRecordAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val spentMoney = itemView.findViewById<TextView>(R.id.previousMoneyRecordAmount)
        val spentReason = itemView.findViewById<TextView>(R.id.previousMoneyRecordReason)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.spent_money_view, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.spentMoney.text = arrList[position].heading
        holder.spentReason.text = arrList[position].note_date
    }
}