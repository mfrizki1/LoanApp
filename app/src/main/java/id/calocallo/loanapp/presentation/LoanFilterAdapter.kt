package id.calocallo.loanapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.loanapp.R
import id.calocallo.loanapp.databinding.LayoutLoanFilterItemBinding

class LoanFilterAdapter(
    private val filterList: List<String>,
) : RecyclerView.Adapter<LoanFilterAdapter.Holder>() {
    private var selectedItemPosition: Int = -1
    var selectedItem: String? = ""

    lateinit var onClick: (String) -> Unit

    fun setOnClickItem(mOnClick: (String) -> Unit) {
        this.onClick = mOnClick
    }

    inner class Holder(
        private val binding: LayoutLoanFilterItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            with(binding) {
                tvLoanFilter.text = item

                if (selectedItem == null) {
                    selectedItemPosition = -1
                }

                if (selectedItem == item) {
                    selectedItemPosition = adapterPosition
                }

                root.setOnClickListener {
                    onClick(item)
                    val previousItem = selectedItemPosition
                    selectedItemPosition = adapterPosition
                    selectedItem = item
                    notifyItemChanged(previousItem)
                    notifyItemChanged(selectedItemPosition)
                }

                if (selectedItemPosition == adapterPosition) {
                    root.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            com.google.android.material.R.color.design_default_color_primary,
                        ),
                    )
                    tvLoanFilter.setTextColor(root.context.getColor(R.color.white))
                } else {
                    root.setCardBackgroundColor(
                        ContextCompat.getColor(root.context, android.R.color.white),
                    )
                    tvLoanFilter.setTextColor(root.context.getColor(R.color.black))
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): Holder {
        val binding =
            LayoutLoanFilterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return Holder(binding)
    }

    override fun getItemCount(): Int = filterList.size

    override fun onBindViewHolder(
        holder: Holder,
        position: Int,
    ) {
        holder.bind(filterList[position])
    }
}
