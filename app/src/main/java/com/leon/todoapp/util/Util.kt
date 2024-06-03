package com.leon.todoapp.util

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leon.todoapp.R
import com.leon.todoapp.model.TodoDatabase
import com.leon.todoapp.view.MainActivity

val DB_NAME = "newtododb"

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 not null"
        )
    }
}

val MIGRATION_2_3 = object : Migration(2,3){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER default 0 not null")
    }
}

fun buildDb(context: Context):TodoDatabase{
    //val db = Room.databaseBuilder(context,TodoDatabase::class.java, DB_NAME).build()
    val db = TodoDatabase.buildDatabase(context)
    return db
}

class NotificationHelper(val context:Context, val activity: Activity){
    private val CHANNEL_ID = "todo_app"
    private val NOTIFICATION_ID = 1

    companion object{
        val REQUEST_NOTIF = 100
    }
    fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_ID, "TODOCHANNEL",NotificationManager.IMPORTANCE_DEFAULT)
                .apply { description = "Create Todo Notif" }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun createNotification(title: String, message: String){
        createNotificationChannel()
        val intent = Intent(context,MainActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE)
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.todochar)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.checklist)
            .setLargeIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(icon)
                    .bigLargeIcon(null)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

            if(ActivityCompat.checkSelfPermission(context,Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),REQUEST_NOTIF)
                return
            } else {
                NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
            }
    }
}