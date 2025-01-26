package id.calocallo.loanapp.data.repository

import id.calocallo.loanapp.data.network.RemoteLoanDataSource
import id.calocallo.loanapp.data.toLoan
import id.calocallo.loanapp.domain.Loan
import id.calocallo.mybookpedia.core.domain.DataError
import id.calocallo.mybookpedia.core.domain.Result
import id.calocallo.mybookpedia.core.domain.map

class DefaultLoanRepository(
    private val remoteLoanDataSource: RemoteLoanDataSource
) : LoanRepository {
    override suspend fun getLoanList(): Result<List<Loan>, DataError.Remote> {
        return remoteLoanDataSource.getLoanList().map { dto ->
            dto.map { it.toLoan() }
        }
    }
}