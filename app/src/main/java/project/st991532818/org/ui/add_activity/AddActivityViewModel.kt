package project.st991532818.org.ui.add_activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import project.st991532818.org.model.expenses.Expense
import kotlin.math.exp

class AddActivityViewModel : ViewModel() {

    private val TAG = "FIREBASE"
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    var text: LiveData<String> = _text

    fun updateText(t: String){
        _text.value = t
    }

    fun addNewExpense(expenseAmount: String, expenseCategory: String) {
        val newExpense = getNewExpenseEntry(expenseAmount, expenseCategory)
        insertExpense(newExpense)
    }

    private fun insertExpense(expense: Expense) {
        viewModelScope.launch {
            // TODO ("Add implementation to insert an expense into firestore")
            val e: MutableMap<String, Any> = HashMap()
            e["amount"] = expense.amount
            e["category"] = expense.category
            e["date"] = expense.date

            fireStoreDatabase.collection("expenses")
                .add(e)
                .addOnSuccessListener{
                    Log.d(TAG, "Added document")
                }
                .addOnFailureListener{
                    Log.w(TAG, "Error adding document")
                }
//            itemDao.insert(item)
        }
    }

    private fun getNewExpenseEntry(expenseAmount: String, expenseCategory: String): Expense {
        return Expense(
            amount = expenseAmount.toDouble(),
            category = expenseCategory
        )
    }
    fun isEntryValid(expenseAmount: String, expenseCategory: String): Boolean {
        if (expenseAmount.isBlank() || expenseCategory.isBlank()) {
            return false
        }
        return true
    }
}