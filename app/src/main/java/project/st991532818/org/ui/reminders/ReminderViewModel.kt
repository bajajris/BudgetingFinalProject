package project.st991532818.org.ui.reminders

import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import project.st991532818.org.models.PaymentReminder
import project.st991532818.org.ui.add_activity.AddActivityViewModel
import java.util.*

class ReminderViewModel(private val ff: FirebaseFirestore) : ViewModel() {

    var reminderCollection = ff.collection("reminders")
    private val _text = MutableLiveData<String>().apply {
        value = "You can see reminders list here"
    }
    val text: LiveData<String> = _text

    fun addNewReminder(date: String, category: String, payee: String, note: String, amount: String) {

        val newReminder = getNewReminder(date, category, payee, note, amount)
        insertReminder(newReminder)
    }

    fun isReminderEntryValid(date: Date, payee: String, category: String, amount:Double): Boolean {
        if (date.toString().isBlank() || payee.isBlank() || category.isBlank() || amount > 0) {
            return false
        }
        return true
    }

    private fun insertReminder(r: PaymentReminder) {
        // insert reminder into the firestore
        viewModelScope.launch {
            val rp: MutableMap<String, Any> = HashMap()
            rp["userid"] = FirebaseAuth.getInstance().currentUser?.uid.toString()
            rp["date"] = r.date
            rp["category"] = r.category
            rp["payee"] = r.payee
            rp["note"] = r.note
            rp["amount"] = r.amount
            // Add a new document with a generated ID
            reminderCollection
                .add(rp)
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

    private fun getNewReminder(date: String, category: String, payee: String, note: String, amount: String): PaymentReminder {
        return PaymentReminder(
            date = date,
            category = category,
            payee = payee,
            note = note,
            isActive = true,
            repeat = true,
            amount = amount
        )
    }
}

class ReminderViewModelFactory(private val ff: FirebaseFirestore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReminderViewModel(ff) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}