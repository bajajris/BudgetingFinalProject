package project.st991532818.org.adapters

import android.R
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import project.st991532818.org.databinding.ListItemExpenseBinding
import project.st991532818.org.model.expenses.Expense
import java.util.*

class ExpensesAdapter(private val context: Context, private val dataSource: ArrayList<Expense>) : RecyclerView.Adapter<ExpensesAdapter.VH>() {


    private lateinit var binding: ListItemExpenseBinding
    class VH(binding: ListItemExpenseBinding) : RecyclerView.ViewHolder(binding.root){
        var expenseTypeLabel = binding.expenseTypeLabel
        var amount = binding.amount
        var category = binding.category
        var dateTime = binding.dateTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        binding = ListItemExpenseBinding.inflate(LayoutInflater.from(parent.context))
        return VH(binding)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val e = dataSource[position]
        var col:Int = 0;
        col = R.color.holo_red_light

        holder.expenseTypeLabel.background = context.getDrawable(col)
        holder.amount.text = e.amount.toString()
        holder.category.text = e.category
//        val dateTime : String = LocalDateTime.parse(e.createdAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME).format(
//            DateTimeFormatter.ofLocalizedDateTime(
//            FormatStyle.LONG, FormatStyle.SHORT)).toString()

        holder.dateTime.text = (e.month + e.year)// todo

//        holder.itemView.setOnClickListener {
////                updateData(e)
//        }
    }

//    private fun updateData(e:Expense) {

//
//    }


    override fun getItemCount() = dataSource.size

}