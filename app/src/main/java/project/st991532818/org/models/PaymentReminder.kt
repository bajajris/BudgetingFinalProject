package project.st991532818.org.models

import java.util.*
/**
 * Name: Raj
 * Student Id: 991550770
 * Date: 2021-11-20
 * Description: Budget model class
 */
class PaymentReminder(
                      date: String = "",
                      isActive: Boolean = false,
                      repeat: Boolean = false,
                      amount: Double = 0.0,
                      payee: String = "",
                      category: String = "",
                        note: String = "") {
    var uid: String = "";
    var date: String = Date().toString()
    var category: String = ""
    var isActive: Boolean = false
    var repeat: Boolean = false
    var amount: Double = 0.0
    var payee: String = ""
    var note: String = ""

    init{
        this.date = date
        this.isActive = isActive
        this.repeat = repeat
        this.amount = amount
        this.payee = payee
        this.note = note
        this.category = category
    }
}