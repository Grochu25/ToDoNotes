package com.example.todonotes

import android.Manifest
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todonotes.repositories.Category
import com.example.todonotes.repositories.NotesDatabase
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NotesMain())
                .commit()
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

//        supportActionBar?.title = "BackArrowToolbar"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        createNotificationChannel()


        Dependencies.noteDao = NotesDatabase.getInstance(this).noteDao()
        Dependencies.categoryDao = NotesDatabase.getInstance(this).categoryDao()


        if(Dependencies.categoryDao.getAll().isEmpty())
            Dependencies.categoryDao.insertAll(Category(0, "Szkoła", Color.RED))
        val kategorie: List<Category> = Dependencies.categoryDao.getAll()

    }
    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

    fun setBackArrowVisibility(visible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    fun updateToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
            } else {
            }
        }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun addNotification(title: String, time: Instant)
    {

        when {
            (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.USE_EXACT_ALARM
            )+ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS)) == PackageManager.PERMISSION_GRANTED -> {

                val intent = Intent("com.example.todonotes.ALARM_ACTION").apply {
                    setPackage("com.example.todonotes")
                }

                intent.putExtra(NOTIFICATION_ID, Instant.now().toEpochMilli().toInt())
                intent.putExtra(NOTIFICATION_TITLE, "Przypomnienie")
                intent.putExtra(NOTIFICATION_MESSAGE, title)
                var pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

                var alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    time.toEpochMilli(),
                    pendingIntent)

                val alarmUp = (PendingIntent.getBroadcast(
                    applicationContext, 0,
                    Intent("com.example.todonotes.ALARM_ACTION"),
                    PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE) != null)

            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.USE_EXACT_ALARM) -> {
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.USE_EXACT_ALARM)
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }}
    }
}
