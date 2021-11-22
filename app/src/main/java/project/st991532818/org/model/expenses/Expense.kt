package project.st991532818.org.model.expenses

class Expense (amount: Double, category: String) {
    var id: Int = 0;
    private var amount: Double = 0.0
    private var category: String = ""

    init{
        this.amount = amount
        this.category = category
    }

}