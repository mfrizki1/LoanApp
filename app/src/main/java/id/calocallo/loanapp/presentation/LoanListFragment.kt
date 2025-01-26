package id.calocallo.loanapp.presentation

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.calocallo.loanapp.R
import id.calocallo.loanapp.databinding.FragmentLoanListBinding
import id.calocallo.loanapp.databinding.LayoutLoanSortBottomDialogBinding
import id.calocallo.loanapp.domain.Loan
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoanListFragment : Fragment() {
    private lateinit var binding: FragmentLoanListBinding

    private val loanListViewModel: LoanListViewModel by viewModel()

    private var loanAdapter = LoanListAdapter(emptyList())

    private var loanList = emptyList<Loan>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoanListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.svLoanList.apply {
            setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            loanListViewModel.searchLoans(it)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let {
                            loanListViewModel.searchLoans(it)
                        }

                        if (newText.isNullOrEmpty()) {
                            loanListViewModel.observeLoanList()
                        }
                        return true
                    }
                },
            )
        }
        binding.cvLoanListFilter.setOnClickListener {
            showSortBottomDialog()
        }

        loanListViewModel.observeLoanList()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loanListViewModel.state.collect { state ->
                    with(binding) {
                        when {
                            state.isLoading -> {
                                progressBar.isVisible = true
                                rvLoanList.isVisible = false
                                tvLoanListErrorMessage.isVisible = false
                            }

                            state.errorMessage != null -> {
                                progressBar.isVisible = false
                                rvLoanList.isVisible = false
                                tvLoanListErrorMessage.apply {
                                    isVisible = true
                                    text = state.errorMessage
                                }
                            }

                            else -> {
                                progressBar.isVisible = false
                                loanList = state.loanResult
                                loanAdapter = LoanListAdapter(loanList)
                                rvLoanList.apply {
                                    isVisible = true
                                    adapter = loanAdapter
                                }
                                loanAdapter.setOnClickListener { loan ->
                                    val bundle = bundleOf("LOAN" to loan)
                                    findNavController().navigate(
                                        R.id.action_loanListFragment_to_loanDetailFragment,
                                        bundle,
                                    )
                                }
                                tvLoanListErrorMessage.isVisible = false

                                /*if (!state.isSelectSort) {
                                    cvLoanListFilter.setCardBackgroundColor(
                                        ContextCompat.getColor(
                                            requireContext(),
                                            com.google.android.material.R.color.design_default_color_primary
                                        )
                                    )
                                    ivLoanListFilter.imageTintList = ColorStateList.valueOf(
                                        ContextCompat.getColor(
                                            requireContext(), R.color.white
                                        )
                                    )
                                } else {
                                    cvLoanListFilter.setCardBackgroundColor(
                                        ContextCompat.getColor(
                                            requireContext(), R.color.white
                                        )
                                    )
                                    ivLoanListFilter.imageTintList = ColorStateList.valueOf(
                                        ContextCompat.getColor(
                                            requireContext(), R.color.black
                                        )
                                    )
                                }*/
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loanListViewModel.stateSort.collect { state ->
                    with(binding) {
                        if (state.sort.isNotEmpty()) {
                            cvLoanListFilter.setCardBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    com.google.android.material.R.color.design_default_color_primary,
                                ),
                            )
                            ivLoanListFilter.imageTintList =
                                ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.white,
                                    ),
                                )
                        } else {
                            cvLoanListFilter.setCardBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white,
                                ),
                            )
                            ivLoanListFilter.imageTintList =
                                ColorStateList.valueOf(
                                    ContextCompat.getColor(requireContext(), R.color.black),
                                )
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showSortBottomDialog() {
        val filterAdapter =
            LoanFilterAdapter(
                listOf(LoanSort.AMOUNT.name, LoanSort.TERM.name, LoanSort.PURPOSE.name),
            )

        val dialog = BottomSheetDialog(binding.root.context)
        val bindingView =
            LayoutLoanSortBottomDialogBinding.inflate(
                LayoutInflater.from(binding.root.context),
            )
        dialog.setContentView(bindingView.root)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.setCancelable(true)
        dialog.show()

        bindingView.apply {
            rvLoanSortItem.adapter = filterAdapter
            filterAdapter.setOnClickItem { sort ->
                loanListViewModel.selectSortLoan(sort)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    loanListViewModel.stateSort.collect { state ->
                        val sort = state.sort
                        if (sort == "") {
                            filterAdapter.selectedItem = null
                        } else {
                            filterAdapter.selectedItem = sort
                        }
                        filterAdapter.notifyDataSetChanged()

                        btnLoanSortSelect.apply {
                            setOnClickListener {
                                dialog.dismiss()
                                loanListViewModel.sortedLoan(sort = state.sort)
                            }
                        }

                        ivLoanFilterClear.apply {
                            isVisible = state.sort.isNotEmpty()
                            setOnClickListener {
                                loanListViewModel.clearSortLoan()
                            }
                        }
                    }
                }
            }
        }
    }
}
