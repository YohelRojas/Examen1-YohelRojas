package cr.ac.utn.appmovil.identities

import identities.Identifier
import java.util.Date

class sin_Sinpe : Identifier {
    var sin_originPerson: String = ""
    var sin_phoneNumber: String = ""
    var sin_destinationName: String = ""
    var sin_amount: Double = 0.0
    var sin_description: String = ""
    var sin_dateTime: Date = Date()

    constructor(
        id: String,
        originPerson: String,
        phoneNumber: String,
        destinationName: String,
        amount: Double,
        description: String,
        dateTime: Date
    ) {
        this.Id = id
        this.sin_originPerson = originPerson
        this.sin_phoneNumber = phoneNumber
        this.sin_destinationName = destinationName
        this.sin_amount = amount
        this.sin_description = description
        this.sin_dateTime = dateTime
    }

    override val FullDescription: String
        get() = "$sin_originPerson sent $sin_amount to $sin_destinationName on $sin_dateTime"
}
