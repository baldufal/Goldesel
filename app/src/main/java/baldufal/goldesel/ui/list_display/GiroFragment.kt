package baldufal.goldesel.ui.list_display

import TransactionAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import baldufal.goldesel.databinding.FragmentGiroBinding

class GiroFragment : Fragment() {

    private var _binding: FragmentGiroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: LDViewModel by activityViewModels()

        _binding = FragmentGiroBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        @Suppress("UNCHECKED_CAST")
        binding.recyclerView.adapter =
            TransactionAdapter(viewModel.giroTransactions)

        binding.refreshLayout.setOnRefreshListener {
            // todo: refresh
            (binding.recyclerView.adapter as TransactionAdapter).notifyDataSetChanged()
            binding.refreshLayout.isRefreshing = false
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                GiroFragmentDirections.actionNavigationGiroToAddFragment(
                    true
                )
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}