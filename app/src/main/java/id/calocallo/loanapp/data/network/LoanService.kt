package id.calocallo.loanapp.data.network

import id.calocallo.loanapp.data.dto.LoanListResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface LoanService {
    @GET("andreascandle/p2p_json_test/main/api/json/loans.json")
    suspend fun getLoanList(): Response<List<LoanListResponseDto>>
}