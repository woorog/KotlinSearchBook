package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.Dao.HistoryDao
import com.example.myapplication.Dao.ReviewDao
import com.example.myapplication.model.History
import com.example.myapplication.model.Review


@Database(entities = [History::class, Review::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        private var instance: AppDatabase? = null

        val migration_1_2 = object: Migration(1, 2) { //
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `REVIEW` (`id` INTEGER, `review` TEXT," + " PRIMARY KEY(`id`))")
            }
        }

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            "BookSearchDB"
                        ).addMigrations(migration_1_2)
                            .build()
                    }
                }
            }
            return instance
        }

    }
}