package cr.ac.utn.appmovil.interfaces

import identities.Identifier

interface IDBManager {
    fun add (obj: Identifier)
    fun update (obj: Identifier)
    fun remove (id: String)
    fun getAll(): List<Identifier>
    fun getByid(id: String): Identifier?
    fun getByFullDescription(fullDescription: String): Identifier?
}