package baldufal.goldesel.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(tableName = "table_transactions")
data class Transaction(
    @PrimaryKey
    val id: String,
    var name: String,
    var cents: Int,
    var date: LocalDateTime,
    var ttype: TransactionType, // Giro or cash or other
    var category: TransactionCategory,
    var depreciation: Double,
    val dateAdded: LocalDateTime,
    var notes: String,
    var tags: List<String>
):Serializable