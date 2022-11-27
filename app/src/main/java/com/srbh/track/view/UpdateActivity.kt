package com.srbh.track.view

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.srbh.track.R
import com.srbh.track.databinding.ActivityUpdateBinding
import com.srbh.track.service.UpdateContentProviderService

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    val PROVIDER_NAME = "com.srbh.note.provider"
    val URL = "content://$PROVIDER_NAME/note"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_update)

        val id = intent.getLongExtra("id",0)
        val topic = intent.getStringExtra("topic").toString()
        val description = intent.getStringExtra("description").toString()

        binding.topic.setText(topic)
        binding.description.setText(description)

        binding.backButton.setOnClickListener{
            goBackToMainActivity()
        }

        binding.updateNoteButton.setOnClickListener{
            val newTopic = binding.topic.text.toString()
            val newDescription = binding.description.text.toString()
            updateNote(id,newTopic,newDescription)
            goBackToMainActivity()
        }

    }

    private fun updateNote(id: Long, newTopic: String, newDescription: String) =

        Intent(this, UpdateContentProviderService::class.java).also {
            it.putExtra("id",id)
            it.putExtra("topic",newTopic)
            it.putExtra("description",newDescription)
            it.putExtra("url",URL)
            startService(it)
        }

    private fun goBackToMainActivity() = Intent(this, MainActivity::class.java).also{
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(it)
        finish()
    }
}