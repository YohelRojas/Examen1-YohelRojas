package cr.ac.utn.movil

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.appmovil.data.MemoryManager
import cr.ac.utn.appmovil.identities.sin_Sinpe
import cr.ac.utn.appmovil.util.EXTRA_MESSAGE_ID
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class sin_CRUD : AppCompatActivity() {

    private lateinit var txtOriginPerson: EditText
    private lateinit var txtPhoneNumber: EditText
    private lateinit var txtDestinationName: EditText
    private lateinit var txtAmount: EditText
    private lateinit var txtDescription: EditText
    private lateinit var txtDateTime: EditText
    private lateinit var btnSaveSinpe: Button
    private var isEditionMode: Boolean = false
    private var sinpeId: String? = null

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sin_crud)

        txtOriginPerson = findViewById(R.id.sin_origin_person)
        txtPhoneNumber = findViewById(R.id.sin_phone_number)
        txtDestinationName = findViewById(R.id.sin_destination_name)
        txtAmount = findViewById(R.id.sin_amount)
        txtDescription = findViewById(R.id.sin_description)
        txtDateTime = findViewById(R.id.sin_date_time)
        btnSaveSinpe = findViewById(R.id.sin_btn_save_sinpe)

        btnSaveSinpe.setOnClickListener {
            saveSinpe()
        }

        setupDatePicker()

        sinpeId = intent.getStringExtra(EXTRA_MESSAGE_ID)
        if (sinpeId != null) {
            loadSinpe(sinpeId!!)
            isEditionMode = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.crud_menu, menu)
        menu?.findItem(R.id.mnu_delete)?.isEnabled = isEditionMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                saveSinpe()
                true
            }
            R.id.mnu_delete -> {
                deleteSinpe()
                true
            }
            R.id.mnu_cancel -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveSinpe() {
        try {
            val sinpe = sin_Sinpe(
                id = sinpeId ?: UUID.randomUUID().toString(),
                originPerson = txtOriginPerson.text.toString().trim(),
                phoneNumber = txtPhoneNumber.text.toString().trim(),
                destinationName = txtDestinationName.text.toString().trim(),
                amount = txtAmount.text.toString().toDoubleOrNull() ?: 0.0,
                description = txtDescription.text.toString().trim(),
                dateTime = dateFormat.parse(txtDateTime.text.toString()) ?: Date()
            )

            if (validateData(sinpe)) {
                if (!isEditionMode) {
                    if (isDuplicate(sinpe)) {
                        Toast.makeText(this, getString(R.string.sinpe_duplicate), Toast.LENGTH_LONG).show()
                        return
                    }
                    MemoryManager.add(sinpe)
                    Toast.makeText(this, getString(R.string.sinpe_saved), Toast.LENGTH_LONG).show()
                } else {
                    MemoryManager.update(sinpe)
                    Toast.makeText(this, getString(R.string.sinpe_updated), Toast.LENGTH_LONG).show()
                }
                finish()
            }
        } catch (e: ParseException) {
            Toast.makeText(this, getString(R.string.invalid_date_format), Toast.LENGTH_LONG).show()
        } catch (e: NumberFormatException) {
            txtAmount.error = getString(R.string.error_amount)
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun validateData(sinpe: sin_Sinpe): Boolean {
        var isValid = true

        // Validate Origin Person
        if (sinpe.sin_originPerson.isBlank()) {
            txtOriginPerson.error = getString(R.string.error_origin_person)
            isValid = false
        } else if (!sinpe.sin_originPerson.matches(Regex("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$"))) {
            txtOriginPerson.error = getString(R.string.error_origin_person)
            isValid = false
        }

        // Validate Phone Number
        if (sinpe.sin_phoneNumber.isBlank()) {
            txtPhoneNumber.error = getString(R.string.error_phone_number)
            isValid = false
        } else if (!sinpe.sin_phoneNumber.matches(Regex("^\\d{8}$"))) {
            txtPhoneNumber.error = getString(R.string.error_phone_number)
            isValid = false
        }

        // Validate Destination Name
        if (sinpe.sin_destinationName.isBlank()) {
            txtDestinationName.error = getString(R.string.error_destination_name)
            isValid = false
        } else if (!sinpe.sin_destinationName.matches(Regex("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$"))) {
            txtDestinationName.error = getString(R.string.error_destination_name)
            isValid = false
        }

        // Validate Amount
        if (txtAmount.text.toString().isBlank()) {
            txtAmount.error = getString(R.string.error_amount)
            isValid = false
        } else {
            val amount = sinpe.sin_amount
            if (amount <= 0) {
                txtAmount.error = getString(R.string.error_amount)
                isValid = false
            } else if (amount > 10000) {
                txtAmount.error = getString(R.string.error_amount)
                isValid = false
            }
        }

        // Validate Description
        if (sinpe.sin_description.isBlank()) {
            txtDescription.error = getString(R.string.error_description)
            isValid = false
        } else if (sinpe.sin_description.length > 200) {
            txtDescription.error = getString(R.string.error_description)
            isValid = false
        }

        // Validate Date
        if (txtDateTime.text.toString().isBlank()) {
            txtDateTime.error = getString(R.string.error_date_time)
            isValid = false
        } else {
            val date = sinpe.sin_dateTime
            val currentDate = Date()
            if (date.after(currentDate)) {
                txtDateTime.error = getString(R.string.error_date_time)
                isValid = false
            }
        }

        return isValid
    }

    private fun isDuplicate(sinpe: sin_Sinpe): Boolean {
        val allSinpes = MemoryManager.getAll().filterIsInstance<sin_Sinpe>()
        return allSinpes.any {
            it.sin_originPerson.equals(sinpe.sin_originPerson, ignoreCase = true) &&
                    it.sin_destinationName.equals(sinpe.sin_destinationName, ignoreCase = true) &&
                    it.sin_dateTime == sinpe.sin_dateTime &&
                    it.sin_amount == sinpe.sin_amount
        }
    }

    private fun deleteSinpe() {
        sinpeId?.let {
            MemoryManager.remove(it)
            Toast.makeText(this, getString(R.string.sinpe_deleted), Toast.LENGTH_LONG).show()
            finish()
        } ?: Toast.makeText(this, getString(R.string.sinpe_not_found), Toast.LENGTH_LONG).show()
    }

    private fun loadSinpe(id: String) {
        val sinpe = MemoryManager.getByid(id) as? sin_Sinpe
        if (sinpe != null) {
            txtOriginPerson.setText(sinpe.sin_originPerson)
            txtPhoneNumber.setText(sinpe.sin_phoneNumber)
            txtDestinationName.setText(sinpe.sin_destinationName)
            txtAmount.setText(sinpe.sin_amount.toString())
            txtDescription.setText(sinpe.sin_description)
            txtDateTime.setText(dateFormat.format(sinpe.sin_dateTime))
            isEditionMode = true
            btnSaveSinpe.text = getString(R.string.update_button_text)
        } else {
            Toast.makeText(this, getString(R.string.sinpe_not_found), Toast.LENGTH_LONG).show()
        }
    }

    private fun setupDatePicker() {
        txtDateTime.setOnClickListener {
            showDatePickerDialog(txtDateTime)
        }
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                editText.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.maxDate = System.currentTimeMillis() // Prevent future dates
        datePicker.show()
    }
}
