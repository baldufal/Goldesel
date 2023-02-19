package baldufal.goldesel.ui.list_display

import android.view.View
import baldufal.goldesel.model.Transaction

interface TransactionLongClickListener {
    fun onTransactionLongClick(view: View, transaction: Transaction?)
}