package baldufal.goldesel.ui.list_display

import TransactionAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        val giroViewModel =
            ViewModelProvider(this).get(LDViewModel::class.java)

        _binding = FragmentGiroBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        @Suppress("UNCHECKED_CAST")
        binding.recyclerView.adapter =
            TransactionAdapter(giroViewModel.entries)

        binding.refreshLayout.setOnRefreshListener {
            giroViewModel.refresh()
            (binding.recyclerView.adapter as TransactionAdapter).notifyDataSetChanged()
            binding.refreshLayout.isRefreshing = false
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(GiroFragmentDirections.actionNavigationGiroToAddFragment())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}