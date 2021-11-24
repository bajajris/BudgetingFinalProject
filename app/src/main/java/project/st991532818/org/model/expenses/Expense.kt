package project.st991532818.org.model.expenses

import java.util.*

class Expense (amount: Double, category: String) {

    var amount: Double = 0.0
    var date: Date = Date()
    var category: String = ""

    init{
        this.amount = amount
        this.date = Date()
        this.category = category
    }

}