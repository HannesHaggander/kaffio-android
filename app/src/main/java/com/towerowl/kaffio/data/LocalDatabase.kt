package com.towerowl.kaffio.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.towerowl.kaffio.data.daos.UserDao
import com.towerowl.kaffio.enums.Roles
import java.util.*

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseTypeConverters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

object DatabaseTypeConverters {

    @JvmStatic
    @TypeConverter
    fun fromUUID(uuid: UUID): String = uuid.toString()

    @JvmStatic
    @TypeConverter
    fun toUUID(uuid: String): UUID = UUID.fromString(uuid)

    @JvmStatic
    @TypeConverter
    fun fromRoles(role: Roles): String = role.name

    @JvmStatic
    @TypeConverter
    fun toRoles(role: String): Roles = Roles.valueOf(role)
}