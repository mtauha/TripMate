package com.project.tripmate.mainapp.fragments.triprecord.noteandchat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.tripmate.R
import com.project.tripmate.mainapp.fragments.triprecord.TripNote

class TripNoteAdapter(val context: Context, val arrList: ArrayList<NoteData>): RecyclerView.Adapter<TripNoteAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val heading = itemView.findViewById<TextView>(R.id.saveNoteHeading)
        val noteDate = itemView.findViewById<TextView>(R.id.saveNoteDate)
        val noteBody = itemView.findViewById<TextView>(R.id.saveNoteBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.save_item_view, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.heading.text = arrList[position].heading
        holder.noteDate.text = arrList[position].note_date
        holder.noteBody.text = arrList[position].body
    }
}