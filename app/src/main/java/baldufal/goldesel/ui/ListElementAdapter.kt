import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import baldufal.goldesel.databinding.ListelementBinding
import baldufal.goldesel.model.ListElement

class ListElementAdapter(private val entries: ArrayList<ListElement>) :
    RecyclerView.Adapter<ListElementAdapter.ElementViewHolder>() {

    class ElementViewHolder(private val binding: ListelementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListElement) {
            binding.name.text = item.name
            binding.date.text = "12.03."
            binding.value.text = String.format("%.2f", item.cents / 100.0)
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