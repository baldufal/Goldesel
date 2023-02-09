package baldufal.goldesel.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM table_transactions")
    // query functions use own dispatcher -> no "suspend" needed
    fun getCashTransactions(): List<Transaction>

    @Query("SELECT * FROM table_transactions")
    // query functions use own dispatcher -> no "suspend" needed
    fun getGiroTransactions(): List<Transaction>

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)
}