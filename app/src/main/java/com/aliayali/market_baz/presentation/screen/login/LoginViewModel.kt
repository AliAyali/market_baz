package com.aliayali.market_baz.presentation.screen.login

import androidx.compose.runtime.MutableState
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
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _user = mutableStateOf<UserEntity?>(null)
    val user: State<UserEntity?> = _user

    private var _error = mutableStateOf("")
    val error: State<String> = _error

    private var _progress = mutableStateOf(false)
    val progress: MutableState<Boolean> = _progress

    fun getUserByPhone(phone: String) {
        viewModelScope.launch {
            val result = repository.getUserByPhone(phone)
            if (result != null) {
                _user.value = result
                _error.value = ""
            } else {
                _user.value = null
                setError("شماره تلفن یافت نشد")
            }
        }
    }

    fun loginState(state: Boolean) {
        viewModelScope.launch {
            userPreferences.setLoggedIn(state)
        }
    }


    fun setError(error: String) {
        _error.value = error
        changeProgress(false)
    }

    fun changeProgress(state: Boolean) {
        _progress.value = state
    }

}