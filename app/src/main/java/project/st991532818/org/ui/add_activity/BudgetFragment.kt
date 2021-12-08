package project.st991532818.org.ui.add_activity

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
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.FirebaseFirestore
import project.st991532818.org.R
import project.st991532818.org.databinding.FragmentBudgetBinding
/**
 * Name: Rishabh Bajaj
 * Student Id: 991532818
 * Date: 2021-11-25
 * Description: Budget fragment to display add budget by month and year
 */
class BudgetFragment : Fragment() {

    // add activity view model
    private val addActivityViewModel: AddActivityViewModel by activityViewModels {
        AddActivityViewModelFactory(
            FirebaseFirestore.getInstance())
    }
    private var _binding: FragmentBudgetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView: TextView = binding.textBudgetExists

        //spinner to select month
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

        //spinner to select year
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

        // check if budget exists when month is changed
        spinnerMonth.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    checkExistingBudget(spinnerMonth.selectedItem.toString(), spinnerYear.selectedItem.toString())
                }
            }



            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }
        }
        // check if budget exists when year is changed

        spinnerYear.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    checkExistingBudget(spinnerMonth.selectedItem.toString(), spinnerYear.selectedItem.toString())
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }
        }
        // add budget click listener
        binding.btnAddBudget.setOnClickListener {
            Log.i("TEST",spinnerMonth.selectedItem.toString())
            addNewItem(spinnerMonth.selectedItem.toString(), spinnerYear.selectedItem.toString())
            checkExistingBudget(spinnerMonth.selectedItem.toString(), spinnerYear.selectedItem.toString())
        }
        //observe text to display if budget exists
        addActivityViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

    }
    // onViewCreated Ends here

    //check if budget exists for month/year
    private fun checkExistingBudget(month: String, year: String){
        addActivityViewModel.budgetExists(month, year)
    }
    //add new budget and call add method of view model
    private fun addNewItem(month: String, year: String) {
        if (isEntryValid()) {
            addActivityViewModel.addNewBudget(
                binding.editBudgetAmount.text.toString(),
                month,
                year
            )
            Toast.makeText(context, "Budget Added. Budget list is updated!!", Toast.LENGTH_SHORT).show()
            clearSelections()
        }else{
            Toast.makeText(context, "Invalid values", Toast.LENGTH_SHORT).show()
        }
    }

    // clear selections when added
    private fun clearSelections() {
        binding.editBudgetAmount.text.clear()
    }

    //check if entry valid
    private fun isEntryValid(): Boolean {
        return addActivityViewModel.isBudgetEntryValid(
            binding.editBudgetAmount.text.toString(),
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

