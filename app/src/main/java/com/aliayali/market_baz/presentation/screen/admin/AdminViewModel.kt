package com.aliayali.market_baz.presentation.screen.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.R
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    fun insertProduct(
        name: String,
        description: String,
        price: Int,
        discount: Int,
        categoryId: Int,
        inventory: Int,
    ) {
        viewModelScope.launch {
            repository.insertProducts(
                ProductEntity(
                    imageUrl = R.drawable.ic_launcher_background,
                    name = name,
                    description = description,
                    price = price,
                    discount = discount,
                    categoryId = categoryId,
                    inventory = inventory
                )
            )
        }
    }

}