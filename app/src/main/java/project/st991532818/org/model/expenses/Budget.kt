package project.st991532818.org.model.expenses

class Budget (amount: Double, month: String, year: Int) {
    var id: String = "";
    var amount: Double = 0.0
    var month: String = ""
    var year: Int = 0

    init{
        this.amount = amount
        this.month = month
        this.year =  year
    }




}