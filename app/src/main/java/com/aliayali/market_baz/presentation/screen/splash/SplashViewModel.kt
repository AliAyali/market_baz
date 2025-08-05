package com.aliayali.market_baz.presentation.screen.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    val isLoggedIn = userPreferences.isLoggedIn.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        false
    )
    private val _delay = mutableStateOf(false)
    val delay: State<Boolean> = _delay

    private val _user = mutableStateOf<UserEntity?>(null)
    val user: State<UserEntity?> = _user

    init {
        viewModelScope.launch {
            delay(3000)
            _delay.value = true
        }
    }
}