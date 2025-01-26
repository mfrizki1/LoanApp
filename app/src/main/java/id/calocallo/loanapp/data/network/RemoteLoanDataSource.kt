package id.calocallo.loanapp.data.network

import id.calocallo.loanapp.data.dto.LoanListResponseDto
import id.calocallo.mybookpedia.core.domain.DataError
import id.calocallo.mybookpedia.core.domain.Result

interface RemoteLoanDataSource {
    suspend fun getLoanList(): Result<List<LoanListResponseDto>, DataError.Remote>
}