package project.st991532818.org.models

class Expense (amount: Double = 0.0, category: String = "", month: String = "", year: Long = 0) {
    var uid: String = "";
    var amount: Double = 0.0
    var category: String = ""
    var month: String = ""
    var year: Long = 0

    init{
        this.amount = amount
        this.category = category
        this.month = month
        this.year =  year
    }




}