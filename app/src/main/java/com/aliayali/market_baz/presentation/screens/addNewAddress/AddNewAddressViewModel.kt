package com.aliayali.market_baz.presentation.screens.addNewAddress

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.market_baz.data.local.database.entity.AddressEntity
import com.aliayali.market_baz.domain.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewAddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository
) : ViewModel() {

    private val _address = mutableStateOf<AddressEntity?>(null)
    val address: State<AddressEntity?> = _address

    fun insertAddress(address: AddressEntity) {
        viewModelScope.launch {
            addressRepository.insertAddress(address)
        }
    }

    fun updateAddress(address: AddressEntity) {
        viewModelScope.launch {
            addressRepository.updateAddress(address)
        }
    }

    fun getAddressById(addressId: Int) {
        viewModelScope.launch {
            val result = addressRepository.getAddressById(addressId)
            if (result != null) {
                _address.value = result
            } else {
                _address.value = null
            }
        }
    }

}