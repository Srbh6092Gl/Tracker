package com.srbh.track.service

import android.app.Service
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.IBinder

class UpdateContentProviderService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val newTopic = intent!!.getStringExtra("topic")
        val newDescription = intent!!.getStringExtra("description")
        val id = intent!!.getLongExtra("id",0)
        val URL = intent!!.getStringExtra("url")
        val CONTENT_URI = Uri.parse(URL)


        var contentValues= ContentValues()
        contentValues.put("topic",newTopic)
        contentValues.put("description",newDescription)

        val selectionArgs = arrayOf(id.toString())

        contentResolver.update(
            CONTENT_URI,
            contentValues,
            "id =? ",
            selectionArgs
        )
        stopSelf()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}