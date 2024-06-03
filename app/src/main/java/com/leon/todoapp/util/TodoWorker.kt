package com.leon.todoapp.util

import android.app.Activity
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class TodoWorker(val context: Context,
                 val params: WorkerParameters,
                 val activity:Activity)
    : Worker(context,params) {

        override fun doWork(): Result{
            NotificationHelper(context,activity)
                .createNotification(
                    inputData.getString("title").toString(),
                    inputData.getString("message").toString()
                )
            return Result.success()
        }
}