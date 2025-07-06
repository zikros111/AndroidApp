package com.taskmaster.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.taskmaster.data.entity.Sphere

@Dao
interface SphereDao {
    @Query("SELECT * FROM spheres ORDER BY `order` ASC")
    fun getAllSpheres(): LiveData<List<Sphere>>

    @Insert
    suspend fun insertSphere(sphere: Sphere): Long

    @Insert
    suspend fun insertSpheres(spheres: List<Sphere>)

    @Update
    suspend fun updateSphere(sphere: Sphere)

    @Delete
    suspend fun deleteSphere(sphere: Sphere)

    @Query("SELECT * FROM spheres WHERE id = :id")
    suspend fun getSphereById(id: Long): Sphere?
}
