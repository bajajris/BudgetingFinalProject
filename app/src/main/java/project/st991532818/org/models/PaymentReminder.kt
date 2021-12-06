package project.st991532818.org.models

import java.util.*

class PaymentReminder(name: String,
                      dateTime: Date,
                      isActive: Boolean,
                      repeat: Boolean,
                      amount: Double,
                      payee: String) {
    var name: String = ""
    var dateTime: Date = Date()
    var isActive: Boolean = false
    //todo implement a repeat frequency
    var repeat: Boolean = false
    var amount: Double = 0.0
    var payee: String = ""

    init{
        this.name = name
        this.dateTime = dateTime
        this.isActive = isActive
        this.repeat = repeat
        this.amount = amount
        this.payee = payee
    }
}