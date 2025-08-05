package com.aliayali.market_baz.presentation.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    fun insertUser(phone: String, name: String, password: String, isLoggedIn: Boolean) {
        viewModelScope.launch {
            repository.insertUser(UserEntity(phone, name, password, isLoggedIn))
        }
    }

}