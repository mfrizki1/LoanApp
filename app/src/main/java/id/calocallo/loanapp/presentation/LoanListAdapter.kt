package id.calocallo.loanapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.loanapp.databinding.LayoutLoanItemBinding
import id.calocallo.loanapp.domain.Loan

class LoanListAdapter(
    private val loanList: List<Loan>
) : RecyclerView.Adapter<LoanListAdapter.Holder>() {
    lateinit var onClick: (Loan) -> Unit

    fun setOnClickListener(onClick: (Loan) -> Unit) {
        this.onClick = onClick
    }

    inner class Holder(val binding: LayoutLoanItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loan: Loan) {
            with(binding) {
                tvLoanItemName.text = "Name: ${loan.borrower.name}"
                tvLoanItemAmount.text = "Amount: ${loan.amount}"
                tvLoanItemPurpose.text = "Purpose: ${loan.purpose}"
                tvLoanItemTermMonth.text = "Term: ${loan.term} Month"
                tvLoanItemRiskRating.text = "Risk Rating: ${loan.riskRating}"
                tvLoanItemInterestRate.text = "Interest Rate: ${loan.interestRate}"

                root.setOnClickListener { onClick(loan) }
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanListAdapter.Holder {
        val binding = LayoutLoanItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: LoanListAdapter.Holder, position: Int) {
        holder.bind(loanList[position])
    }

    override fun getItemCount(): Int = loanList.size
}