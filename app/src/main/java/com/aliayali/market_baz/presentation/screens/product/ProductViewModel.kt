package com.aliayali.market_baz.presentation.screens.product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.CommentEntity
import com.aliayali.market_baz.data.local.database.entity.FavoriteEntity
import com.aliayali.market_baz.data.local.database.entity.RatingEntity
import com.aliayali.market_baz.data.local.database.entity.ShoppingCardEntity
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.domain.model.Product
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

    private val _product = mutableStateOf<Product?>(null)
    val product: State<Product?> = _product

    private val _isFavorite = mutableStateOf(false)
    val isFavorite: State<Boolean> = _isFavorite
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

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

    fun getProductById(id: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            _product.value = id?.let { productRepository.getProductById(it) }
            _isLoading.value = false
        }
    }

    fun insertShoppingCard(product: Product?, price: Int, number: Int) {
        viewModelScope.launch {
            product?.let { p ->
                val existingItem = p.id?.let { shoppingCardRepository.getItemByProductId(it, _phone.value) }
                if (existingItem != null) {
                    val newNumber = existingItem.number + number
                    val finalNumber = if (newNumber > p.inventory) p.inventory else newNumber
                    shoppingCardRepository.updateItem(existingItem.copy(number = finalNumber))
                } else {
                    val finalNumber = if (number > p.inventory) p.inventory else number
                    p.id?.let {
                        shoppingCardRepository.insertItem(
                            ShoppingCardEntity(
                                productId = it,
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
    }

    fun checkIfFavorite(productId: String) {
        viewModelScope.launch {
            _isFavorite.value = favoriteRepository.isFavorite(productId, _phone.value)
        }
    }

    fun toggleFavorite(product: Product?) {
        if (product == null) return
        viewModelScope.launch {
            val isFav = product.id?.let { favoriteRepository.isFavorite(it, _phone.value) }
            if (isFav == true) {
                favoriteRepository.deleteFavoriteByProductId(product.id, _phone.value)
            } else {
                favoriteRepository.insertFavorite(product.toFavoriteEntity(_phone.value))
            }
            isFav?.let { _isFavorite.value = !it }
        }
    }

    fun Product.toFavoriteEntity(userPhone: String): FavoriteEntity {
        return FavoriteEntity(
            productId = this.id.toString(),
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

    fun loadComments(productId: String) {
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

    fun addRating(product: Product?, newRating: Int) {
        if (product == null || _user.value == null) return

        val userPhone = _user.value!!.phone
        viewModelScope.launch(Dispatchers.IO) {
            val existingRating =
                product.id?.let { ratingRepository.getRatingByUserAndProduct(userPhone, it) }

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
                product.id?.let {
                    ratingRepository.insertRating(
                        RatingEntity(productId = it, userPhone = userPhone, rating = newRating)
                    )
                }
            }
            getProductById(product.id)
        }
    }


}