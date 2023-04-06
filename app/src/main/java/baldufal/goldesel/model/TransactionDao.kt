package baldufal.goldesel.model

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDateTime

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM table_transactions WHERE ttype = 'GIRO' ORDER BY date DESC")
    // query functions use own dispatcher -> no "suspend" needed
    fun getGiroTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM table_transactions WHERE ttype = 'CASH' ORDER BY date DESC")
    // query functions use own dispatcher -> no "suspend" needed
    fun getCashTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM table_transactions WHERE ttype = 'OTHER' ORDER BY date DESC")
    // query functions use own dispatcher -> no "suspend" needed
    fun getOtherTransactions(): LiveData<List<Transaction>>

    @Query("SELECT SUM( cents ) FROM table_transactions WHERE ttype = 'GIRO' AND date >= :from AND date <= :to")
    fun getGiroCount(from: LocalDateTime, to: LocalDateTime): LiveData<Integer>

    @Query("SELECT SUM( cents ) FROM table_transactions WHERE ttype = 'CASH' AND date >= :from AND date <= :to")
    fun getCashCount(from: LocalDateTime, to: LocalDateTime): LiveData<Integer>

    @Query("SELECT SUM( cents ) FROM table_transactions WHERE ttype = 'OTHER' AND date >= :from AND date <= :to")
    fun getOtherCount(from: LocalDateTime, to: LocalDateTime): LiveData<Integer>

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)
}