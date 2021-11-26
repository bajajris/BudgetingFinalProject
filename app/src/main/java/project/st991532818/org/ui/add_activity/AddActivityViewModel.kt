package project.st991532818.org.ui.add_activity

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import project.st991532818.org.model.expenses.Expense
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import com.google.firebase.firestore.DocumentReference

import com.google.android.gms.tasks.OnSuccessListener




class AddActivityViewModel(private val ff: FirebaseFirestore) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    var text: LiveData<String> = _text

    fun updateText(t: String){
        _text.value = t
    }

    fun addNewExpense(expenseAmount: String, expenseCategory: String, month: String, year: String) {
        val newExpense = getNewExpenseEntry(expenseAmount, expenseCategory, month, year)
        insertExpense(newExpense)
    }

    private fun insertExpense(expense: Expense) {
        viewModelScope.launch {
            // TODO ("Add implementation to insert an expense into firestore")
//            itemDao.insert(item)
            // Create a new user with a first and last name
            // Create a new user with a first and last name
            val myExpense: MutableMap<String, Any> = HashMap()
            myExpense["year"] = expense.year
            myExpense["month"] = expense.month
            myExpense["amount"] = expense.amount
            myExpense["category"] = expense.category

            // Add a new document with a generated ID

            // Add a new document with a generated ID
            ff.collection("expenses")
                .add(myExpense)
                .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                    Log.d(
                        "TAG",
                        "DocumentSnapshot added with ID: " + documentReference.id
                    )
                })
                .addOnFailureListener(OnFailureListener { e ->
                    Log.w(
                        "TAG",
                        "Error adding document",
                        e
                    )
                })
        }
    }

    private fun getNewExpenseEntry(expenseAmount: String, expenseCategory: String, month: String, year: String): Expense {
        return Expense(
            amount = expenseAmount.toDouble(),
            category = expenseCategory,
            month = month,
            year = year.toInt()
        )
    }
    fun isEntryValid(expenseAmount: String, expenseCategory: String): Boolean {
        if (expenseAmount.isBlank() || expenseCategory.isBlank()) {
            return false
        }
        return true
    }
}

class AddActivityViewModelFactory(private val ff: FirebaseFirestore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddActivityViewModel(ff) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}