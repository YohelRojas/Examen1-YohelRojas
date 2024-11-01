package cr.ac.utn.appmovil.model
import cr.ac.utn.appmovil.data.MemoryManager
import cr.ac.utn.appmovil.identities.Persona
import cr.ac.utn.appmovil.interfaces.IDBManager

class PersonaModel {
    private val dbManager: IDBManager = MemoryManager

    fun addContact(contact: Persona){

    }

    fun updateContact(contact: Persona){

    }

    fun removeContact(id: String){

    }

    fun getContacts()= dbManager.getAll()

    fun getContact(id: String): Persona{
        return Persona()
    }

    fun getContactbyName(fullName: String): Persona{
       return Persona()
    }

    fun getContactNames(): List<String> {
        val names = mutableListOf<String>()
        return names.toList()
    }
}