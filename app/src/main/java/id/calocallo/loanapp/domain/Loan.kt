package id.calocallo.loanapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Loan(
    val id: String,
    val amount: Int,
    val interestRate: Double,
    val term: Int,
    val purpose: String,
    val riskRating: String,
    val borrower: Borrower,
    val collateral: Collateral,
    val documents: List<Documents> = emptyList(),
    val repaymentSchedule: RepaymentSchedule
) : Parcelable

@Parcelize
data class Borrower(
    val id: String,
    val name: String,
    val email: String,
    val creditScore: Int
) : Parcelable

@Parcelize
data class Collateral(
    val type: String,
    val value: Int
) : Parcelable

@Parcelize
data class Documents(
    val type: String,
    val url: String
) : Parcelable

@Parcelize
data class RepaymentSchedule(
    val installment: List<Installment>
) : Parcelable

@Parcelize
data class Installment(
    val dueDate: String,
    val amountDue: Int
) : Parcelable
