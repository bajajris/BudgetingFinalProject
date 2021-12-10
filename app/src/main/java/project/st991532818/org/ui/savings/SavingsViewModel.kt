package project.st991532818.org.ui.savings

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await
import project.st991532818.org.models.Budget
import project.st991532818.org.models.Expense
import java.util.*
import kotlin.collections.ArrayList

/**
 * Name: Rishabh Bajaj
 * Student Id: 991532818
 * Date: 2021-12-04
 * Description: Savings fragment view model
 */
class SavingsViewModel(private val ff: FirebaseFirestore) : ViewModel() {

    var expensesCollection = ff.collection("expenses")
    var budgetCollection = ff.collection("budgets")
    private val _budgetId = MutableLiveData<String>().apply {
        value = ""
    }
    var budgetId: LiveData<String> = _budgetId

    // mutable live data for budget and expenses
    private val _budget = MutableLiveData<Budget>().apply {
        value = Budget()
    }

    private val _expenses = MutableLiveData<ArrayList<Expense>>().apply {
        value = ArrayList()
    }

    var budget: LiveData<Budget> = _budget

    var expenses: LiveData<ArrayList<Expense>> = _expenses

    fun checkBudgetExists(month: String, year: String) {
        viewModelScope.launch {
            budgetExistsLaunch(month, year)
        }
    }


    // check if budget exists
    private suspend fun budgetExistsLaunch(month: String, year: String) {
        var query = budgetCollection
            .whereEqualTo("month", month)
            .whereEqualTo("year", year.toInt())
            .whereEqualTo("userid", FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get().await()
        if (query.documents.isNotEmpty()) {
            _budgetId.value = query.documents[0].id
        } else {
            _budgetId.value = ""
        }
    }

    // get All expenses
    fun getAllExpenses(month: String, year: String) {
        viewModelScope.launch {
            getAllExpensesLaunch(month, year)
        }
    }

    // launch get all expenses
    suspend fun getAllExpensesLaunch(month: String, year: String){
        var query = expensesCollection
            .whereEqualTo("month", month)
            .whereEqualTo("year", year.toInt())
            .whereEqualTo("userid", FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get().await()
        _expenses.value = ArrayList()
        if(query.documents.isNotEmpty()){

            for(document in query.documents){
                _expenses.value?.add(Expense(
                    document.get("amount") as Double,
                    document.get("category") as String,
                    document.get("month") as String,
                    document.get("year") as Long
                ))

            }
            _expenses.value = _expenses.value

        }else{
            _expenses.value = ArrayList()
        }

    }

    // get budget from database
    fun getBudget(value: String?) {
        viewModelScope.launch {
            if (value != null) {
                getBudgetLaunch(value)
            }
        }
    }

    suspend fun getBudgetLaunch(value: String) {
        _budget.value = Budget()
        // get budget for data
        if(value.isNotEmpty()){
            var query = budgetCollection
                .document(value)
                .get()
                .await()
            if(query.id!=null){
                _budget.value = Budget(
                    query.get("amount") as Double,
                    query.get("month") as String,
                    query.get("year") as Long
                )

                _budget.value = _budget.value
            }else{
                _budget.value = Budget()
            }
        }


    }
}

// savings view model factory
class SavingsViewModelFactory(private val ff: FirebaseFirestore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavingsViewModel(ff) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")        }

}
