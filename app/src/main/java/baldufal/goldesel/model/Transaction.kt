package baldufal.goldesel.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(tableName = "table_transactions")
data class Transaction(
    @PrimaryKey
    val id: String,
    var name: String,
    var cents: Int,
    var date: LocalDateTime,
    var giro: Boolean, // Giro or cash
    var investment: Boolean, // Investment or  Consumption
    var essential: Boolean, // Essential or luxury
    val dateAdded: LocalDateTime,
    var notes: String,
    var tags: List<String>
)