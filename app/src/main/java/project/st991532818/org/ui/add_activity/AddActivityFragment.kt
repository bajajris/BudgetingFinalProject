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


class AddActivityFragment : Fragment() {
    private val myArray = arrayOf(
        "Budget",
        "Expense"
    )

    private val addActivityViewModel: AddActivityViewModel by activityViewModels {
        AddActivityViewModelFactory(
            FirebaseFirestore.getInstance())
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

        val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
        viewPager.adapter = adapter

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

