package baldufal.goldesel.ui

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
import baldufal.goldesel.ui.list_display.LDViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.math.roundToInt

private const val ARG_GIRO = "giro"

class AddFragment : Fragment() {

    private var giro: Boolean = true
    private var essential: Boolean = false
    private var investment: Boolean = false
    private var dateTime: LocalDateTime = LocalDateTime.now()

    private var _binding: FragmentAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: AddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            giro = it.getBoolean(ARG_GIRO)
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

        binding.btnAddTop.setOnClickListener { it -> onClickAdd(it) }
        binding.btnAdd.setOnClickListener { it -> onClickAdd(it) }

        binding.rbtnGiro.isChecked = giro
        binding.rbtnCash.isChecked = !giro
        binding.radioGroup.setOnCheckedChangeListener { radioGroup: RadioGroup, _: Int ->
            giro = resources.getResourceName(radioGroup.checkedRadioButtonId).contains("giro")
        }

        fun updateButtonState() {
            if (essential) {
                binding.btnEssential.strokeWidth = 20
                binding.btnLuxury.strokeWidth = 0
            } else {
                binding.btnEssential.strokeWidth = 0
                binding.btnLuxury.strokeWidth = 20
            }
            if (investment) {
                binding.btnInvestment.strokeWidth = 20
                binding.btnConsumption.strokeWidth = 0
                binding.tvDepreciation.visibility = View.VISIBLE
                binding.etDepreciation.visibility = View.VISIBLE
            } else {
                binding.btnInvestment.strokeWidth = 0
                binding.btnConsumption.strokeWidth = 20
                binding.tvDepreciation.visibility = View.GONE
                binding.etDepreciation.visibility = View.GONE
            }
        }

        updateButtonState()

        binding.btnEssential.setOnClickListener {
            essential = true
            updateButtonState()
        }

        binding.btnLuxury.setOnClickListener {
            essential = false
            updateButtonState()
        }

        binding.btnInvestment.setOnClickListener {
            investment = true
            updateButtonState()
        }

        binding.btnConsumption.setOnClickListener {
            investment = false
            updateButtonState()
        }



        fun updateDateTime() {
            binding.btnTime.text = "%02d:%02d".format(dateTime.hour, dateTime.minute)
            binding.btnDate.text = dateTime.toLocalDate().toString()
        }
        updateDateTime()

        binding.btnDate.setOnClickListener {
            val picker = DatePickerDialog(this.requireContext())
            picker.show()
            picker.setOnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                dateTime = LocalDateTime.of(LocalDate.of(year, month, day), dateTime.toLocalTime())
                updateDateTime()
            }
        }

        binding.btnTime.setOnClickListener {

            val picker = TimePickerDialog(
                this.requireContext(),
                { _, hourOfDay, minute ->
                    dateTime =
                        LocalDateTime.of(dateTime.toLocalDate(), LocalTime.of(hourOfDay, minute))
                    updateDateTime()
                },
                dateTime.hour,
                dateTime.minute,
                true
            )
            picker.show()
        }





        return binding.root
    }

    private fun createTransaction(): Pair<String?, Transaction?> {
        if (binding.etName.text.toString().length < 2)
            return Pair("Name too short", null)
        if (binding.etName.text.toString().length > 50)
            return Pair("Name too long", null)
        var cents = 0;
        try {
            cents = (binding.etCents.text.toString().toDouble() * 100.0).roundToInt()
        } catch (e: Exception) {
            return Pair("Cannot parse value", null)
        }
        return Pair(
            null, Transaction(
                UUID.randomUUID().toString(),
                binding.etName.text.toString(),
                cents,
                dateTime,
                giro,
                investment,
                essential,
                LocalDateTime.now(),
                binding.etNotes.text.toString(),
                listOf()
            )
        );
    }


    companion object {
        @JvmStatic
        fun newInstance(giro: Boolean) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_GIRO, giro)
                }
            }
    }

}