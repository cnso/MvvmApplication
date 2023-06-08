package org.jash.mvvmapplication.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.reactivex.rxjava3.core.Maybe
import org.jash.mvvmapplication.model.Category

@Dao
interface CategoryDao {
    @Query("select * from category")
    fun getAll():Maybe<List<Category>>
    @Query("select * from category where parentId = :parentId")
    fun findCategoryByParentId(parentId:Int):Maybe<List<Category>>
    @Query("select * from category where id = :id")
    fun findCategoryById(id:Int):Maybe<Category>
    @Upsert
    fun insert(category: Category)
}