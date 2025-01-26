package id.calocallo.loanapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.loanapp.R
import id.calocallo.loanapp.databinding.LayoutLoanItemBinding
import id.calocallo.loanapp.domain.Loan

class LoanListAdapter(
    private val loanList: List<Loan>,
) : RecyclerView.Adapter<LoanListAdapter.Holder>() {
    lateinit var onClick: (Loan) -> Unit

    fun setOnClickListener(onClick: (Loan) -> Unit) {
        this.onClick = onClick
    }

    inner class Holder(
        val binding: LayoutLoanItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loan: Loan) {
            with(binding) {
                tvLoanItemName.text =
                    root.context.getString(R.string.loan_list_name_value, loan.borrower.name)
                tvLoanItemAmount.text =
                    root.context.getString(R.string.loan_list_amount_value, loan.amount)
                tvLoanItemPurpose.text =
                    root.context.getString(R.string.loan_list_purpose_value, loan.purpose)
                tvLoanItemTermMonth.text =
                    root.context.getString(R.string.loan_list_term_value, loan.term)
                tvLoanItemRiskRating.text =
                    root.context.getString(R.string.loan_list_risk_rating_value, loan.riskRating)
                tvLoanItemInterestRate.text =
                    root.context.getString(
                        R.string.loan_list_interest_rate_value,
                        loan.interestRate,
                    )

                root.setOnClickListener { onClick(loan) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LoanListAdapter.Holder {
        val binding =
            LayoutLoanItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return Holder(binding)
    }

    override fun onBindViewHolder(
        holder: LoanListAdapter.Holder,
        position: Int,
    ) {
        holder.bind(loanList[position])
    }

    override fun getItemCount(): Int = loanList.size
}
