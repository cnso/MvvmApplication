package org.jash.mvvmapplication.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.jash.mvvmapplication.model.User

@Dao
interface UserDao {
    @Query("select * from user")
    fun getAll():List<User>
    @Upsert
    fun insert(user: User)
}