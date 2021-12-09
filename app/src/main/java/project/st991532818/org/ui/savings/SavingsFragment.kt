package project.st991532818.org.ui.savings

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.FirebaseFirestore
import project.st991532818.org.R
import project.st991532818.org.databinding.FragmentAddactivityBinding
import project.st991532818.org.databinding.FragmentSavingsBinding
import org.eazegraph.lib.models.PieModel
import project.st991532818.org.ui.home.HomeViewModel
import project.st991532818.org.ui.home.HomeViewModelFactory

/**
 * Name: Rishabh Bajaj
 * Student Id: 991532818
 * Date: 2021-12-04
 * Description: Savings fragment to display add expenses and analyze savings
 */
class SavingsFragment : Fragment() {
    private var _binding: FragmentSavingsBinding? = null
    private val binding get() = _binding!!
    private val savingsViewModel: SavingsViewModel by activityViewModels {
        SavingsViewModelFactory(
            FirebaseFirestore.getInstance()
        )
    }
    var totalExpenses = 0.0;
    var automotivePrice = 0.0;
    var hospitalPrice = 0.0;
    var entertainmentPrice = 0.0;
    var groceryPrice = 0.0;
    var educationPrice = 0.0;
    var totalBudgetPrice = 0.0
    var amountToCompare = 0.0
    var savingsAmount = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSavingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        // check budget exists when month changed
        spinnerMonth.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {

                    savingsViewModel.checkBudgetExists(
                        spinnerMonth.selectedItem.toString(),
                        spinnerYear.selectedItem.toString()
                    )
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
            }
        }


        // check budget exists when year changed
        spinnerYear.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {


                    savingsViewModel.checkBudgetExists(
                        spinnerMonth.selectedItem.toString(),
                        spinnerYear.selectedItem.toString()
                    )
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        // observe changes to budget id
        // if exists
        savingsViewModel.budgetId.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                binding.cardViewGraph.visibility = View.GONE
            } else {
                binding.cardViewGraph.visibility = View.VISIBLE
            }
            savingsViewModel.getBudget(savingsViewModel.budgetId.value)
            savingsViewModel.getAllExpenses(
                spinnerMonth.selectedItem.toString(),
                spinnerYear.selectedItem.toString()
            )
        })


        savingsViewModel.expenses.observe(viewLifecycleOwner, {
            totalExpenses = 0.0
            educationPrice = 0.0
            hospitalPrice = 0.0
            automotivePrice = 0.0
            groceryPrice = 0.0
            entertainmentPrice = 0.0
            amountToCompare = 0.0
            savingsAmount = 0.0
            binding.piechart.clearChart()
            for (expense in it) {
                totalExpenses += expense.amount
                when (expense.category) {
                    "Automotive" -> {
                        automotivePrice += expense.amount
                    }
                    "Education" -> {
                        educationPrice += expense.amount
                    }
                    "Grocery" -> {
                        groceryPrice += expense.amount
                    }
                    "Hospital" -> {
                        hospitalPrice += expense.amount
                    }
                    "Entertainment" -> {
                        entertainmentPrice += expense.amount
                    }
                }
            }
            binding.tvAutomotive.text = "$ $automotivePrice"
            binding.tvGrocery.text = "$ $groceryPrice"
            binding.tvEntertainment.text = "$ $entertainmentPrice"
            binding.tvEducation.text = "$ $educationPrice"
            binding.tvHospital.text = "$ $hospitalPrice"


            if (totalExpenses > totalBudgetPrice && totalBudgetPrice > 0) {
                binding.budgetMessage.text =
                    "Expenses are greater than the budget\nExpense Analysis with respect to total expenses"
                amountToCompare = totalExpenses
            } else if (totalExpenses <= totalBudgetPrice && totalBudgetPrice > 0) {
                binding.budgetMessage.text = "Expense Analysis with respect to total budget"
                amountToCompare = totalBudgetPrice
                savingsAmount = totalBudgetPrice - totalExpenses

            }
            binding.tvSavings.text = "$ $savingsAmount"

            var entertainmentPieData =
                (entertainmentPrice / amountToCompare * 100).toFloat()
            var automotivePieData =
                (automotivePrice / amountToCompare * 100).toFloat()
            var hospitalPieData =
                (hospitalPrice / amountToCompare * 100).toFloat()
            var groceryPieData =
                (groceryPrice / amountToCompare * 100).toFloat()
            var educationPieData =
                (educationPrice / amountToCompare * 100).toFloat()
            var savingsPieData =
                (savingsAmount / amountToCompare * 100).toFloat()

            if (entertainmentPieData > 0) {
                binding.piechart.addPieSlice(
                    PieModel(
                        "Entertainment",
                        entertainmentPieData,
                        Color.parseColor("#C930E3")
                    )
                )
            }
            if (hospitalPieData > 0) {
                binding.piechart.addPieSlice(
                    PieModel(
                        "Hospital",
                        hospitalPieData,
                        Color.parseColor("#29B6F6")
                    )
                )
            }

            if (educationPieData > 0) {
                binding.piechart.addPieSlice(
                    PieModel(
                        "Education",
                        educationPieData,
                        Color.parseColor("#66BB6A")
                    )
                )
            }
            if (groceryPieData > 0) {
                binding.piechart.addPieSlice(
                    PieModel(
                        "Grocery",
                        groceryPieData,
                        Color.parseColor("#EF5350")
                    )
                )
            }
            if (automotivePieData > 0) {
                binding.piechart.addPieSlice(
                    PieModel(
                        "Automotive",
                        automotivePieData,
                        Color.parseColor("#FFA726")
                    )
                )
            }
            if (savingsPieData > 0) {
                binding.piechart.addPieSlice(
                    PieModel(
                        "Savings",
                        savingsPieData,
                        Color.parseColor("#03395C")
                    )
                )
            }
        })

        savingsViewModel.budget.observe(viewLifecycleOwner, {
            totalBudgetPrice = 0.0
            totalBudgetPrice = it.amount
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}