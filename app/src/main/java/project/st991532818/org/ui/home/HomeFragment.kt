package project.st991532818.org.ui.home

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import project.st991532818.org.R
import project.st991532818.org.databinding.FragmentHomeBinding
import project.st991532818.org.model.expenses.Expense
import com.google.firebase.firestore.*
import project.st991532818.org.databinding.ListItemExpenseBinding


class HomeFragment : Fragment() {

    private lateinit var firebaseFirestore : FirebaseFirestore
    private lateinit var mFirestoreList: RecyclerView
//    private var _expensesList = ArrayList<Expense>()
    private lateinit var adapter : FirestoreRecyclerAdapter<*, *>

    private val homeViewModel: HomeViewModel by activityViewModels {
     HomeViewModelFactory(
            FirebaseFirestore.getInstance())
    }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemListbinding: ListItemExpenseBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        firebaseFirestore = FirebaseFirestore.getInstance()
        mFirestoreList = binding.expenseRecyclerView

        //Query
        var query = firebaseFirestore.collection("expenses")
            .whereEqualTo("userid", FirebaseAuth.getInstance().currentUser?.uid.toString())


        //Recycler Options
        var options: FirestoreRecyclerOptions<Expense?> = FirestoreRecyclerOptions.Builder<Expense>()
            .setQuery(query, Expense::class.java)
            .build()

        adapter =
            object : FirestoreRecyclerAdapter<Expense?, ExpenseViewHolder?>(options) {

                override fun onBindViewHolder(
                    holder: ExpenseViewHolder,
                    position: Int,
                    model: Expense
                ) {
                    holder.category.text = model.category
                    holder.amount.text = model.amount.toString()
                    var col:Int = 0;
                    col = android.R.color.holo_red_light

                    holder.expenseTypeLabel.background = context?.getDrawable(col)
                    holder.dateTime.text = (model.month + " " + model.year)
                    holder.itemView.setOnClickListener{
                       var uid = snapshots.getSnapshot(position).id;
                        updateData(uid, getItem(position))
                    }

                }

                override fun onCreateViewHolder(group: ViewGroup, i: Int): ExpenseViewHolder {
                    // Using a custom layout called R.layout.message for each item, we create a new instance of the viewholder
                    itemListbinding = ListItemExpenseBinding.inflate(LayoutInflater.from(group.context))
                    return ExpenseViewHolder(itemListbinding)
                }
            }

        mFirestoreList.setHasFixedSize(true)
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        mFirestoreList.layoutManager = llm
        mFirestoreList.adapter = adapter
        return root
    }

    private fun updateData(uid: String, item: Expense) {
        val builder = AlertDialog.Builder(context)

        var mView: View = LayoutInflater.from(context).inflate(R.layout.update_dialog, null)
        builder.setTitle("Update Item")

        builder.setView(mView)

        var dialog = builder.create()

        val spinnerActivityCategory = mView.findViewById<Spinner>(R.id.update_expense_category)
        // Create an ArrayAdapter using the string array and a default spinner layout
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.category_array,
                // todo change this array to dynamic array (user can add manage its items)
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerActivityCategory.adapter = adapter
            }
        }

        spinnerActivityCategory.setSelection(
            (spinnerActivityCategory.adapter as ArrayAdapter<String?>).getPosition(
                item.category.toString()
            )
        )

        var amountEditText = mView.findViewById<EditText>(R.id.update_expense_amount)
            amountEditText.setText(item.amount.toString())

        mView.findViewById<Button>(R.id.btn_update_expense).setOnClickListener {
            homeViewModel.updateExpense(uid, amountEditText.text.toString(), spinnerActivityCategory.selectedItem.toString())
            dialog.cancel()
        }

        mView.findViewById<Button>(R.id.btn_delete_expense).setOnClickListener {
            homeViewModel.deleteExpense(uid)
            dialog.cancel()
        }
        //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

//        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
//            Toast.makeText(context,
//                android.R.string.ok, Toast.LENGTH_SHORT).show()
//        }
//
//        builder.setNegativeButton(android.R.string.cancel) { _, _ ->
//            Toast.makeText(context,
//                android.R.string.cancel, Toast.LENGTH_SHORT).show()
//        }
//
//        builder.setNeutralButton("Maybe") { dialog, which ->
//            Toast.makeText(context,
//                "Maybe", Toast.LENGTH_SHORT).show()
//        }
        dialog.show()
    }

    private class ExpenseViewHolder(binding: ListItemExpenseBinding) : RecyclerView.ViewHolder(binding.root) {
        var expenseTypeLabel = binding.expenseTypeLabel
        var amount = binding.amount
        var category = binding.category
        var dateTime = binding.dateTime
    }

/*
@RequiresApi(Build.VERSION_CODES.N)
private fun readData(){
firebaseFirestore.collection("expenses")
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
adapter.notifyDataSetChanged()
}
}
}
}
*/


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}