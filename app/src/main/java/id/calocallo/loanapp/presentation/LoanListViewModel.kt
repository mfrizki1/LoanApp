package id.calocallo.loanapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.calocallo.loanapp.data.repository.LoanRepository
import id.calocallo.loanapp.domain.Loan
import id.calocallo.loanapp.utils.toErrorText
import id.calocallo.mybookpedia.core.domain.onError
import id.calocallo.mybookpedia.core.domain.onSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoanListViewModel(
    private val loanRepository: LoanRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(LoanListState())
    val state =
        _state
            .onStart { observeLoanList() }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                _state.value,
            )

    private val _stateSort = MutableStateFlow(LoanSortState())
    val stateSort =
        _stateSort
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _stateSort.value)

    private var originalLoanList = emptyList<Loan>()

    private val MIN_SEARCH_CHARACTERS = 3

    fun observeLoanList() =
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }

            loanRepository
                .getLoanList()
                .onSuccess { loanList ->
                    originalLoanList = loanList
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            loanResult = loanList,
                        )
                    }
                }.onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.toErrorText(),
                        )
                    }
                }
        }

    fun searchLoans(query: String) {
        _state.update {
            it.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            if (query.length < MIN_SEARCH_CHARACTERS) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        loanResult = originalLoanList,
                        errorMessage = "Min $MIN_SEARCH_CHARACTERS characters",
                    )
                }
                return@launch
            }

            delay(1500)
            val filteredList =
                originalLoanList.filter { loan ->
                    loan.borrower.name.contains(query, ignoreCase = true)
                }

            _state.update {
                it.copy(
                    isLoading = false,
                    loanResult = filteredList,
                    errorMessage = null,
                )
            }
        }
    }

    fun sortedLoan(sort: String) {
        _state.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            delay(1500)
            val filteredList =
                when (sort) {
                    LoanSort.AMOUNT.name -> originalLoanList.sortedBy { it.amount }
                    LoanSort.TERM.name -> originalLoanList.sortedBy { it.term }
                    LoanSort.PURPOSE.name -> originalLoanList.sortedBy { it.purpose }
                    else -> originalLoanList
                }

            _state.update {
                it.copy(
                    isLoading = false,
                    loanResult = filteredList,
                    errorMessage = null,
                )
            }
        }
    }

    fun selectSortLoan(sort: String) {
        _stateSort.update { it.copy(sort = sort) }
    }

    fun clearSortLoan() {
        _stateSort.update { it.copy(sort = "") }
    }
}

data class LoanListState(
    val isLoading: Boolean = true,
    val loanResult: List<Loan> = emptyList(),
    val errorMessage: String? = null,
)

data class LoanSortState(
    val sort: String = "",
)

enum class LoanSort {
    AMOUNT,
    TERM,
    PURPOSE,
}
