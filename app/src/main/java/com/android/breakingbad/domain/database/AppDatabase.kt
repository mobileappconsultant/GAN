package com.android.breakingbad.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.breakingbad.domain.database.DBConstants.ROOMTABLECONSTANTS.DATABASE_NAME
import com.android.breakingbad.domain.model.BreakbadCharacterRoomItem


@Database(entities = [BreakbadCharacterRoomItem::class], version = 2)
@TypeConverters(IntDataConverter::class, StringDataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breakingBadDao(): BreakingBadDao

    companion object {

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries()
                .build()
        }
    }
}
