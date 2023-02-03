package com.peterchege.expensetrackerapp.domain.models

data class Expense (
    val expenseId:String,
    val expenseName:String,
    val expenseAmount:Int,
    val expenseCategoryId:String,

    val expenseCreatedAt:String,
    val expenseCreatedOn:String,

    val expenseUpdatedOn:String,
    val expenseUpdatedAt:String,

    )