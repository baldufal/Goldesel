package baldufal.goldesel.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM table_transactions WHERE ttype = 'GIRO' ORDER BY dateAdded DESC")
    // query functions use own dispatcher -> no "suspend" needed
    fun getGiroTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM table_transactions WHERE ttype = 'CASH' ORDER BY dateAdded DESC")
    // query functions use own dispatcher -> no "suspend" needed
    fun getCashTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM table_transactions WHERE ttype = 'OTHER' ORDER BY dateAdded DESC")
    // query functions use own dispatcher -> no "suspend" needed
    fun getOtherTransactions(): LiveData<List<Transaction>>


    @Delete
    suspend fun deleteTransaction(transaction: Transaction)
}