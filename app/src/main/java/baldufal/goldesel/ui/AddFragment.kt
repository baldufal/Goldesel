package baldufal.goldesel.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import baldufal.goldesel.databinding.FragmentAddBinding
import baldufal.goldesel.model.Transaction
import baldufal.goldesel.model.TransactionCategory
import baldufal.goldesel.model.TransactionType
import baldufal.goldesel.ui.list_display.LDViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.math.roundToInt

private const val ARG_TTYPE = "ttype"
private const val ARG_TRANS = "trans"

class AddFragment : Fragment() {

    private var trans = Transaction(
        UUID.randomUUID().toString(),
        "",
        cents = 0,
        LocalDateTime.now(),
        TransactionType.CASH,
        TransactionCategory.LUXURY_CONSUMPTION,
        -1.0,
        LocalDateTime.now(),
        "",
        listOf()
    )

    private var _binding: FragmentAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            trans.ttype = it.getSerializable(ARG_TTYPE) as TransactionType
            val t = it.getSerializable(ARG_TRANS) as Transaction?
            if (t != null) {
                trans = t
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        val ldViewModel: LDViewModel by activityViewModels()

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        fun onClickAdd(it: View) {
            val (check, trans) = createTransaction()
            // Check if exactly one of the pair values is non null
            assert(check != null || trans != null)
            assert(!(check == null && trans == null))
            if (trans != null) {
                ldViewModel.add(trans)
                findNavController().navigateUp()
            } else
                Toast.makeText(it.context, check, Toast.LENGTH_SHORT).show()
        }

        binding.btnAddTop.setOnClickListener { onClickAdd(it) }
        binding.btnAdd.setOnClickListener { onClickAdd(it) }

        when (trans.ttype) {
            TransactionType.GIRO ->
                binding.rbtnGiro.isChecked = true
            TransactionType.CASH ->
                binding.rbtnCash.isChecked = true
            TransactionType.OTHER ->
                binding.rbtnOther.isChecked = true
        }
        binding.radioGroup.setOnCheckedChangeListener { radioGroup: RadioGroup, _: Int ->
            if (resources.getResourceName(radioGroup.checkedRadioButtonId).contains("giro"))
                trans.ttype = TransactionType.GIRO
            else if (resources.getResourceName(radioGroup.checkedRadioButtonId).contains("cash"))
                trans.ttype = TransactionType.CASH
            else if (resources.getResourceName(radioGroup.checkedRadioButtonId).contains("other"))
                trans.ttype = TransactionType.OTHER
        }

        if (trans.cents != 0)
            binding.etCents.setText((trans.cents / 100.0).toString())

        binding.etName.setText(trans.name)

        fun updateButtonState() {
            val STROKE = 20

            var depreciation = View.GONE
            var li = 0
            var ei = 0
            var lc = 0
            var ec = 0
            var ex = 0

            when (trans.category) {
                TransactionCategory.LUXURY_INVESTMENT -> {
                    depreciation = View.VISIBLE
                    li = STROKE
                }
                TransactionCategory.ESSENTIAL_INVESTMENT -> {
                    depreciation = View.VISIBLE
                    ei = STROKE
                }
                TransactionCategory.ESSENTIAL_CONSUMPTION ->
                    ec = STROKE
                TransactionCategory.LUXURY_CONSUMPTION ->
                    lc = STROKE
                TransactionCategory.EXCLUDE ->
                    ex = STROKE
            }

            binding.btnLi.strokeWidth = li
            binding.btnLc.strokeWidth = lc
            binding.btnEi.strokeWidth = ei
            binding.btnEc.strokeWidth = ec
            binding.btnExclude.strokeWidth = ex

            binding.tvDepreciation.visibility = depreciation
            binding.etDepreciation.visibility = depreciation
        }

        updateButtonState()

        binding.btnEc.setOnClickListener {
            trans.category = TransactionCategory.ESSENTIAL_CONSUMPTION
            updateButtonState()
        }

        binding.btnEi.setOnClickListener {
            trans.category = TransactionCategory.ESSENTIAL_INVESTMENT
            updateButtonState()
        }

        binding.btnLc.setOnClickListener {
            trans.category = TransactionCategory.LUXURY_CONSUMPTION
            updateButtonState()
        }

        binding.btnLi.setOnClickListener {
            trans.category = TransactionCategory.LUXURY_INVESTMENT
            updateButtonState()
        }

        binding.btnExclude.setOnClickListener {
            trans.category = TransactionCategory.EXCLUDE
            updateButtonState()
        }

        binding.tvTags.visibility = View.GONE
        binding.spinner.visibility = View.GONE
/*
        val tags = ldViewModel.getTags()
        tags.add("*NEW*")
        val tagsArray = tags.toTypedArray()

        // Create an ArrayAdapter
        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            tagsArray
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.spinner.adapter = adapter


        {
            Toast.makeText(
                requireContext(),
                binding.spinner.selectedItem.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
*/




        @SuppressLint("SetTextI18n")
        fun updateDateTime() {
            binding.btnTime.text = "%02d:%02d".format(trans.date.hour, trans.date.minute)
            binding.btnDate.text = trans.date.toLocalDate().toString()
        }
        updateDateTime()

        binding.btnDate.setOnClickListener {
            val picker = DatePickerDialog(this.requireContext())
            picker.show()
            picker.setOnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                trans.date =
                    LocalDateTime.of(LocalDate.of(year, month, day), trans.date.toLocalTime())
                updateDateTime()
            }
        }

        binding.btnTime.setOnClickListener {
            val picker = TimePickerDialog(
                this.requireContext(),
                { _, hourOfDay, minute ->
                    trans.date =
                        LocalDateTime.of(trans.date.toLocalDate(), LocalTime.of(hourOfDay, minute))
                    updateDateTime()
                },
                trans.date.hour,
                trans.date.minute,
                true
            )
            picker.show()
        }

        binding.etNotes.setText(trans.notes)

        return binding.root
    }

    private fun createTransaction(): Pair<String?, Transaction?> {
        if (binding.etName.text.toString().length < 2)
            return Pair("Name too short", null)
        if (binding.etName.text.toString().length > 50)
            return Pair("Name too long", null)
        trans.name = binding.etName.text.toString()

        val cents: Int
        try {
            cents = (binding.etCents.text.toString().toDouble() * 100.0).roundToInt()
        } catch (e: Exception) {
            return Pair("Cannot parse value", null)
        }
        trans.cents = cents

        var depreciation = -1.0

        if ((trans.category == TransactionCategory.LUXURY_INVESTMENT
                    || trans.category == TransactionCategory.ESSENTIAL_INVESTMENT)
            && binding.etDepreciation.text.isNotEmpty()
        )
            try {
                depreciation = binding.etDepreciation.text.toString().toDouble()
                if (depreciation <= 0)
                    return Pair("Depreciation must be positive or empty", null)
            } catch (e: Exception) {
                return Pair("Cannot parse depreciation", null)
            }

        trans.depreciation = depreciation

        trans.notes = binding.etNotes.text.toString()

        return Pair(
            null, trans
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(ttype: TransactionType) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TTYPE, ttype)
                }
            }
    }
}