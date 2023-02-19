package baldufal.goldesel.ui.list_display

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import baldufal.goldesel.model.DataRepository
import baldufal.goldesel.model.Transaction

class LDViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DataRepository(application)
    val giroTransactions: LiveData<List<Transaction>> = repository.giroData
    val cashTransactions: LiveData<List<Transaction>> = repository.cashData
    val otherTransactions: LiveData<List<Transaction>> = repository.otherData

    fun getTags(): HashSet<String> {
        val tags: HashSet<String> = HashSet();
        if (giroTransactions.value != null)
            for (t in giroTransactions.value!!)
                tags.addAll(t.tags)
        if (cashTransactions.value != null)
            for (t in cashTransactions.value!!)
                tags.addAll(t.tags)
        if (otherTransactions.value != null)
            for (t in otherTransactions.value!!)
                tags.addAll(t.tags)
        return tags
    }

    fun add(transaction: Transaction) {
        repository.insert(transaction)
    }

    fun delete(transaction: Transaction) {
        repository.deleteItem(transaction)
    }


}