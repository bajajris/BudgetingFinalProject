package project.st991532818.org.model.expenses

class Expense (amount: Double, category: String, month: String, year: Int) {
    var id: String = "";
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