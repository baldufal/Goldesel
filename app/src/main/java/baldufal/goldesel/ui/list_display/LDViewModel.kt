package baldufal.goldesel.ui.list_display

import androidx.lifecycle.ViewModel
import baldufal.goldesel.model.Transaction
import java.time.LocalDateTime

class LDViewModel : ViewModel() {

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