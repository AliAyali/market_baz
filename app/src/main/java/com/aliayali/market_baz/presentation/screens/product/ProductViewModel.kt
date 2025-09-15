package com.aliayali.market_baz.presentation.screens.product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.CommentEntity
import com.aliayali.market_baz.data.local.database.entity.FavoriteEntity
import com.aliayali.market_baz.data.local.database.entity.ProductEntity
import com.aliayali.market_baz.data.local.database.entity.RatingEntity
import com.aliayali.market_baz.data.local.database.entity.ShoppingCardEntity
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.domain.repository.CommentRepository
import com.aliayali.market_baz.domain.repository.FavoriteRepository
import com.aliayali.market_baz.domain.repository.ProductRepository
import com.aliayali.market_baz.domain.repository.RatingRepository
import com.aliayali.market_baz.domain.repository.ShoppingCardRepository
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val shoppingCardRepository: ShoppingCardRepository,
    private val favoriteRepository: FavoriteRepository,
    private val commentRepository: CommentRepository,
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository,
    private val ratingRepository: RatingRepository,
) : ViewModel() {

    private val _user = mutableStateOf<UserEntity?>(null)
    val user: State<UserEntity?> = _user

    private val _comments = MutableStateFlow<List<CommentEntity>>(emptyList())
    val comments: StateFlow<List<CommentEntity>> = _comments.asStateFlow()

    private var _phone = mutableStateOf("")

    private val _product = mutableStateOf<ProductEntity?>(null)
    val product: State<ProductEntity?> = _product

    private val _isFavorite = mutableStateOf(false)
    val isFavorite: State<Boolean> = _isFavorite

    init {
        viewModelScope.launch {
            userPreferences.phoneNumber.collect { phoneNumber ->
                phoneNumber?.let {
                    _phone.value = it
                    getUserByPhone(it)
                }
            }
        }
    }

    fun getUserByPhone(phone: String) {
        viewModelScope.launch {
            val result = userRepository.getUserByPhone(phone)
            if (result != null) {
                _user.value = result
            } else {
                _user.value = null
            }
        }
    }

    fun getProductById(id: Int?) {
        viewModelScope.launch {
            _product.value = productRepository.getProductById(id ?: 0)
        }
    }

    fun insertShoppingCard(product: ProductEntity?, price: Int, number: Int) {
        viewModelScope.launch {
            product?.let { p ->
                val existingItem = shoppingCardRepository.getItemByProductId(p.id, _phone.value)
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
                            number = finalNumber,
                            userPhone = _phone.value
                        )
                    )
                }
            }
        }
    }

    fun checkIfFavorite(productId: Int) {
        viewModelScope.launch {
            _isFavorite.value = favoriteRepository.isFavorite(productId, _phone.value)
        }
    }

    fun toggleFavorite(product: ProductEntity?) {
        if (product == null) return
        viewModelScope.launch {
            val isFav = favoriteRepository.isFavorite(product.id, _phone.value)
            if (isFav) {
                favoriteRepository.deleteFavoriteByProductId(product.id, _phone.value)
            } else {
                favoriteRepository.insertFavorite(product.toFavoriteEntity(_phone.value))
            }
            _isFavorite.value = !isFav
        }
    }

    fun ProductEntity.toFavoriteEntity(userPhone: String): FavoriteEntity {
        return FavoriteEntity(
            productId = this.id,
            title = this.name,
            imageUrl = this.imageUrl,
            price = this.price,
            discount = this.discount,
            userPhone = userPhone
        )
    }

    fun insertComment(comment: CommentEntity) {
        viewModelScope.launch {
            commentRepository.insertComment(comment)
        }
    }

    fun loadComments(productId: Int) {
        viewModelScope.launch {
            commentRepository.getCommentsByProductId(productId).collect { list ->
                _comments.value = list
            }
        }
    }

    fun deleteComment(comment: CommentEntity) {
        viewModelScope.launch {
            commentRepository.deleteComment(comment)
            loadComments(comment.productId)
        }
    }

    fun addRating(product: ProductEntity?, newRating: Int) {
        if (product == null || _user.value == null) return

        val userPhone = _user.value!!.phone
        viewModelScope.launch(Dispatchers.IO) {
            val existingRating = ratingRepository.getRatingByUserAndProduct(userPhone, product.id)

            if (existingRating == null) {
                val oldStar = product.star
                val oldComments = product.numberOfComments

                val newNumberOfComments = oldComments + 1
                val newStar = ((oldStar * oldComments) + newRating) / newNumberOfComments

                val updatedProduct = product.copy(
                    star = newStar,
                    numberOfComments = newNumberOfComments
                )
                productRepository.updateProduct(updatedProduct)
                ratingRepository.insertRating(
                    RatingEntity(productId = product.id, userPhone = userPhone, rating = newRating)
                )
            }
            getProductById(product.id)
        }
    }


}