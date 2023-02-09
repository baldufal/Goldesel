package baldufal.goldesel.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import baldufal.goldesel.databinding.FragmentAddBinding
import baldufal.goldesel.model.Transaction
import baldufal.goldesel.ui.list_display.LDViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        val ldViewModel: LDViewModel by activityViewModels()

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.etCents.inputType = InputType.TYPE_NUMBER_FLAG_SIGNED

        binding.etDate.inputType = InputType.TYPE_DATETIME_VARIATION_DATE
        binding.etDate.setText(LocalDateTime.now().toString())
        binding.etDate.setOnClickListener {
            val picker = DatePickerDialog(this.requireContext())
            picker.show()
            picker.setOnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                binding.etDate.setText(LocalDateTime.of(year, month, day,0,0).toString())
            }
        }

        binding.btnAdd.setOnClickListener {
            val check = checkInputs()
            if (check == null)
                ldViewModel.add(createTransaction())
            else
                Toast.makeText(it.context, check, Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        return binding.root
    }

    fun checkInputs(): String? {
        return null;
    }

    fun createTransaction(): Transaction {
        return Transaction(
            UUID.randomUUID().toString(),
            binding.etName.text.toString(),
            Integer.parseInt(binding.etCents.text.toString()),
            LocalDateTime.parse(binding.etDate.text),
            isGiro(),
            true,
            true,
            LocalDateTime.now(),
            "Notes",
            listOf("lumpia", "shack")
        )
    }

    fun isGiro(): Boolean {
        return true;
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        // TODO: Use the ViewModel
    }

}