import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import baldufal.goldesel.databinding.ListelementBinding
import baldufal.goldesel.model.Transaction
import java.time.format.DateTimeFormatter

class TransactionAdapter(private val entries: MutableList<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.ElementViewHolder>() {

    class ElementViewHolder(private val binding: ListelementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val formatter = DateTimeFormatter.ofPattern("dd.MM")

        fun bind(item: Transaction) {
            binding.name.text = item.name
            binding.date.text = item.date.format(formatter)
            binding.value.text = String.format("%.2f€", item.cents / 100.0)
            if(item.essential) {
                binding.essential.text = "essential"
            }else{
                binding.essential.text = "luxury"
            }
            if(item.investment) {
                binding.investment.text = "investment"
            }else{
                binding.investment.text = "consumption"
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
        holder.bind(entries[position])
    }

    override fun getItemCount(): Int = entries.size

}