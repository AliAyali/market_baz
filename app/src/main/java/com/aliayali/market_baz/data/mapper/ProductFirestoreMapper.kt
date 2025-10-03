package com.aliayali.market_baz.data.mapper

import com.aliayali.market_baz.domain.model.Product
import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.toDomain(): Product? {
    return this.toObject(Product::class.java)?.copy(id = this.id)
}