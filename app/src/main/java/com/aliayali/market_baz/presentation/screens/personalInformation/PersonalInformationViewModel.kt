package com.aliayali.market_baz.presentation.screens.personalInformation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInformationViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _user = mutableStateOf<UserEntity?>(null)
    val user: State<UserEntity?> = _user
    private var _phone = mutableStateOf("")

    init {
        viewModelScope.launch {
            userPreferences.phoneNumber.collect { phoneNumber ->
                phoneNumber?.let {
                    _phone.value = it
                    getUserByPhone(it)
                }
            }
        }
    }

    fun getUserByPhone(phone: String) {
        viewModelScope.launch {
            val result = userRepository.getUserByPhone(phone)
            if (result != null) {
                _user.value = result
            } else {
                _user.value = null
            }
        }
    }

    fun changeName(name: String) {
        val currentUser = _user.value ?: return
        viewModelScope.launch {
            val updatedUser = currentUser.copy(name = name)
            userRepository.updateUser(updatedUser)
            _user.value = updatedUser
        }
    }

    fun changePassword(password: String) {
        val currentUser = _user.value ?: return
        viewModelScope.launch {
            val updatedUser = currentUser.copy(password = password)
            userRepository.updateUser(updatedUser)
            _user.value = updatedUser
        }
    }

    fun changeImage(image: Int) {
        val currentUser = _user.value ?: return
        viewModelScope.launch {
            val updatedUser = currentUser.copy(image = image)
            userRepository.updateUser(updatedUser)
            _user.value = updatedUser
        }
    }

}