package com.aliayali.market_baz.presentation.screen.product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.data.local.database.entity.ShoppingCardEntity
import com.aliayali.market_baz.domain.repository.ProductRepository
import com.aliayali.market_baz.domain.repository.ShoppingCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val shoppingCardRepository: ShoppingCardRepository,
) : ViewModel() {

    private val _product = mutableStateOf<ProductEntity?>(null)
    val product: State<ProductEntity?> = _product

    fun getProductById(id: Int?) {
        viewModelScope.launch {
            _product.value = productRepository.getProductById(id ?: 0)
        }
    }

    fun insertShoppingCard(product: ProductEntity?, price: Int, number: Int) {
        viewModelScope.launch {
            product?.let { p ->
                val existingItem = shoppingCardRepository.getItemByProductId(p.id)
                if (existingItem != null) {
                    val newNumber = existingItem.number + number
                    val finalNumber = if (newNumber > p.inventory) p.inventory else newNumber
                    shoppingCardRepository.updateItem(existingItem.copy(number = finalNumber))
                } else {
                    val finalNumber = if (number > p.inventory) p.inventory else number
                    shoppingCardRepository.insertItem(
                        ShoppingCardEntity(
                            productId = p.id,
                            imageUrl = p.imageUrl,
                            name = p.name,
                            price = price,
                            number = finalNumber
                        )
                    )
                }
            }
        }
    }

}