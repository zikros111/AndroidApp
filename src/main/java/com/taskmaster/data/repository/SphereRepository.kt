package com.taskmaster.data.repository

import androidx.lifecycle.LiveData
import com.taskmaster.data.dao.SphereDao
import com.taskmaster.data.entity.Sphere
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SphereRepository @Inject constructor(
    private val sphereDao: SphereDao
) {
    fun getAllSpheres(): LiveData<List<Sphere>> = sphereDao.getAllSpheres()

    suspend fun insertSphere(sphere: Sphere): Long = sphereDao.insertSphere(sphere)

    suspend fun updateSphere(sphere: Sphere) = sphereDao.updateSphere(sphere)

    suspend fun deleteSphere(sphere: Sphere) = sphereDao.deleteSphere(sphere)

    suspend fun addXpToSphere(sphereId: Long, xp: Int) {
        // Простая реализация - можно улучшить позже
    }
}
