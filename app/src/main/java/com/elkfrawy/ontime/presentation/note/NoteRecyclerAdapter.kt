package com.elkfrawy.ontime.presentation.note

import android.view.LayoutInflater
import android.view.View.*
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elkfrawy.ontime.databinding.NoteCardBinding
import com.elkfrawy.ontime.domain.model.Note
import com.elkfrawy.ontime.presentation.util.ViewUtil.hide
import com.elkfrawy.ontime.presentation.util.ViewUtil.show
import java.text.DateFormat

class NoteRecyclerAdapter : ListAdapter<Note, NoteRecyclerAdapter.NoteViewHolder>(DiffCallback()){

    lateinit var mListener:OnItemClick


    class NoteViewHolder(val binding: NoteCardBinding):RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note = currentList[position]

        if (note.title.isNotBlank()) {

            holder.binding.apply {
                noteTitle.show()
                noteTitle.text = note.title
            }
        } else holder.binding.noteTitle.hide()


        if (note.text.isNotBlank()){
            holder.binding.apply {
                noteText.show()
                noteText.text = note.text
            }
        }else holder.binding.noteText.hide()


        if (note.pin) holder.binding.pinNote.visibility = VISIBLE else holder.binding.pinNote.visibility = INVISIBLE

        val formatDate = DateFormat.getDateInstance().format(note.date)
        holder.binding.noteDate.text = formatDate

        holder.binding.root.setOnClickListener{
            mListener.onItemClickListener(note)
        }

    }


    private class DiffCallback: DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }


        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    fun setOnItemClickListener(listener:OnItemClick){
        mListener = listener
    }

    interface OnItemClick{
        fun onItemClickListener(note: Note)
    }
}