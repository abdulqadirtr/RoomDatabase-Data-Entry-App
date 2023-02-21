package com.example.dataentryapp.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserInfoDatabaseTest : TestCase(){

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userInfoDatabase: UserInfoDatabase
    private lateinit var userInfoDao: DataEntryDao

    @Before
    public override fun setUp() {
        userInfoDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), UserInfoDatabase::class.java).allowMainThreadQueries().build()

        userInfoDao = userInfoDatabase.dataEntryDao()
    }

    @After
    fun closeDb(){
        userInfoDatabase.close()
    }

    @Test
    fun writeAndReadTest(){
        runBlocking {
            val xkcdRespons = UserInfoEntity(1,"Test", "Test")
            userInfoDao.insert(xkcdRespons)
            val xkcd = userInfoDao.getAllUsers().asLiveData()
            xkcd.observeForever {
                assertEquals(it.contains(xkcdRespons), true)
            }
        }
    }

    @Test
    fun deleteTest(){
        runBlocking {
            val xkcdRespons = UserInfoEntity(1,"Test", "Test")
            userInfoDao.insert(xkcdRespons)
            userInfoDao.deleteById(1)
            val xkcd = userInfoDao.getAllUsers().asLiveData()
            xkcd.observeForever {
                assertEquals(it.isNullOrEmpty(), true)
            }


        }
    }
}