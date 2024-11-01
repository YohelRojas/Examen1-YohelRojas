package cr.ac.utn.movil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.identities.sin_Sinpe

class sin_adapter(
    private val sinpeList: List<sin_Sinpe>,
    private val onItemClick: (sin_Sinpe) -> Unit
) : RecyclerView.Adapter<sin_adapter.SinpeViewHolder>() {

    class SinpeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtOriginPerson: TextView = itemView.findViewById(R.id.txt_origin_person)
        val txtDestinationName: TextView = itemView.findViewById(R.id.txt_destination_name)
        val txtAmount: TextView = itemView.findViewById(R.id.txt_amount)
        val txtDateTime: TextView = itemView.findViewById(R.id.txt_date_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinpeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sin_list_item, parent, false)
        return SinpeViewHolder(view)
    }

    override fun onBindViewHolder(holder: SinpeViewHolder, position: Int) {
        val sinpe = sinpeList[position]
        holder.txtOriginPerson.text = sinpe.sin_originPerson
        holder.txtDestinationName.text = sinpe.sin_destinationName
        holder.txtAmount.text = sinpe.sin_amount.toString()
        holder.txtDateTime.text = sinpe.sin_dateTime.toString()
        holder.itemView.setOnClickListener { onItemClick(sinpe) }
    }

    override fun getItemCount() = sinpeList.size
}
