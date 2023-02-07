package baldufal.goldesel.ui.cash

import androidx.lifecycle.ViewModel
import baldufal.goldesel.model.ListElement
import java.time.Instant
import java.util.*

class CashViewModel : ViewModel() {

    val entries = arrayListOf<ListElement>(
        ListElement(
            "Apfeltasche", 380, Date.from(Instant.now()),
            investment = false,
            essential = true,
            dateAdded = Date.from(
                Instant.now()
            )
        )
    )

    fun refresh() {

    }
}