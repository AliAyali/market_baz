package com.aliayali.market_baz.presentation.screen.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    private val _user = mutableStateOf<UserEntity?>(null)
    val user: State<UserEntity?> = _user

    private var _error = mutableStateOf("")
    val error: State<String> = _error

    fun getUserByPhone(phone: String) {
        viewModelScope.launch {
            val result = repository.getUserByPhone(phone)
            if (result != null) {
                _user.value = result
                _error.value = ""
            } else {
                _user.value = null
                _error.value = "شماره تلفن یافت نشد"
            }
        }
    }


    fun setError(error: String) {
        _error.value = error
    }

}