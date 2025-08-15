package com.aliayali.market_baz.presentation.screens.profile

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
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences,
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

    fun loginState(state: Boolean) {
        viewModelScope.launch {
            userPreferences.setLoggedIn(state)
        }
    }

}