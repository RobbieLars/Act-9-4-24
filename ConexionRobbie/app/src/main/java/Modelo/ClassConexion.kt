package Modelo

import java.sql.Connection
import java.sql.DriverManager

class ClassConexion {
    fun cadenaConexion(): Connection?{
        try{
            val ip = "jdbc:oracle:thin:@10.10.0.59:1521:xe"
            val usuario = "system"
            val contra = "desarrollo"

            val conexion = DriverManager.getConnection(ip,usuario,contra)
            return conexion

        }catch (e:Exception){
            println("Este es un error: $e")
            return null

        }
    }
}