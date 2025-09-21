package com.aliayali.market_baz.presentation.screens.admin.reportsSection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.domain.repository.CommentRepository
import com.aliayali.market_baz.domain.repository.ProductRepository
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val commentRepository: CommentRepository,
) : ViewModel() {

    private val _userCount = MutableStateFlow(0)
    val userCount: StateFlow<Int> = _userCount

    private val _productCount = MutableStateFlow(0)
    val productCount: StateFlow<Int> = _productCount

    private val _commentCount = MutableStateFlow(0)
    val commentCount: StateFlow<Int> = _commentCount

    fun loadReports() {
        viewModelScope.launch {
            _userCount.value = userRepository.getUsersCount()
            _productCount.value = productRepository.getProductsCount()
            _commentCount.value = commentRepository.getCommentsCount()
        }
    }

}