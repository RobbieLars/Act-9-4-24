package RecycleViewHelpers

import Robbie.Larios.myapplication.R
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder (view: View): RecyclerView.ViewHolder(view){
        val txtNombre:TextView = view.findViewById(R.id.txtMusica)
    }