package cr.ac.utn.appmovil.util

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

const val EXTRA_MESSAGE_ID = "cr.ac.utn.appmovil.Id"

class util {
    companion object  {
        fun openActivity(context: Context, objclass: Class<*>, extraName: String, value: String?){
            val intent = Intent(context, objclass).apply { putExtra(extraName, value)}
            startActivity(context, intent, null)
        }
    }
}