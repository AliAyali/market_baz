package com.aliayali.market_baz.presentation.screens.user

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
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user

    fun getUserByPhone(phone: String) {
        viewModelScope.launch {
            val fetchedUser = userRepository.getUserByPhone(phone)
            _user.value = fetchedUser
        }
    }

    fun deleteUser(phone: String, onDeleted: (() -> Unit)? = null) {
        viewModelScope.launch {
            val fetchedUser = userRepository.getUserByPhone(phone)
            fetchedUser?.let {
                userRepository.deleteUserByPhone(it.phone)
            }
            _user.value = null
            onDeleted?.invoke()
        }
    }


    fun toggleAdminStatus(phone: String) {
        viewModelScope.launch {
            val u = userRepository.getUserByPhone(phone) ?: return@launch
            val updatedUser = u.copy(isAdmin = !u.isAdmin)
            userRepository.updateUser(updatedUser)
            _user.value = updatedUser
        }
    }


    fun updateUser(userEntity: UserEntity, onUpdated: (() -> Unit)? = null) {
        viewModelScope.launch {
            userRepository.updateUser(userEntity)
            _user.value = userEntity
            onUpdated?.invoke()
        }
    }
}
