package id.calocallo.loanapp.data

import id.calocallo.loanapp.data.dto.BorrowerResponseDto
import id.calocallo.loanapp.data.dto.CollateralResponseDto
import id.calocallo.loanapp.data.dto.DocumentsResponseDto
import id.calocallo.loanapp.data.dto.InstallmentResponseDto
import id.calocallo.loanapp.data.dto.LoanListResponseDto
import id.calocallo.loanapp.data.dto.RepaymentScheduleResponseDto
import id.calocallo.loanapp.domain.Borrower
import id.calocallo.loanapp.domain.Collateral
import id.calocallo.loanapp.domain.Documents
import id.calocallo.loanapp.domain.Installment
import id.calocallo.loanapp.domain.Loan
import id.calocallo.loanapp.domain.RepaymentSchedule

fun LoanListResponseDto.toLoan(): Loan {
    return Loan(
        id = id.orEmpty(),
        amount = amount ?: 0,
        interestRate = interestRate ?: 0.0,
        term = term ?: 0,
        purpose = purpose.orEmpty(),
        riskRating = riskRating.orEmpty(),
        borrower = borrower.toBorrower(),
        collateral = collateral.toCollateral(),
        documents = documents.map { it.toDocuments() },
        repaymentSchedule = repaymentSchedule.toRepaymentSchedule()
    )
}

fun BorrowerResponseDto.toBorrower(): Borrower {
    return Borrower(
        id = id.orEmpty(),
        name = name.orEmpty(),
        email = email.orEmpty(),
        creditScore = creditScore ?: 0
    )
}

fun CollateralResponseDto.toCollateral(): Collateral {
    return Collateral(type = type.orEmpty(), value = value ?: 0)
}

fun DocumentsResponseDto.toDocuments(): Documents {
    return Documents(type = type.orEmpty(), url = url.orEmpty())
}

fun RepaymentScheduleResponseDto.toRepaymentSchedule(): RepaymentSchedule {
    return RepaymentSchedule(
        installment.map {
            it.toInstallment()
        }
    )
}

fun InstallmentResponseDto.toInstallment(): Installment {
    return Installment(dueDate = dueDate.orEmpty(), amountDue = amountDue ?: 0)
}