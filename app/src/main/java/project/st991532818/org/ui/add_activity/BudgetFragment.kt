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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import project.st991532818.org.R
import project.st991532818.org.databinding.FragmentAddactivityBinding
import project.st991532818.org.databinding.FragmentBudgetBinding

class BudgetFragment : Fragment() {

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
//        addActivityViewModel =
//            ViewModelProvider(this).get(AddActivityViewModel::class.java)

        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView: TextView = binding.textBudgetExists

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
                    checkExistingBudget(spinnerMonth.selectedItem.toString(), spinnerYear.selectedItem.toString())
                }
            }



            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }
        }

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
        binding.btnAddBudget.setOnClickListener {
            Log.i("TEST",spinnerMonth.selectedItem.toString())
            addNewItem(spinnerMonth.selectedItem.toString(), spinnerYear.selectedItem.toString())
            checkExistingBudget(spinnerMonth.selectedItem.toString(), spinnerYear.selectedItem.toString())
        }
        addActivityViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

    }
    // onViewCreated Ends here
    private fun checkExistingBudget(month: String, year: String){
        addActivityViewModel.budgetExists(month, year)
    }
    private fun addNewItem(month: String, year: String) {
        if (isEntryValid()) {
            addActivityViewModel.addNewBudget(
                binding.editBudgetAmount.text.toString(),
                month,
                year
            )
            Toast.makeText(context, "Budget Added. Budget list is updated!!", Toast.LENGTH_SHORT).show()
            clearSelections()
        }
    }

    private fun clearSelections() {
        binding.editBudgetAmount.text.clear()
    }

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

