package project.st991532818.org.ui.add_expenses

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.whiteelephant.monthpicker.MonthPickerDialog
import project.st991532818.org.R
import project.st991532818.org.databinding.FragmentAddexpenseBinding
import java.util.*

class AddExpenseFragment : Fragment() {

    private lateinit var addExpenseViewModel: AddExpenseViewModel
    private var _binding: FragmentAddexpenseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addExpenseViewModel =
            ViewModelProvider(this).get(AddExpenseViewModel::class.java)

        _binding = FragmentAddexpenseBinding.inflate(inflater, container, false)
        val root: View = binding.root




        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val textView: TextView = binding.textMonth
        addExpenseViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val spinnerMonth = binding.monthSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.months_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerMonth.adapter = adapter
            }
        }

        val spinnerYear = binding.yearSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.year_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerYear.adapter = adapter
            }
        }

        spinnerMonth.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    addExpenseViewModel.updateText(parent.getItemAtPosition(position).toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }
        }

        binding.btnAddexpense.setOnClickListener {
            addNewItem()
        }

    }

    private fun addNewItem() {
        if (isEntryValid()) {
            addExpenseViewModel.addNewExpense(
                binding.editExpenseAmount.text.toString(),
                binding.editExpenseCategory.text.toString(),
            )
            Toast.makeText(context, "Expense Added. Expense list is updated!!", Toast.LENGTH_SHORT)
//            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
//            findNavController().navigate(action)
        }
    }

    private fun isEntryValid(): Boolean {
        return addExpenseViewModel.isEntryValid(
            binding.editExpenseAmount.text.toString(),
            binding.editExpenseCategory.text.toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

