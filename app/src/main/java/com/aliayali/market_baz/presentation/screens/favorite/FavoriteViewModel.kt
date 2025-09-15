package com.aliayali.market_baz.presentation.screens.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.FavoriteEntity
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _favorite = mutableStateOf<List<FavoriteEntity>>(emptyList())
    val favorite: State<List<FavoriteEntity>> = _favorite

    private var _phone = mutableStateOf("")

    init {
        viewModelScope.launch {
            userPreferences.phoneNumber.collect { phoneNumber ->
                phoneNumber?.let {
                    _phone.value = it
                    collectFavorites(it)
                }
            }
        }
    }

    private fun collectFavorites(phone: String) {
        viewModelScope.launch {
            favoriteRepository.getAllFavorites(phone).collect { products ->
                _favorite.value = products
            }
        }
    }
}
