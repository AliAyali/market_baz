package com.aliayali.market_baz.presentation.screens.address

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.AddressEntity
import com.aliayali.market_baz.data.local.database.entity.UserEntity
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.domain.repository.AddressRepository
import com.aliayali.market_baz.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository,
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _addresses = MutableStateFlow<List<AddressEntity>>(emptyList())
    val addresses: StateFlow<List<AddressEntity>> = _addresses.asStateFlow()
    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user.asStateFlow()
    private var _phone = mutableStateOf("")

    init {
        viewModelScope.launch {
            userPreferences.phoneNumber.collect { phoneNumber ->
                phoneNumber?.let {
                    _phone.value = it
                    getUserByPhone(it)

                    launch {
                        addressRepository.getAllAddresses(it).collect { list ->
                            _addresses.value = list
                        }
                    }
                }
            }
        }
    }


    fun deleteAddress(address: AddressEntity) {
        viewModelScope.launch {
            addressRepository.deleteAddress(address)
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

    fun updateUserAddress(newAddress: String) {
        viewModelScope.launch {
            _user.value?.let { currentUser ->
                val updatedUser = currentUser.copy(address = newAddress)
                _user.value = updatedUser
                userRepository.updateUser(updatedUser)
            }
        }
    }


}