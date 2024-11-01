package cr.ac.utn.movil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.appmovil.data.MemoryManager
import cr.ac.utn.appmovil.identities.sin_Sinpe
import cr.ac.utn.appmovil.util.EXTRA_MESSAGE_ID
import cr.ac.utn.appmovil.util.util

class sin_ListaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: sin_adapter
    private var sinpeList = mutableListOf<sin_Sinpe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sin_lista)

        recyclerView = findViewById(R.id.sin_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadSinpeTransactions()
        adapter = sin_adapter(sinpeList) { sinpe ->
            util.openActivity(this, sin_CRUD::class.java, EXTRA_MESSAGE_ID, sinpe.Id)
        }
        recyclerView.adapter = adapter
    }

    private fun loadSinpeTransactions() {
        sinpeList = MemoryManager.getAll().filterIsInstance<sin_Sinpe>().toMutableList()
    }

    override fun onResume() {
        super.onResume()
        loadSinpeTransactions()
        adapter.notifyDataSetChanged()
    }
}
