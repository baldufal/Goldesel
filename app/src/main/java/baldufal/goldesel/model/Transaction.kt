package baldufal.goldesel.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime

@Entity(tableName = "table_transactions")
data class Transaction(
    @PrimaryKey
    val id: String,
    var name: String,
    var cents: Int,
    var date: LocalDateTime,
    var ttype: TransactionType, // Giro or cash or other source
    var category: TransactionCategory,
    var factor: Double, // Factor for cents. 1 for "normal" transactions or .5 for "shared" transactions
    var depreciation: Double,
    val dateAdded: LocalDateTime,
    var notes: String,
    var tags: List<String>
) : Serializable