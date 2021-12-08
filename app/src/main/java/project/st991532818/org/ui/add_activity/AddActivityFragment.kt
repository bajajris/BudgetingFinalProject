package project.st991532818.org.ui.add_activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.FirebaseFirestore
import project.st991532818.org.databinding.FragmentAddactivityBinding

import com.google.android.material.tabs.TabLayoutMediator

/**
 * Name: Rishabh Bajaj
 * Student Id: 991532818
 * Date: 2021-11-22
 * Description: Add Activity fragment that displays two tabs Budgets and Expenses
 */
class AddActivityFragment : Fragment() {
    private val myArray = arrayOf(
        "Budget",
        "Expense"
    )

    // add activity view model
    private val addActivityViewModel: AddActivityViewModel by activityViewModels {
        AddActivityViewModelFactory(
            FirebaseFirestore.getInstance()
        )
    }
    private var _binding: FragmentAddactivityBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddactivityBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        // view pager adapter for tabbed layout
        val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
        viewPager.adapter = adapter

        //switching tabs
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = myArray[position]
        }.attach()
    }
    // onViewCreated Ends here

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

