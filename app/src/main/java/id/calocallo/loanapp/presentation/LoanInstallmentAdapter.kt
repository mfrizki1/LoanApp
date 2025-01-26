package id.calocallo.loanapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.loanapp.R
import id.calocallo.loanapp.databinding.LayoutInstallmentItemBinding
import id.calocallo.loanapp.domain.Installment

class LoanInstallmentAdapter(
    private val installmentList: List<Installment>,
) : RecyclerView.Adapter<LoanInstallmentAdapter.Holder>() {
    inner class Holder(
        val binding: LayoutInstallmentItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(installment: Installment) {
            with(binding) {
                tvInstallmentItemDueDate.text =
                    root.context.getString(R.string.installment_due_date_value, installment.dueDate)
                tvInstallmentItemAmount.text =
                    root.context.getString(R.string.installment_amount_value)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): Holder {
        val binding =
            LayoutInstallmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = installmentList.size

    override fun onBindViewHolder(
        holder: Holder,
        position: Int,
    ) {
        holder.bind(installmentList[position])

        holder.binding.tvInstallmentItemName.text =
            holder.binding.root.context.getString(
                R.string.installment_number_value,
                position + 1,
            )
    }
}
