package id.calocallo.loanapp.data.network

import id.calocallo.loanapp.data.dto.LoanListResponseDto
import id.calocallo.loanapp.utils.safeApiCall
import id.calocallo.mybookpedia.core.domain.DataError
import id.calocallo.mybookpedia.core.domain.Result

class RetrofitRemoteLoanDataSource(private val loanService: LoanService) : RemoteLoanDataSource {
    override suspend fun getLoanList(): Result<List<LoanListResponseDto>, DataError.Remote> {
        return safeApiCall {
            loanService.getLoanList()
        }
    }

}