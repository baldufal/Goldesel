package baldufal.goldesel.ui.list_display

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import baldufal.goldesel.databinding.FragmentGiroBinding
import baldufal.goldesel.model.Transaction
import baldufal.goldesel.model.TransactionType
import baldufal.goldesel.ui.TransactionAdapter

class GiroFragment : Fragment(), TransactionLongClickListener {

    private var _binding: FragmentGiroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: LDViewModel by activityViewModels()

        _binding = FragmentGiroBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = TransactionAdapter(viewModel.giroTransactions, this)
        binding.recyclerView.adapter = adapter
        viewModel.giroTransactions.observe(
            viewLifecycleOwner
        ) { adapter.notifyDataSetChanged() }
        binding.refreshLayout.setOnRefreshListener {
            // todo: refresh
            (binding.recyclerView.adapter as TransactionAdapter).notifyDataSetChanged()
            binding.refreshLayout.isRefreshing = false
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                GiroFragmentDirections.actionNavigationGiroToAddFragment(
                    TransactionType.GIRO
                )
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // DUPLICATE
    override fun onTransactionLongClick(view: View, transaction: Transaction?) {
        if (transaction == null)
            return
        val alertDialog: AlertDialog = AlertDialog.Builder(binding.root.context).create()
        alertDialog.setTitle("Edit Transaction")
        alertDialog.setMessage(transaction.name)

        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "Edit"
        ) { _, _ ->
            findNavController().navigate(
                GiroFragmentDirections.actionNavigationGiroToAddFragment(
                    transaction.ttype,
                    transaction
                )
            )
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel") { _, _ -> }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Delete") { _, _ ->
            activityViewModels<LDViewModel>().value.delete(transaction)
        }

        alertDialog.show()
    }
}