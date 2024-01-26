package ui.goal

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.donyawan.salehere.databinding.FragmentGoalBinding
import java.util.Calendar
import java.util.TimeZone


class GoalFragment : Fragment() {

    lateinit var binding: FragmentGoalBinding

    private lateinit var viewModel: GoalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGoalBinding.inflate(layoutInflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.etSelectDate.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())

            val dialog = DatePickerDialog(
                requireContext(),
                { p0, p1, p2, p3 ->
                    binding.etSelectDate.setText("${p0.dayOfMonth}-${p0.month + 1}-${p0.year}")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }

        binding.goalTypeRadio.setOnCheckedChangeListener { radioGroup, i ->
            Toast.makeText(requireContext(), "selected $i", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = GoalFragment()
    }
}