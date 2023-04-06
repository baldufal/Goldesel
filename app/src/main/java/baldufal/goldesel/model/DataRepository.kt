package baldufal.goldesel.model

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class DataRepository(application: Application) {
    private val dao: TransactionDao
     val giroData: LiveData<List<Transaction>>
     val cashData: LiveData<List<Transaction>>
     val otherData: LiveData<List<Transaction>>

    init {
        val db = TransactionDatabase.getDatabase(application.applicationContext)
        dao = db.dataDAO()

        giroData = dao.getGiroTransactions()
        cashData = dao.getCashTransactions()
        otherData = dao.getOtherTransactions()
    }

    fun insert(item: Transaction) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertTransaction(item)
        }
    }

    fun deleteItem(item: Transaction) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteTransaction(item)
        }
    }

    fun getGiroCount(from: LocalDateTime, to:LocalDateTime) : LiveData<Integer>{
            return dao.getGiroCount(from, to)
    }
    fun getCashCount(from: LocalDateTime, to:LocalDateTime) : LiveData<Integer>{
        return dao.getCashCount(from, to)
    }
    fun getOtherCount(from: LocalDateTime, to:LocalDateTime) : LiveData<Integer>{
        return dao.getOtherCount(from, to)
    }
}