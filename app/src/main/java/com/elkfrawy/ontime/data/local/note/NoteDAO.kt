package com.elkfrawy.ontime.data.local.note

import  androidx.room.*
import com.elkfrawy.ontime.domain.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("select * from note ORDER BY pin DESC")
    fun getNotes(): Flow<List<Note>>

    @Query("select * from note WHERE title LIKE '%' || :text || '%' ORDER BY pin DESC")
    fun getNotes(text:String): Flow<List<Note>>

}