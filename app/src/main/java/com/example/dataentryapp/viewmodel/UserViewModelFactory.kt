package com.example.dataentryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.dataentryapp.repository.UserInfoRepository

/**
 * If you are creating a new project by your own and you have a problem with the ViewModelFactory
 * please add this line in build.gradle Module level inside android{} block kotlinOptions {
freeCompilerArgs += [
"-Xjvm-default=all",
]
}
 */
class UserViewModelFactory(private val repository: UserInfoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}