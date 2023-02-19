package baldufal.goldesel.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Transaction::class], version = 1)
@TypeConverters(Converters::class)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun dataDAO(): TransactionDao

    companion object {
        private var INSTANCE: TransactionDatabase? = null
        fun getDatabase(context: Context): TransactionDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context,
                            TransactionDatabase::class.java,
                            "transaction_database"
                        )
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}
