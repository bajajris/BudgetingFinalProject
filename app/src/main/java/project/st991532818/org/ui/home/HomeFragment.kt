package project.st991532818.org.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.st991532818.org.R
import project.st991532818.org.databinding.FragmentHomeBinding
import project.st991532818.org.model.expenses.Expense
import project.st991532818.org.adapters.ExpensesAdapter
import com.google.firebase.firestore.*

class HomeFragment : Fragment() {

    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private var _expensesList = ArrayList<Expense>()
    private lateinit var _adapter : ExpensesAdapter

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        _adapter = ExpensesAdapter(requireContext(),_expensesList)
        // create recycler view
        val recyclerView = root.findViewById<RecyclerView>(R.id.expenseRecyclerView)
        // attach the adapter to recycler view
        recyclerView.adapter = _adapter


        recyclerView.setHasFixedSize(true)
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.setLayoutManager(llm)
        readData()
        _adapter.notifyDataSetChanged()
        _adapter.notifyDataSetChanged()

        return root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun readData(){
        fireStoreDatabase.collection("expenses")
            .get()
            .addOnCompleteListener {
                val result : StringBuffer = StringBuffer()
                if(it.isSuccessful){
                    for(document in it.result!!) {
                        val amount = document.data.getValue("amount").toString().toDouble()
                        val category = document.data.getValue("category").toString()
                        val month = document.data.getValue("month").toString()+" "
                        val year = document.data.getValue("year").toString().toInt()
                        val e = Expense(amount, category, month, year)
                        _expensesList.add(e)
                        _adapter.notifyDataSetChanged()
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}