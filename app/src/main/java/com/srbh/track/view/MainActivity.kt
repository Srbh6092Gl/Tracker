package com.srbh.track.view

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.srbh.track.R
import com.srbh.track.adapter.NoteAdapter
import com.srbh.track.databinding.ActivityMainBinding
import com.srbh.track.model.Note

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var list: List<Note>
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    val PROVIDER_NAME = "com.srbh.note.provider"
    val URL = "content://$PROVIDER_NAME/note"
    val CONTENT_URI = Uri.parse(URL)
    val REFRESH_TIME = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.notesRv.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter()
        binding.notesRv.adapter=noteAdapter

        binding.refreshButton.setOnClickListener{
            Toast.makeText(this,"Auto-refresh is carried out every 5s",Toast.LENGTH_LONG).show()
            refesh()
        }
        handler= Handler()
        runnable = Runnable(){
            refesh()
            handler.postDelayed(runnable,REFRESH_TIME)
        }

    }

    override fun onResume() {
        super.onResume()
        refesh()
        handler.postDelayed(runnable,REFRESH_TIME)
    }

    private fun refesh() {
        list = getList()
        noteAdapter.setList(list)
        noteAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("Range")
    fun getList(): List<Note>{
        var list = ArrayList<Note>()
        val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val note = Note(
                    id = cursor.getLong(cursor.getColumnIndex("id")),
                    sender = cursor.getString(cursor.getColumnIndex("sender")),
                    topic = cursor.getString(cursor.getColumnIndex("topic")),
                    description = cursor.getString(cursor.getColumnIndex("description")),
                )
                list.add(note)
                Log.i("MainActivity", "getList: $note")
                cursor.moveToNext()
            }
        }
        return list
    }

}