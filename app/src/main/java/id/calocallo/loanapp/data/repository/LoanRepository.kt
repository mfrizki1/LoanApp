package id.calocallo.loanapp.data.repository

import id.calocallo.loanapp.domain.Loan
import id.calocallo.mybookpedia.core.domain.DataError
import id.calocallo.mybookpedia.core.domain.Result

interface LoanRepository {
    suspend fun getLoanList(): Result<List<Loan>, DataError.Remote>

}