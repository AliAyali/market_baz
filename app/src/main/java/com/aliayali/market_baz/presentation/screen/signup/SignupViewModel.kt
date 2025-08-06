package com.aliayali.market_baz.presentation.screen.signup

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
class SignupViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private var _user = mutableStateOf<UserEntity?>(null)
    val user: State<UserEntity?> = _user
    private var _error = mutableStateOf("")
    var error: State<String> = _error

    fun insertUser(phone: String, name: String, password: String) {
        viewModelScope.launch {
            repository.insertUser(UserEntity(phone, name, password))
        }
    }

    fun getDataByPhone(phone: String) {
        viewModelScope.launch {
            _user.value = repository.getUserByPhone(phone)
        }
    }

    fun setError(error: String) {
        _error.value = error
    }

    fun savePhone(phone: String) {
        viewModelScope.launch {
            userPreferences.savePhoneNumber(phone)
        }
    }

}