package com.aliayali.market_baz.presentation.screens.addNewAddress

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.AddressEntity
import com.aliayali.market_baz.data.local.datastore.UserPreferences
import com.aliayali.market_baz.domain.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewAddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _address = mutableStateOf<AddressEntity?>(null)
    val address: State<AddressEntity?> = _address

    private var _phone = mutableStateOf("")
    val phone: State<String> = _phone

    init {
        viewModelScope.launch {
            userPreferences.phoneNumber.collect { phoneNumber ->
                phoneNumber?.let {
                    _phone.value = it
                }
            }
        }
    }

    fun insertAddress(address: AddressEntity) {
        viewModelScope.launch {
            val addressWithUser = address.copy(userPhone = _phone.value)
            addressRepository.insertAddress(addressWithUser)
        }
    }

    fun updateAddress(address: AddressEntity) {
        viewModelScope.launch {
            val addressWithUser = address.copy(userPhone = _phone.value)
            addressRepository.updateAddress(addressWithUser)
        }
    }

    fun getAddressById(addressId: Int) {
        viewModelScope.launch {
            _address.value = addressRepository.getAddressById(addressId)
        }
    }

}