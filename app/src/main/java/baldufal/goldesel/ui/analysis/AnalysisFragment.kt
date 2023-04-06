package baldufal.goldesel.ui.analysis

import android.R
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import baldufal.goldesel.databinding.FragmentAnalysisBinding
import baldufal.goldesel.model.TransactionType
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

        val ldViewModel: LDViewModel by activityViewModels()
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)

        var giro = 0
        var cash = 0
        var other = 0
        var timeframe = TimeFrame.TODAY

        fun displayData() {
            val list = mutableListOf<PieEntry>()

            list.add(PieEntry(giro.toFloat() / 100.0f, "Giro"))
            list.add(PieEntry(cash.toFloat() / 100.0f, "Cash"))
            list.add(PieEntry(other.toFloat() / 100.0f, "Other"))

            val pieDataSet = PieDataSet(list, "Sources")
            pieDataSet.colors = mutableListOf(Color.GREEN, Color.BLUE, Color.RED)
            pieDataSet.valueTextSize = 20f

            binding.chartSource.data = PieData(pieDataSet)
            binding.chartSource.setEntryLabelTextSize(20f) // Labels on slices
            binding.chartSource.setCenterTextSizePixels(20f) // useless
            binding.chartSource.setCenterTextSize(20f) // useless
            binding.chartSource.description.text = "Source"
            binding.chartSource.invalidate()
        }

            ldViewModel.getGiroCounts(
                timeframe.calculateStartDateTime(),
                timeframe.calculateEndDateTime()
            ).observe(viewLifecycleOwner) { value ->
                if (value != null) {
                    giro = value.toInt();
                    displayData()
                    println("giro loaded")
                }else {
                    println("giro null loaded")
                }
            }


            ldViewModel.getCashCounts(
                timeframe.calculateStartDateTime(),
                timeframe.calculateEndDateTime()
            ).observe(viewLifecycleOwner) { value ->
                if (value != null) {
                    cash = value.toInt();
                    displayData()
                    println("cash loaded")
                }else {
                    println("cash null loaded")
                }
            }

            ldViewModel.getOtherCounts(
                timeframe.calculateStartDateTime(),
                timeframe.calculateEndDateTime()
            ).observe(viewLifecycleOwner) { value ->
                if (value != null) {
                    other = value.toInt()
                    displayData()
                    println("other loaded")
                }else {
                    println("other null loaded")
                }
            }



        val list = mutableListOf<PieEntry>()
        list.add(PieEntry(1.0f, "Lupus"))
        list.add(PieEntry(2.0f, "Lumpi"))
        list.add(PieEntry(3.0f, "Lauschi"))
        val pieDataSet = PieDataSet(list, "dummy")

        binding.chartSource.data = PieData(pieDataSet)
        binding.chartSource.invalidate()

        val tags = mutableListOf<String>()
        for(tf in TimeFrame.values())
            tags.add(tf.pretty)
        val tagsArray = tags.toTypedArray()

        // Create an ArrayAdapter
        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.simple_spinner_item,
            tagsArray
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    timeframe = TimeFrame.values()[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}