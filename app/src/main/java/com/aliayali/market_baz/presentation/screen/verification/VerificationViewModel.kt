package com.aliayali.market_baz.presentation.screen.verification

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor() : ViewModel() {


    private var _resend = mutableIntStateOf(60)
    val resend: State<Int> = _resend

    private var timerDuration = 60
    private var timerJob: Job? = null

    init {
        startResendTimer()
    }

    fun startResendTimer() {
        timerJob?.cancel()
        _resend.intValue = timerDuration

        timerJob = viewModelScope.launch {
            for (i in timerDuration downTo 0) {
                _resend.intValue = i
                delay(1000)
            }
        }
    }

    fun timerPlus() {
        timerDuration = if (timerDuration >= 480) 600 else timerDuration * 2
        _resend.intValue = timerDuration
        startResendTimer()
    }
}

