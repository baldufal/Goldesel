package baldufal.goldesel.model

import java.util.*

data class ListElement(
    val name: String,
    val cents: Int,
    val date: Date,
    val investment: Boolean,
    val essential: Boolean,
    val dateAdded: Date
)
