package baldufal.goldesel.ui.list_display

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import baldufal.goldesel.model.Transaction
import baldufal.goldesel.model.TransactionDao
import baldufal.goldesel.model.TransactionDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.*

class LDViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var transactionDao: TransactionDao
    val giroTransactions = mutableListOf<Transaction>()
    val cashTransactions = mutableListOf<Transaction>()

    init {
        val db = Room.databaseBuilder(
            getApplication(),
            TransactionDatabase::class.java, "transactions"
        ).build()
        transactionDao = db.transactionDao()
        CoroutineScope(Dispatchers.IO).launch {
            giroTransactions.addAll(transactionDao.getGiroTransactions())
            cashTransactions.addAll(transactionDao.getCashTransactions())
        }
    }

    fun add(transaction: Transaction){
        CoroutineScope(Dispatchers.IO).launch {
            transactionDao.insertTransaction(transaction)
        }
    }


    val entries = arrayListOf<Transaction>(
        Transaction(
            UUID.randomUUID().toString(),
            "Apfeltasche",
            380,
            LocalDateTime.now(),
            investment = false,
            essential = true,
            giro = false,
            dateAdded = LocalDateTime.now(),
            notes = "Sehr wichtig\nBadisch Backstub",
            tags = listOf()
        )
    )
}