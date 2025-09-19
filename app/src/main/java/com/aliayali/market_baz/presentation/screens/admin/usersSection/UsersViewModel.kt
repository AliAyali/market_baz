package com.aliayali.market_baz.presentation.screens.admin.usersSection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _users = MutableStateFlow<List<UserEntity>>(emptyList())
    val users: StateFlow<List<UserEntity>> = _users

    private var allUsers: List<UserEntity> = emptyList()

    fun loadAllUsers() {
        viewModelScope.launch {
            val usersList = userRepository.getAllUsers()
            allUsers = usersList
            _users.value = usersList
        }
    }

    fun getUserByPhone(phone: String, onResult: (UserEntity?) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserByPhone(phone)
            onResult(user)
        }
    }

    fun searchUsers(query: String) {
        _users.value = if (query.isBlank()) {
            allUsers
        } else {
            allUsers.filter {
                it.name?.contains(query, ignoreCase = true) == true ||
                        it.phone.contains(query)
            }
        }
    }
}