package com.example.todonotes

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.time.Instant

const val CHANNEL_ID = "ToDoNotesChannelID"
const val NOTIFICATION_ID = "noteId"
const val NOTIFICATION_TITLE = "notifTitle"
const val NOTIFICATION_MESSAGE = "notifMessage"

class Notifications : BroadcastReceiver()
{
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(NOTIFICATION_ID, 1)
        val message = intent.getStringExtra(NOTIFICATION_MESSAGE).toString()
        val title = intent.getStringExtra(NOTIFICATION_TITLE).toString()

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE)

        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(R.drawable.full_star)
                .setContentText(message).setContentTitle(title).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent).setAutoCancel(true).build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id, notification)
    }
}