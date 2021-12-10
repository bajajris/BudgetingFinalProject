package project.st991532818.org.ui.home

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.google.firebase.firestore.*

/**
 * Name: 1. Raj Rajput, 2. Rishabh Bajaj
 * Student Id: 991550770, 991532818
 * Date: 2021-11-28
 * Description: Home fragment view model
 */
class HomeViewModel(private val ff: FirebaseFirestore) : ViewModel() {

    var expensesCollection = ff.collection("expenses")


    fun updateExpense(uid: String, amt: String, cat: String) {
        viewModelScope.launch {
            expensesCollection.document(
                uid
            ).update("amount", amt.toDouble(), "category", cat).addOnSuccessListener {
                Log.d("TAG", "Document Updated")
            }
        }
    }

    fun deleteExpense(uid: String) {
        viewModelScope.launch {
            expensesCollection.document(
                uid
            ).delete().addOnSuccessListener {
                Log.d("TAG", "Document Updated")
            }
        }
    }

}
    class HomeViewModelFactory(private val ff: FirebaseFirestore) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(ff) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")        }

    }
