package com.aliayali.market_baz.presentation.screen.splash

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    private val _delay = mutableStateOf(false)
    val delay: State<Boolean> = _delay

    init {
        viewModelScope.launch {
            delay(3000)
            _delay.value = true
        }
    }
}