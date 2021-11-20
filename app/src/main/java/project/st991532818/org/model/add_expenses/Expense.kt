package project.st991532818.org.model.add_expenses

class Expense (id: Int, amount: Double, category: String) {
    var id: Int = 0;
    var amount: Double = 0.0
    var category: String = ""

    init{
        this.id = id
        this.amount = amount
        this.category = category
    }

}