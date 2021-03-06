package project.st991532818.org.ui.add_activity

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import project.st991532818.org.models.Expense

import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await
import project.st991532818.org.models.Budget


/**
 * Name: Rishabh Bajaj
 * Student Id: 991532818
 * Date: 2021-11-22
 * Description: Add Activity view model to do database operations on firestore collections
 */
class AddActivityViewModel(private val ff: FirebaseFirestore) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is a Fragment"
    }
    var text: LiveData<String> = _text
    var budgetExists = false
    var budgetCollection = ff.collection("budgets")
    var expensesCollection = ff.collection("expenses")

    lateinit var docId: String

    //method to execute insert new expense into firestore
    fun addNewExpense(expenseAmount: String, expenseCategory: String, month: String, year: String) {
        val newExpense = getNewExpenseEntry(expenseAmount, expenseCategory, month, year)
        insertExpense(newExpense)
    }
    //method to execute insert new budget into firestore

    fun addNewBudget(budgetAmount: String, month: String, year: String) {
        val newBudget = getNewBudgetEntry(budgetAmount, month, year)
        insertBudget(newBudget)
    }

    //method to check if budget exists in firestore

    fun budgetExists(month: String, year: String){
        viewModelScope.launch {
            budgetExistsLaunch(month,year)
        }
    }
    //method to check if budget exists in firestore

    private suspend fun budgetExistsLaunch(month: String, year: String){
        var query = budgetCollection
            .whereEqualTo("month", month)
            .whereEqualTo("year", year.toInt())
            .whereEqualTo("userid", FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get().await()
        if(query.documents.isNotEmpty()){
            _text.value = "Budget Already Exists for $month, $year: ${query.documents[0].get("amount")}"
            docId = query.documents[0].id
            budgetExists = true
        }else{
            _text.value = "No Budget for $month, $year"
            docId = ""
            budgetExists = false
        }
    }
    //method to create a new expense and insert into firestore add display success

    private fun insertExpense(expense: Expense) {
        viewModelScope.launch {
            val myExpense: MutableMap<String, Any> = HashMap()
            myExpense["userid"] = FirebaseAuth.getInstance().currentUser?.uid.toString()
            myExpense["year"] = expense.year
            myExpense["month"] = expense.month
            myExpense["amount"] = expense.amount
            myExpense["category"] = expense.category
            // Add a new document with a generated ID
            expensesCollection
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

    //method to create a new budget and insert into firestore add display success
    // if budget exists update it
    private fun insertBudget(budget: Budget) {
        if(budgetExists){
            viewModelScope.launch {
                //updating document amount
                budgetCollection.document(
                   docId
                ).update("amount", budget.amount).addOnSuccessListener {
                    Log.d("TAG", "Document Updated")
                }
            }
        }else{
            viewModelScope.launch {
                val myBudget: MutableMap<String, Any> = HashMap()
                myBudget["userid"] = FirebaseAuth.getInstance().currentUser?.uid.toString()
                myBudget["year"] = budget.year
                myBudget["month"] = budget.month
                myBudget["amount"] = budget.amount

                // Add a new document with a generated ID
                budgetCollection
                    .add(myBudget)
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

    }

    // get expense created object
    private fun getNewExpenseEntry(expenseAmount: String, expenseCategory: String, month: String, year: String): Expense {
        return Expense(
            amount = expenseAmount.toDouble(),
            category = expenseCategory,
            month = month,
            year = year.toLong()
        )
    }
    // get budget created object

    private fun getNewBudgetEntry(budgetAmount: String, month: String, year: String): Budget {
        return Budget(
            amount = budgetAmount.toDouble(),
            month = month,
            year = year.toLong()
        )
    }

    // check if expense entry valid

    fun isExpenseEntryValid(expenseAmount: String, expenseCategory: String): Boolean {
        if (expenseAmount.isBlank() || expenseCategory.isBlank()) {
            return false
        }
        return true
    }

    // check if budget entry valid
    fun isBudgetEntryValid(budgetAmount: String): Boolean {
        if (budgetAmount.isBlank()) {
            return false
        }
        return true
    }
}
// view model factory
class AddActivityViewModelFactory(private val ff: FirebaseFirestore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddActivityViewModel(ff) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}