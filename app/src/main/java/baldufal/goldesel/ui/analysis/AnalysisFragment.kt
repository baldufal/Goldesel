package baldufal.goldesel.ui.analysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import baldufal.goldesel.databinding.FragmentAnalysisBinding
import baldufal.goldesel.ui.list_display.LDViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class AnalysisFragment : Fragment() {

    private var _binding: FragmentAnalysisBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val analysisViewModel =
            ViewModelProvider(this)[AnalysisViewModel::class.java]

        val viewModel: LDViewModel by activityViewModels()
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)

        val list = mutableListOf<PieEntry>()
        list.add(PieEntry(1.0f, "Lupus"))
        list.add(PieEntry(2.0f, "Lumpi"))
        list.add(PieEntry(3.0f, "Lauschi"))

        val pieDataSet = PieDataSet(list, "piedataset")

        binding.chart1.data = PieData(pieDataSet)
        binding.chart1.invalidate()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}