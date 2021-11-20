package project.st991532818.org.ui.add_expenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddExpenseViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    var text: LiveData<String> = _text

    fun updateText(t: String){
        _text.value = t
    }
}