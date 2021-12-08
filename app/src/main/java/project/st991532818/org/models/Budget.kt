package project.st991532818.org.models

/**
 * Name: Raj Rajput
 * Student Id:
 * Date: 2021-11-20
 * Description: Budget model class
 */

class Budget (amount: Double = 0.0, month: String = "", year: Long = 0) {
    var id: String = "";
    var amount: Double = 0.0
    var month: String = ""
    var year: Long = 0

    init{
        this.amount = amount
        this.month = month
        this.year =  year
    }




}