package baldufal.goldesel.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import baldufal.goldesel.R
import baldufal.goldesel.databinding.ListelementBinding
import baldufal.goldesel.model.Transaction
import baldufal.goldesel.model.TransactionType
import baldufal.goldesel.ui.list_display.TransactionLongClickListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class TransactionAdapter(
    private val entries: LiveData<List<Transaction>>,
    private val listener: TransactionLongClickListener
) :
    RecyclerView.Adapter<TransactionAdapter.ElementViewHolder>() {


    class ElementViewHolder(val binding: ListelementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val formatter = DateTimeFormatter.ofPattern("dd.MM")

        fun bind(item: Transaction) {
            binding.name.text = item.name
            binding.date.text = item.date.format(formatter)
            binding.value.text = String.format("%.2f€", item.cents / 100.0)
            if (item.essential) {
                binding.luxury.setImageResource(baldufal.goldesel.R.drawable.essential)
                binding.luxury.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.gold
                    )
                )
            } else {
                binding.luxury.setImageResource(R.drawable.luxury)
                binding.luxury.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.silver
                    )
                )
            }
            if (item.investment) {
                binding.consumption.setImageResource(R.drawable.investment)
                binding.consumption.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.gold
                    )
                )
            } else {
                binding.consumption.setImageResource(R.drawable.consumption)
                binding.consumption.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.silver
                    )
                )
            }
            if (item.notes.isNotEmpty())
                binding.tvNotes.text = item.notes

            if(item.tags.isNotEmpty()) {
                var tags = ""
                for (tag in item.tags)
                    tags += "$tag "
                binding.tvTags.text = tags
            }

            binding.tvDateAdded.text = "Date Added: " + item.dateAdded.toString()

            binding.root.setOnClickListener {
                if (binding.tvNotes.visibility == TextView.GONE) {
                    binding.tvNotes.visibility = TextView.VISIBLE
                    binding.tvTags.visibility = TextView.VISIBLE
                    binding.tvDateAdded.visibility = TextView.VISIBLE
                } else {
                    binding.tvNotes.visibility = TextView.GONE
                    binding.tvTags.visibility = TextView.GONE
                    binding.tvDateAdded.visibility = TextView.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ElementViewHolder =
        ElementViewHolder(
            ListelementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        holder.bind(
            entries.value?.get(position) ?: Transaction(
                "error",
                "error",
                0,
                LocalDateTime.now(),
                TransactionType.OTHER,
                investment = false,
                essential = false,
                depreciation = 0.0,
                dateAdded = LocalDateTime.now(),
                notes = "",
                tags = listOf()
            )
        )
        holder.binding.root.setOnLongClickListener{
            listener.onTransactionLongClick(it, entries.value?.get(position))
            true
        }
    }

    override fun getItemCount(): Int = entries.value?.size ?: 0

}