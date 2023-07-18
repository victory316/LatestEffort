package com.choidev.latesteffort.data.core.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.choidev.latesteffort.data.core.model.BookmarkedResult
import com.choidev.latesteffort.data.core.model.SearchResult
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Query(value = "SELECT * FROM search_result")
    fun getSearchResults(): Flow<List<SearchResult>>

    @Query(value = "SELECT * FROM bookmarked_result")
    fun getBookmarkedResults(): Flow<List<BookmarkedResult>>

    @Upsert
    suspend fun upsertSearchResult(entities: List<SearchResult>)

    @Query(value = "DELETE FROM search_result")
    suspend fun clearSearchResult()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookmarked(entity: BookmarkedResult)

    @Delete
    suspend fun removeBookmarked(entity: BookmarkedResult)
}
