package cr.ac.utn.appmovil.model

import cr.ac.utn.appmovil.data.MemoryManager
import cr.ac.utn.appmovil.interfaces.IDBManager
import cr.ac.utn.appmovil.identities.sin_Sinpe

class sin_model {
    private val dbManager: IDBManager = MemoryManager

    fun addSinpeTransaction(transaction: sin_Sinpe) {
        dbManager.add(transaction)
    }

    fun updateSinpeTransaction(transaction: sin_Sinpe) {
        dbManager.update(transaction)
    }

    fun removeSinpeTransaction(id: String) {
        dbManager.remove(id)
    }

    fun getSinpeTransactions(): List<sin_Sinpe> {
        return dbManager.getAll().filterIsInstance<sin_Sinpe>()
    }

    fun getSinpeTransactionById(id: String): sin_Sinpe? {
        return dbManager.getByid(id) as? sin_Sinpe
    }
}
