package id.calocallo.loanapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import id.calocallo.loanapp.R
import id.calocallo.loanapp.databinding.FragmentLoanDetailBinding
import id.calocallo.loanapp.domain.Loan

class LoanDetailFragment : Fragment() {
    private lateinit var binding: FragmentLoanDetailBinding

    private var installmentAdapter = LoanInstallmentAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoanDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        val loan = arguments?.getParcelable<Loan>("LOAN")
        with(binding) {
            loan?.let {
                tvLoanDetailName.text = it.borrower.name
                tvLoanDetailEmail.text = it.borrower.email
                tvLoanDetailCollateralType.text = "Collateral Type: ${it.collateral.type}"
                tvLoanDetailCollateralValue.text = "Collateral Value: $${it.collateral.value}"
                tvLoanDetailCreditScore.text = "Credit Score: ${it.borrower.creditScore}"

                // Installment
                installmentAdapter = LoanInstallmentAdapter(it.repaymentSchedule.installment)
                rvLoanDetailInstallment.adapter = installmentAdapter

                // Docs
                if (it.documents.isEmpty()) {
                    tvLoanDetailDocuments.text = "-"
                } else {
                    tvLoanDetailDocuments.apply {
                        text = "Open Document"
                        setOnClickListener {
                            val bundle = bundleOf("LOAN_DOCUMENTS" to loan.documents.firstOrNull())
                            findNavController().navigate(
                                R.id.action_loanDetailFragment_to_loanDocumentFragment,
                                bundle,
                            )
                        }
                    }
                }
            }
        }
    }
}
