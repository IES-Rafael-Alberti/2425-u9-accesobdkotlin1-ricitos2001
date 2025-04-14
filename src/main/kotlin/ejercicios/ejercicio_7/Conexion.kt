package org.example.ejercicios.ejercicio_7

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Conexion {
    var c: Connection
    private val url = "jdbc:h2:./DataBase/mydb"
    private val user = "user"
    private val password = "password"

    init {
        try {
            Class.forName("org.h2.Driver")
            c = DriverManager.getConnection(url, user, password)
        } catch (e: SQLException) {
            throw SQLException("Error en la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            throw ClassNotFoundException("No se encontró el driver JDBC: ${e.message}")
        }
    }
}
