package project.st991532818.org.ui.add_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import project.st991532818.org.model.expenses.Expense

class AddActivityViewModel : ViewModel() {

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