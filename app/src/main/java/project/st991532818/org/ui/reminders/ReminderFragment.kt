package project.st991532818.org.ui.reminders

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import project.st991532818.org.R
import project.st991532818.org.databinding.FragmentHomeBinding
import project.st991532818.org.databinding.FragmentReminderBinding
import project.st991532818.org.databinding.ListItemExpenseBinding
import project.st991532818.org.databinding.ListItemReminderBinding
import project.st991532818.org.models.Expense
import project.st991532818.org.models.PaymentReminder
import project.st991532818.org.ui.home.HomeFragment
import project.st991532818.org.ui.home.HomeViewModel
import project.st991532818.org.ui.home.HomeViewModelFactory
import java.util.*
/**
 * Name: Raj Rajput
 * Student Id:
 * Date: 2021-12-7
 * Description: Reminder fragment to add reminders
 */
class ReminderFragment : Fragment() {
    // firebase adapter
    private lateinit var firebaseFirestore : FirebaseFirestore
    private lateinit var mFirestoreList: RecyclerView
    private lateinit var adapter : FirestoreRecyclerAdapter<PaymentReminder?, *>

    private val reminderViewModel: ReminderViewModel by activityViewModels {
        ReminderViewModelFactory(
            FirebaseFirestore.getInstance())
    }
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemListReminder: ListItemReminderBinding



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //reminderViewModel =
         //   ViewModelProvider(this).get(ReminderViewModel::class.java)

        //_binding = FragmentReminderBinding.inflate(inflater, container, false)


//        val textView: TextView = binding.textGallery
//        reminderViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })


        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        val root: View = binding.root


        firebaseFirestore = FirebaseFirestore.getInstance()
        mFirestoreList = binding.reminderRecyclerview

        //Query
        var query = firebaseFirestore.collection("reminders")
            .whereEqualTo("userid", FirebaseAuth.getInstance().currentUser?.uid.toString())


        //Recycler Options
        var options: FirestoreRecyclerOptions<PaymentReminder?> = FirestoreRecyclerOptions.Builder<PaymentReminder>()
            .setQuery(query, PaymentReminder::class.java)
            .build()

        adapter =
            object : FirestoreRecyclerAdapter<PaymentReminder?, ReminderViewHolder?>(options) {

                override fun onBindViewHolder(
                    holder: ReminderViewHolder,
                    position: Int,
                    model: PaymentReminder
                ) {
                    holder.category.text = model.category
                    holder.amount.text = model.amount.toString()
                    holder.payee.text = model.payee
                    holder.note.text = model.note
                    var col:Int = 0;
                    col = android.R.color.holo_green_light

                    holder.expenseTypeLabel.background = context?.getDrawable(col)
                    holder.dateTime.text = model.date//(model.month + " " + model.year)
                    holder.itemView.setOnClickListener{
                        var uid = snapshots.getSnapshot(position).id;
                        updateData(uid, getItem(position))
                    }
                }

                override fun onDataChanged() {
                    super.onDataChanged()
//                    if(itemCount==0){
//                        binding.noPaymentReminderMessage.visibility = View.VISIBLE
//                    }else{
//                        binding.noPaymentReminderMessage.visibility = View.GONE
//                    }
                }
                override fun onCreateViewHolder(group: ViewGroup, i: Int): ReminderFragment.ReminderViewHolder {
                    // Using a custom layout called R.layout.message for each item, we create a new instance of the viewholder
                    itemListReminder = ListItemReminderBinding.inflate(LayoutInflater.from(group.context))
                    return ReminderFragment.ReminderViewHolder(itemListReminder)
                }
            }

        mFirestoreList.setHasFixedSize(true)
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        mFirestoreList.layoutManager = llm
        mFirestoreList.adapter = adapter



        return root
    }

    private fun updateData(uid: String, item: PaymentReminder) {
        val builder = AlertDialog.Builder(context)

        var mView: View = LayoutInflater.from(context).inflate(R.layout.update_dialog_reminder, null)
        builder.setTitle("Update Reminder")

        builder.setView(mView)

        var dialog = builder.create()

        var dateUpdate = mView.findViewById<TextView>(R.id.editTextDateUpdate)
        dateUpdate.text = item.date
        var payeeUpdate = mView.findViewById<TextView>(R.id.editTextPayeeUpdate)
        payeeUpdate.text = item.payee
        var categoryUpdate = mView.findViewById<TextView>(R.id.editTextCategoryUpdate)
        categoryUpdate.text = item.category
        var amountUpdate = mView.findViewById<TextView>(R.id.editTextAmountUpdate)
        amountUpdate.text = item.amount.toString()
        var notesUpdate = mView.findViewById<TextView>(R.id.editTextNotesUpdate)
        notesUpdate.text = item.note

        mView.findViewById<Button>(R.id.updateButton).setOnClickListener{
            reminderViewModel.updateReminder(uid, dateUpdate.text.toString(), payeeUpdate.text.toString(), categoryUpdate.text.toString(), amountUpdate.text.toString(), notesUpdate.text.toString() )
            dialog.cancel()
        }
        mView.findViewById<Button>(R.id.deleteButton).setOnClickListener{
            reminderViewModel.deleteReminder(uid)
            dialog.cancel()
        }
        dialog.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addReminderButton.setOnClickListener{
            addNewReminder(binding.editTextDate.text.toString(), binding.editTextCategory.text.toString(), binding.editTextPayee.text.toString(), binding.editTextNotes.text.toString(), binding.editTextAmount.text.toString().toDouble())
            binding.editTextDate.setText("")
            binding.editTextCategory.setText("")
            binding.editTextPayee.setText("")
            binding.editTextNotes.setText("")
            binding.editTextAmount.setText("")
        }

        val newCalender = Calendar.getInstance()
        binding.select.setOnClickListener{
            val dialog = DatePickerDialog(
                requireContext(),
                { view, year, month, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    val newTime = Calendar.getInstance()
                    val time = TimePickerDialog(requireContext(),
                        { view, hourOfDay, minute ->
                            newDate[year, month, dayOfMonth, hourOfDay, minute] = 0
                            val tem = Calendar.getInstance()
                            Log.w("TIME", System.currentTimeMillis().toString() + "")
                            if (newDate.timeInMillis - tem.timeInMillis > 0) binding.editTextDate.setText(
                                newDate.time.toString()
                            ) else Toast.makeText(requireContext(), "Invalid time", Toast.LENGTH_SHORT)
                                .show()
                        }, newTime[Calendar.HOUR_OF_DAY], newTime[Calendar.MINUTE], true
                    )
                    time.show()
                }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(
                    Calendar.DAY_OF_MONTH
                )
            )

            dialog.datePicker.minDate = System.currentTimeMillis()
            dialog.show()
        }
    }

    private fun addNewReminder(date: String, category: String, payee: String,  notes: String, amount: Double) {
        reminderViewModel.addNewReminder(date,category,payee,notes,amount)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private class ReminderViewHolder(binding: ListItemReminderBinding) : RecyclerView.ViewHolder(binding.root) {
        var expenseTypeLabel = binding.expenseTypeLabel
        var amount = binding.amount
        var category = binding.category
        var dateTime = binding.dateTime
        var note = binding.note
        var payee = binding.payee
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


}