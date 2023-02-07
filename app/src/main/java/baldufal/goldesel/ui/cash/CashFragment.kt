package baldufal.goldesel.ui.cash

import TransactionAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import baldufal.goldesel.databinding.FragmentCashBinding

class CashFragment : Fragment() {

    private var _binding: FragmentCashBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cashViewModel =
            ViewModelProvider(this).get(CashViewModel::class.java)

        _binding = FragmentCashBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        @Suppress("UNCHECKED_CAST")
        binding.recyclerView.adapter =
            TransactionAdapter(cashViewModel.entries)

        binding.refreshLayout.setOnRefreshListener {
            cashViewModel.refresh()
            (binding.recyclerView.adapter as TransactionAdapter).notifyDataSetChanged()
            binding.refreshLayout.isRefreshing = false
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(CashFragmentDirections.actionNavigationCashToAddFragment())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}