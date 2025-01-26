package id.calocallo.loanapp.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoanListResponseDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("amount") val amount: Int? = null,
    @SerializedName("interestRate") val interestRate: Double? = null,
    @SerializedName("term") val term: Int? = null,
    @SerializedName("purpose") val purpose: String? = null,
    @SerializedName("riskRating") val riskRating: String? = null,
    @SerializedName("borrower") val borrower: BorrowerResponseDto,
    @SerializedName("collateral") val collateral: CollateralResponseDto,
    @SerializedName("documents") val documents: List<DocumentsResponseDto>,
    @SerializedName("repaymentSchedule") val repaymentSchedule: RepaymentScheduleResponseDto
) : Parcelable

@Parcelize
data class BorrowerResponseDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("creditScore") val creditScore: Int? = null
) : Parcelable

@Parcelize
data class CollateralResponseDto(
    @SerializedName("type") val type: String? = null,
    @SerializedName("value") val value: Int? = null
) : Parcelable

@Parcelize
data class DocumentsResponseDto(
    @SerializedName("type") val type: String? = null,
    @SerializedName("url") val url: String? = null
) : Parcelable


@Parcelize
data class RepaymentScheduleResponseDto(
    @SerializedName("installments") val installment: List<InstallmentResponseDto>
) : Parcelable

@Parcelize
data class InstallmentResponseDto(
    @SerializedName("dueDate") val dueDate: String? = null,
    @SerializedName("amountDue") val amountDue: Int? = null
) : Parcelable
