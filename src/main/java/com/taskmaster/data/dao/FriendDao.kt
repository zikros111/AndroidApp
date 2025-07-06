package com.taskmaster.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.taskmaster.data.entity.Friend

@Dao
interface FriendDao {
    @Query("SELECT * FROM friends WHERE userId = :userId AND status = 'accepted'")
    fun getFriends(userId: Long): LiveData<List<Friend>>

    @Query("SELECT * FROM friends WHERE friendId = :userId AND status = 'pending'")
    fun getFriendRequests(userId: Long): LiveData<List<Friend>>

    @Insert
    suspend fun insertFriend(friend: Friend): Long

    @Update
    suspend fun updateFriend(friend: Friend)

    @Query("UPDATE friends SET status = 'accepted' WHERE id = :friendId")
    suspend fun acceptFriend(friendId: Long)

    @Query("DELETE FROM friends WHERE userId = :userId AND friendId = :friendId")
    suspend fun removeFriend(userId: Long, friendId: Long)
}
