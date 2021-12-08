package project.st991532818.org.models

import java.util.*

class PaymentReminder(/*name: String,*/
                      date: String = "",
                      isActive: Boolean = false,
                      repeat: Boolean = false,
                      amount: String = "",
                      payee: String = "",
                      category: String = "",
                        note: String = "") {
    //var name: String = ""
    var uid: String = "";
    var date: String = Date().toString()
    var category: String = ""
    var isActive: Boolean = false
    //todo implement a repeat frequency
    var repeat: Boolean = false
    var amount: String = "0.0"
    var payee: String = ""
    var note: String = ""

    init{
        //this.name = name
        this.date = date
        this.isActive = isActive
        this.repeat = repeat
        this.amount = amount
        this.payee = payee
        this.note = note
        this.category = category
    }
}