package com.application.quickcook.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.application.quickcook.data.pojo.MealDB
import com.application.quickcook.data.pojo.MealDetail

@Database(entities = [MealDB::class], version = 6)
abstract class MealsDatabase : RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: MealsDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MealsDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealsDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    }

