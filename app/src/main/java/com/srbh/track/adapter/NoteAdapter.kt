package com.srbh.track.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.srbh.track.R
import com.srbh.track.databinding.ItemNoteBinding
import com.srbh.track.model.Note
import com.srbh.track.view.UpdateActivity

class NoteAdapter: RecyclerView.Adapter<NoteViewHolder>() {

    private val noteList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemNoteBinding= DataBindingUtil.inflate(layoutInflater, R.layout.item_note,parent,false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position])

        holder.binding.editButton.setOnClickListener{
            Intent(holder.binding.root.context,UpdateActivity::class.java).also {
                it.putExtra("id",noteList[position].id)
                it.putExtra("topic",noteList[position].topic)
                it.putExtra("description",noteList[position].description)
                it.putExtra("sender",noteList[position].sender)
                holder.binding.root.context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int = noteList.size

    fun setList(list: List<Note>?) {
        noteList.clear()
        noteList.addAll(list!!)
    }
}

class NoteViewHolder(val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(note: Note) {
        binding.from.text = note.sender
        binding.itemTopic.text = note.topic
        binding.itemDescription.text = note.description
    }
}