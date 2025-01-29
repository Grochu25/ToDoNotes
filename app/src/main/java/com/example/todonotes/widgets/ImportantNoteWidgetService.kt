package com.example.todonotes.widgets

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.annotation.RequiresApi
import com.example.todonotes.Dependencies
import com.example.todonotes.MainActivity
import com.example.todonotes.R
import com.example.todonotes.repositories.Note
import com.example.todonotes.repositories.NotesDatabase
import com.example.todonotes.repositories.Priority
import java.time.format.DateTimeFormatter

class ImportantNoteWidgetService : RemoteViewsService()
{
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return ListRemoteViewsFactory(this.applicationContext, intent)
    }

}

class ListRemoteViewsFactory(
    private val context: Context,
    intent: Intent
) : RemoteViewsService.RemoteViewsFactory {

    private lateinit var notes: List<Note>

    override fun onCreate() {
        notes = NotesDatabase.getInstance(context).noteDao().getAllByPriority(Priority.WYSOKA)
        Log.v("mango V2",notes.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getViewAt(p0: Int): RemoteViews {
        return RemoteViews(context.packageName, R.layout.widget_item).apply {
            setTextViewText(R.id.widget_item_title, notes[p0].title)
            setTextViewText(R.id.widget_item_time, notes[p0].date!!.format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")))
            val fillInIntent = Intent().apply{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            setOnClickFillInIntent(R.id.widget_item,fillInIntent)
        }
    }

    override fun onDataSetChanged() {

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return notes.count()
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}