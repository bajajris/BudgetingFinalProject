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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SavingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavingsFragment : Fragment() {
    private var _binding: FragmentSavingsBinding? = null
    private val binding get() = _binding!!
    private val savingsViewModel: SavingsViewModel by activityViewModels {
        SavingsViewModelFactory(
            FirebaseFirestore.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSavingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        binding.tvR.text = Integer.toString(25)
//        binding.tvCPP.text = Integer.toString(25)
//        binding.tvJava.text = Integer.toString(25)
//        binding.tvPython.text = Integer.toString(25)
//
//        binding.piechart.addPieSlice(
//            PieModel(
//                "R", binding.tvR.getText().toString().toFloat(),
//                Color.parseColor("#FFA726")
//            )
//        )
//        binding.piechart.addPieSlice(
//            PieModel(
//                "R", binding.tvPython.getText().toString().toFloat(),
//                Color.parseColor("#66BB6A")
//            )
//        )
//        binding.piechart.addPieSlice(
//            PieModel(
//                "R", binding.tvCPP.getText().toString().toFloat(),
//                Color.parseColor("#EF5350")
//            )
//        )
//        binding.piechart.addPieSlice(
//            PieModel(
//                "R", binding.tvJava.getText().toString().toFloat(),
//                Color.parseColor("#29B6F6")
//            )
//        )
        // Inflate the layout for this fragment
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
        spinnerMonth.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    savingsViewModel.checkBudgetExists(spinnerMonth.selectedItem.toString(), spinnerYear.selectedItem.toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
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
                    savingsViewModel.checkBudgetExists(spinnerMonth.selectedItem.toString(), spinnerYear.selectedItem.toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        savingsViewModel.budgetId.observe(viewLifecycleOwner, {
            if(it.isNullOrEmpty()){
                savingsViewModel.getAllExpenses(spinnerMonth.selectedItem.toString(), spinnerYear.selectedItem.toString())
                savingsViewModel.getBudget(savingsViewModel.budgetId.value)
                binding.cardViewGraph.visibility = View.GONE
                binding.cardViewDetails.visibility = View.GONE
                binding.budgetMessage.visibility = View.VISIBLE
            }else{

               savingsViewModel.getAllExpenses(spinnerMonth.selectedItem.toString(), spinnerYear.selectedItem.toString())
               savingsViewModel.getBudget(savingsViewModel.budgetId.value)
                binding.cardViewGraph.visibility = View.VISIBLE
                binding.cardViewDetails.visibility = View.VISIBLE
                binding.budgetMessage.visibility = View.GONE
            }
        })

        savingsViewModel.expenses.observe(viewLifecycleOwner, {
            var totalPrice = 0.0
            for(expense in it){
                totalPrice+=expense.amount
            }
        })

        savingsViewModel.budget.observe(viewLifecycleOwner, {

        })



    }
        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}