package Robbie.Larios.myapplication

import Modelo.ClassConexion
import Modelo.dataClassMusica
import RecycleViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.txtDuracion)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Mandar a llamar a todos los elementos
        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtDuracion = findViewById<EditText>(R.id.txtDuracion2)
        val txtAutor = findViewById<EditText>(R.id.txtAutor)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        val rcvMusica = findViewById<RecyclerView>(R.id.rcvMusica)

        rcvMusica.layoutManager = LinearLayoutManager(this)

        //////TODO: mostrar datos

        fun mostrarDatos(): List<dataClassMusica>
        {
            val objConexion = ClassConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT * FROM tbMusica")!!

            val canciones = mutableListOf<dataClassMusica>()

            while (resultSet.next()){
                val nombre = resultSet.getString("nombreCancion")
                val cancion = dataClassMusica(nombre)
                canciones.add(cancion)
            }
            return canciones
        }

        CoroutineScope(Dispatchers.IO).launch{
            val musicaDB = mostrarDatos()
            withContext(Dispatchers.Main){
                val miAdaptador = Adaptador(musicaDB)
                rcvMusica.adapter = miAdaptador
            }
        }


        btnAgregar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //crear un objeto
                val objConexion = ClassConexion().cadenaConexion()
                //Crear una variable que contenga preparestartement
                val addMusica = objConexion?.prepareStatement("insert into tbMusica Values(?,?,?)")!!


                addMusica.setString(1,txtNombre.text.toString())
                addMusica.setInt(2,txtDuracion.text.toString().toInt())
                addMusica.setString(3,txtAutor.text.toString())
                addMusica.executeUpdate()

            }
        }
    }
}