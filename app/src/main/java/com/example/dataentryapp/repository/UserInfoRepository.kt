package com.example.dataentryapp.repository

import com.example.dataentryapp.room.DataEntryDao
import com.example.dataentryapp.room.UserInfoDatabase
import com.example.dataentryapp.room.UserInfoEntity
import kotlinx.coroutines.flow.Flow

class UserInfoRepository(private val dataEntryDao: DataEntryDao) {

    /** Please Note : Room executes all queries on a separate thread.
    Observed Flow will notify the observer when the data has changed.
    **/
    var allUsers: Flow<List<UserInfoEntity>> = dataEntryDao.getAllUsers()

    suspend fun insert(userInfo: UserInfoEntity) {
        dataEntryDao.insert(userInfo)
    }

   suspend fun deleteById(u_id : Int){
        dataEntryDao.deleteById(u_id)
    }
}