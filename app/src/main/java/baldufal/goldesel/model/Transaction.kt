package baldufal.goldesel.model

import java.time.LocalDateTime
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_transactions")
data class Transaction(
    val name: String,
    val cents: Int,
    val date: LocalDateTime,
    val giro: Boolean, // Giro or cash
    val investment: Boolean, // Investment or  Consumption
    val essential: Boolean, // Essential or luxury
    @PrimaryKey
    val dateAdded: LocalDateTime
)
