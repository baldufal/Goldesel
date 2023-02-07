package baldufal.goldesel.ui.cash

import androidx.lifecycle.ViewModel
import baldufal.goldesel.model.Transaction
import java.time.LocalDateTime

class CashViewModel : ViewModel() {

    val entries = arrayListOf<Transaction>(
        Transaction(
            "Apfeltasche",
            380,
            LocalDateTime.now(),
            investment = false,
            essential = true,
            giro = false,
            dateAdded = LocalDateTime.now()
        )
    )

    fun refresh() {

    }
}