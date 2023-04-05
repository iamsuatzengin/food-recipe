package com.example.foodyrecipes.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "shopping_table")
data class ShoppingEntity(
    val title: String,
    val shoppingList: String,
): Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
