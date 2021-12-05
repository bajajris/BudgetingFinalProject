package project.st991532818.org.model.expenses

class Expense (amount: Double = 0.0, category: String = "", month: String = "", year: Int = 0) {
    var uid: String = "";
    var amount: Double = 0.0
    var category: String = ""
    var month: String = ""
    var year: Int = 0

    init{
        this.amount = amount
        this.category = category
        this.month = month
        this.year =  year
    }




}