package ui.goal

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.donyawan.salehere.MainViewModel
import com.donyawan.salehere.R
import com.donyawan.salehere.databinding.FragmentGoalBinding
import org.joda.time.LocalDate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import java.util.TimeZone


class GoalFragment : Fragment() {

    lateinit var binding: FragmentGoalBinding

    private val viewModel: GoalViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalBinding.inflate(layoutInflater, container, false)
        setupView()
        observe()
        return binding.root
    }

    private fun observe() {
        with(viewModel) {
            submitModel?.observe(viewLifecycleOwner) {

            }
        }

        viewModel.submitModel.observe(viewLifecycleOwner) {
            it?.let {
                mainViewModel.addGoal(it)
            }
            mainViewModel.navigateToHome()
        }
    }

    private fun setupView() {
        binding.etSelectDate.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())

            val dialog = DatePickerDialog(
                requireContext(),
                { p0, p1, p2, p3 ->
                    binding.etSelectDate.setText("${p0.dayOfMonth}-${p0.month + 1}-${p0.year}")
                    viewModel.setTargetDate(LocalDate(p0.year, p0.month + 1, p0.dayOfMonth))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }


        binding.goalTypeRadio.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.onSelectedType(i)
        }

        binding.btSubmit.setOnClickListener {
            viewModel.onClickSubmitButton(
                binding.etGoalHeader.text.toString(),
                binding.etAmount.text.toString(),
                binding.etBahtPerMonth.text.toString()
            )
        }

        binding.goalBankDropdown.apply {
            val bankAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.bank_account)
            )
            adapter = bankAdapter

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.setBankAccount(p0?.getItemAtPosition(p2).toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}

            }
        }
    }

    companion object {
        fun newInstance() = GoalFragment()
    }
}