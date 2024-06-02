package com.leon.todoapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leon.todoapp.util.DB_NAME
import com.leon.todoapp.util.MIGRATION_1_2
import com.leon.todoapp.util.MIGRATION_2_3

@Database(entities = arrayOf(Todo::class), version = 3)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object{
        @Volatile private var instance: TodoDatabase ?= null
        private val LOCK = Any()

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                DB_NAME)
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()

        operator fun invoke(context: Context){
            if(instance!=null){
                synchronized(LOCK){
                    instance ?: buildDatabase(context).also{
                        instance = it
                    }
                }
            }
        }

//        val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER NOT NULL DEFAULT 0")
//            }
//        }
    }
}