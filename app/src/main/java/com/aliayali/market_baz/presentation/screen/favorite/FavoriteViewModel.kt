package com.aliayali.market_baz.presentation.screen.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.FavoriteEntity
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.data.local.database.entity.ShoppingCardEntity
import com.aliayali.market_baz.domain.repository.FavoriteRepository
import com.aliayali.market_baz.domain.repository.ProductRepository
import com.aliayali.market_baz.domain.repository.ShoppingCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _favorite = mutableStateOf<List<FavoriteEntity?>>(listOf())
    val favorite: State<List<FavoriteEntity?>> = _favorite

    init {

        viewModelScope.launch {
            favoriteRepository.getAllFavorites().collect { products ->
                _favorite.value = products
            }
        }

    }

}